package br.com.parking.service.api.controllers;

import br.com.parking.core.controller.AbstractREST;
import br.com.parking.service.api.dto.RegisterDTO;
import br.com.parking.service.api.services.RegisterBO;
import br.com.parking.service.database.models.Register;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(name = "Register", path = "/rest/register")
public class RegisterREST extends AbstractREST<Register, RegisterDTO> {
	@RequestMapping(method = RequestMethod.GET, path = "/report")
	public ResponseEntity<?> getReport(@RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startsAt, @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endsAt) {
		return ResponseEntity.ok(((RegisterBO) getService()).getReport(startsAt, endsAt));
	}
}
