package br.com.parking.service;

import br.com.parking.service.api.dto.CarDTO;
import br.com.parking.service.api.dto.PeriodDTO;
import br.com.parking.service.database.enumerations.WeekDay;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class APITest {
	@Getter(AccessLevel.PRIVATE)
	@Autowired
	private			MockMvc			mvc;

	@Getter(AccessLevel.PRIVATE)
	@Autowired
	private 		ObjectMapper	mapper;

	@Test
	public void postCarThenOk() throws Exception {
		CarDTO		dto			= new CarDTO();

		dto.setColor("RED");
		dto.setLicensePlate("NPA4904");
		dto.setManufacturer("FORD");
		dto.setModelName("MUSTANG");

		mvc.perform(post("/rest/car/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getMapper().writeValueAsString(dto)))
				.andExpect(status().is2xxSuccessful());

	}

	@Test
	public void postPeriodThenOk() throws Exception {
		PeriodDTO	dto			= new PeriodDTO();

		dto.setDay(WeekDay.WEDNESDAY);
		dto.setEndsAt(LocalTime.of(20, 0));
		dto.setStartsAt(LocalTime.of(19, 0));
		dto.setValue(5.0d);

		mvc.perform(post("/rest/period/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(getMapper().writeValueAsString(dto)))
				.andExpect(status().is2xxSuccessful());
	}
}
