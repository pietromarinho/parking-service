package br.com.parking.service.initialize;

import br.com.parking.core.dao.DAOInterface;
import br.com.parking.service.database.enumerations.WeekDay;
import br.com.parking.service.database.models.Period;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("ALL")
@Component
class Database {
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	private			DAOInterface			DAO;

	@Autowired
	public Database(DAOInterface dao) {
		setDAO(dao);
	}

	@EventListener(ApplicationStartedEvent.class)
	public void initialize() {
		if (getDAO().find(Period.class).isEmpty()) {
			for (WeekDay day : WeekDay.values()) {
				List<String>	days		= Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY");
				List<String>	weekends	= Arrays.asList("SATURDAY", "SUNDAY", "HOLIDAY");

				if (days.stream().anyMatch(i -> i.equalsIgnoreCase(day.getValue()))) {
					Period			periodA		= new Period();
					Period			periodB		= new Period();
					periodA.setDay(day);
					periodA.setStartsAt(LocalTime.of(8, 0));
					periodA.setEndsAt(LocalTime.of(11, 59, 59));
					periodA.setValue(2d);
					periodB.setDay(day);
					periodB.setStartsAt(LocalTime.of(12, 0));
					periodB.setEndsAt(LocalTime.of(18, 0));
					periodB.setValue(3d);
					getDAO().save(periodA);
					getDAO().save(periodB);
				}

				if (weekends.stream().anyMatch(i -> i.equalsIgnoreCase(day.getValue()))) {
					Period			period		= new Period();
					period.setDay(day);
					period.setStartsAt(LocalTime.of(8, 0));
					period.setEndsAt(LocalTime.of(18, 0));
					period.setValue(3.5d);
					getDAO().save(period);
				}
			}
		}
	}
}
