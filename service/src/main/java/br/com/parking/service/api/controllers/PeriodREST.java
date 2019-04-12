package br.com.parking.service.api.controllers;

import br.com.parking.core.controller.AbstractREST;
import br.com.parking.service.api.dto.PeriodDTO;
import br.com.parking.service.database.models.Period;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(name = "Period", path = "/rest/period")
public class PeriodREST extends AbstractREST<Period, PeriodDTO> {
	//
}
