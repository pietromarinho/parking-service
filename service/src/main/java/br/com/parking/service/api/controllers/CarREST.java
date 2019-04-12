package br.com.parking.service.api.controllers;

import br.com.parking.core.controller.AbstractREST;
import br.com.parking.service.api.dto.CarDTO;
import br.com.parking.service.database.models.Car;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "Car", path = "/rest/car")
public class CarREST extends AbstractREST<Car, CarDTO> {
	//
}
