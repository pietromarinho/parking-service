package br.com.parking.service.api.services;

import br.com.parking.core.service.AbstractBO;
import br.com.parking.service.api.dto.RegisterDTO;
import br.com.parking.service.api.dto.ReportDTO;
import br.com.parking.service.database.enumerations.TrafficType;
import br.com.parking.service.database.enumerations.WeekDay;
import br.com.parking.service.database.models.*;
import br.com.parking.service.database.models.dash.Report;
import br.com.parking.service.database.models.dash.Report_;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegisterBO extends AbstractBO<Register, RegisterDTO> {
	@Override
	public RegisterDTO find(String identifier) {
		Register		register			= getDAO().find(identifier, Register.class);
		Register		lastOut				= Optional.ofNullable(getDAO().find((r, q, b) -> b.and(b.equal(r.get(Register_.car), register.getCar()), b.equal(r.get(Register_.type), TrafficType.OUT)), Register.class)).orElse(new ArrayList<>()).parallelStream().max(Comparator.comparing(a -> a.getDate().getDateTime())).orElse(null);
		Register		lastIn				= Optional.ofNullable(getDAO().find((r, q, b) -> b.and(b.equal(r.get(Register_.car), register.getCar()), b.equal(r.get(Register_.type), TrafficType.IN)), Register.class)).orElse(new ArrayList<>()).parallelStream().max(Comparator.comparing(a -> a.getDate().getDateTime())).orElse(null);

		if (lastIn != null && lastOut == null) {
			return new RegisterDTO(register, lastIn.getDate().getDateTime(), LocalDateTime.now(), lastIn.getPeriod().getValue());
		} else if (lastIn != null){
			return new RegisterDTO(register, lastIn.getDate().getDateTime(), lastOut.getDate().getDateTime(), lastIn.getPeriod().getValue());
		}

		return super.find(identifier);
	}

	@Override
	public RegisterDTO save(RegisterDTO data) {
		Register		model				= data.getModel();
		Register		register			= getDAO().find(data.getIdentifier(), Register.class);
		Car				car					= getDAO().find(data.getCarID(), Car.class);
		Date			date				= new Date();
		LocalDateTime	dateTime			= LocalDateTime.now();
		List<Period>	periods				= Optional.ofNullable(getDAO().find((r, q, b) -> b.and(b.and(b.lessThanOrEqualTo(r.get(Period_.startsAt), dateTime.toLocalTime()), b.greaterThanOrEqualTo(r.get(Period_.endsAt), dateTime.toLocalTime())), b.or(b.equal(r.get(Period_.day), WeekDay.values()[dateTime.getDayOfWeek().getValue() - 1]), b.equal(r.get(Period_.day), WeekDay.HOLIDAY))), Period.class)).orElse(new ArrayList<>());
		Register		last				= Optional.ofNullable(getDAO().find((r, q, b) -> b.equal(r.get(Register_.car), car), Register.class)).orElse(new ArrayList<>()).parallelStream().max(Comparator.comparing(a -> a.getDate().getDateTime())).orElse(null);

		if (periods.isEmpty()) {
			throw new RuntimeException("[Register]: No period registered for current time!");
		}

		if (car == null) {
			throw new RuntimeException("[Register]: No car registered!");
		}

		if (register != null) {
			getMapper(Register.class).map(model, register);
			register.setCar(car);
			register.setPeriod(periods.get(0));
			return new RegisterDTO(getDAO().save(register));
		}

		if (last != null) {
			if (last.getType() == TrafficType.OUT && model.getType() == TrafficType.OUT) {
				throw new RuntimeException("[Register]: No entering registered for this car!");
			}
			if (last.getType() == TrafficType.IN && model.getType() == TrafficType.IN) {
				throw new RuntimeException("[Register]: No exit registered for this car!");
			}
			last.setValid(false);
			getDAO().save(last);
		}

		date.setDateTime(dateTime);
		date.setHour(dateTime.getHour());
		date.setDay(dateTime.getDayOfMonth());
		date.setMonth(dateTime.getMonth().getValue());
		date.setQuarter(dateTime.getMonth().getValue()/4 + 1);
		date.setSemester(dateTime.getMonth().getValue()/6 + 1);
		date.setYear(dateTime.getYear());
		model.setDate(getDAO().save(date));
		model.setCar(car);
		model.setValid(true);
		model.setPeriod(periods.get(0));

		if (model.getType() == TrafficType.OUT && last != null) {
			Report			report			= new Report();
			report.setCar(car);
			report.setDate(date);
			report.setPeriod(periods.get(0));
			report.setValue(last.getDate().getDateTime().until(model.getDate().getDateTime(), ChronoUnit.HOURS) * last.getPeriod().getValue());
			getDAO().save(report);
			return new RegisterDTO(getDAO().save(model), last.getDate().getDateTime(), model.getDate().getDateTime(), last.getPeriod().getValue());
		}

		return new RegisterDTO(getDAO().save(model));
	}

	@Override
	public List<RegisterDTO> save(List<RegisterDTO> data) {
		return null;
	}

	public List<ReportDTO> getReport(LocalDate beginning, LocalDate ending) {
		return Optional.ofNullable(getDAO().find((r, q, b) -> b.between(r.get(Report_.date).get(Date_.dateTime), beginning.atStartOfDay(), ending.atTime(23, 23, 59)), Report.class))
				.orElse(new ArrayList<>()).stream().sorted(Comparator.comparing(y -> y.getPeriod().getStartsAt(), Comparator.naturalOrder())).collect(Collectors.groupingBy(i -> i.getDate().getDateTime().toLocalDate(), Collectors.groupingBy(i -> i.getPeriod().toString(), Collectors.summarizingDouble(Report::getValue))))
				.entrySet().stream().flatMap(i -> i.getValue().entrySet().stream().map(k -> new ReportDTO(k.getKey(), i.getKey().toString(), k.getValue().getSum()))).collect(Collectors.toList());
	}
}