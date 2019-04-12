package br.com.parking.service.api.services;

import br.com.parking.core.dto.AbstractDTO;
import br.com.parking.core.service.AbstractBO;
import br.com.parking.service.api.dto.CarDTO;
import br.com.parking.service.database.models.Car;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarBO extends AbstractBO<Car, CarDTO> {
	@Override
	public CarDTO save(CarDTO data) {
		Car				model				= data.getModel();
		Car				car					= getDAO().find(model.getIdentifier(), Car.class);

		if (car != null) {
			getMapper(Car.class).map(model, car);
			return new CarDTO(getDAO().save(car));
		}

		return new CarDTO(getDAO().save(model));
	}

	@Override
	public List<CarDTO> save(List<CarDTO> collection) {
		List<String>		identifiers		= collection.stream().map(AbstractDTO::getIdentifier).collect(Collectors.toList());
		List<Car>			cars			= getDAO().find(identifiers, Car.class);
		List<Car>			models			= new ArrayList<>();

		for (CarDTO data : collection) {
			Car				model			= data.getModel();
			Car				car				= cars.stream().filter(i -> i.getIdentifier().equals(model.getIdentifier())).findAny().orElse(null);

			if (car != null) {
				getMapper(Car.class).map(model, car);
				models.add(car);
			} else {
				models.add(model);
			}
		}

		return getDAO().save(models).stream().map(CarDTO::new).collect(Collectors.toList());
	}
}