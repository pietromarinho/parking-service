package br.com.parking.service.api.services;

import br.com.parking.core.dto.AbstractDTO;
import br.com.parking.core.service.AbstractBO;
import br.com.parking.service.api.dto.PeriodDTO;
import br.com.parking.service.database.models.Period;
import br.com.parking.service.database.models.Period_;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeriodBO extends AbstractBO<Period, PeriodDTO> {
	@Override
	public PeriodDTO save(PeriodDTO data) {
		Period			model				= data.getModel();
		Period			period				= getDAO().find(data.getIdentifier(), Period.class);

		if (period != null) {
			getMapper(Period.class).map(model, period);
			if (available(model)) {
				return new PeriodDTO(getDAO().save(period));
			} else {
				throw new RuntimeException("[Period]: Period unavailable");
			}
		}

		if (available(model)) {
			return new PeriodDTO(getDAO().save(model));
		} else {
			throw new RuntimeException("[Period]: Period unavailable");
		}
	}

	@Override
	public List<PeriodDTO> save(List<PeriodDTO> collection) {
		List<String>		identifiers		= collection.stream().map(AbstractDTO::getIdentifier).collect(Collectors.toList());
		List<Period>		periods			= getDAO().find(identifiers, Period.class);
		List<Period>		models			= new ArrayList<>();

		for (PeriodDTO data : collection) {
			Period			model			= data.getModel();
			Period			period			= periods.stream().filter(i -> i.getIdentifier().equals(model.getIdentifier())).findAny().orElse(null);

			if (period != null) {
				getMapper(Period.class).map(model, period);
				if (available(model)) {
					models.add(period);
				}
			} else {
				if (available(model)) {
					models.add(model);
				}
			}
		}

		return getDAO().save(models).stream().map(PeriodDTO::new).collect(Collectors.toList());
	}

	private boolean available(Period model) {
		return getDAO().find((r, q, b) -> b.or(b.between(r.get(Period_.startsAt), model.getStartsAt(), model.getEndsAt()), b.between(r.get(Period_.endsAt), model.getStartsAt(), model.getEndsAt())), Period.class).isEmpty();
	}
}