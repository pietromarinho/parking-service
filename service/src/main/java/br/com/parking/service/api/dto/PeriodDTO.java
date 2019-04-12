package br.com.parking.service.api.dto;

import br.com.parking.core.dto.AbstractDTO;
import br.com.parking.service.database.enumerations.WeekDay;
import br.com.parking.service.database.models.Period;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class PeriodDTO extends AbstractDTO<Period> {
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	private 		WeekDay 		day;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@JsonProperty("ends_at")
	private 		LocalTime 		startsAt;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@JsonProperty("starts_at")
	private			LocalTime		endsAt;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	private 		Double			value;

	public PeriodDTO(Period entity) {
		super(entity);
	}
}