package br.com.parking.service.api.dto;

import br.com.parking.core.dto.AbstractDTO;
import br.com.parking.service.database.enumerations.TrafficType;
import br.com.parking.service.database.models.Register;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class RegisterDTO extends AbstractDTO<Register> {
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private			CarDTO				car;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private			PeriodDTO			period;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	private			TrafficType			type;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@JsonProperty("car_id")
	@Length(min = 20, max = 20, message = "[Register]: Invalid car identifier!")
	private			String				carID;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	private 		LocalDateTime		entering;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	private 		LocalDateTime		exiting;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	private 		Double				total;

	public RegisterDTO(Register entity) {
		super(entity);
		if (entity != null) {
			setCarID(entity.getCar().getIdentifier());
		}
	}

	public RegisterDTO(Register entity, LocalDateTime entering, LocalDateTime exiting, Double value) {
		super(entity);
		setCarID(entity.getCar().getIdentifier());
		setEntering(entering);
		setExiting(exiting);
		setTotal(entering.until(exiting, ChronoUnit.HOURS) * value);
	}
}