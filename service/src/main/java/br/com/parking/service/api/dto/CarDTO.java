package br.com.parking.service.api.dto;

import br.com.parking.core.dto.AbstractDTO;
import br.com.parking.service.database.models.Car;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CarDTO extends AbstractDTO<Car> {
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@JsonProperty("plate")
	@Length(max = 7, min = 7, message = "[Car]: Invalid license plate!")
	public			String				licensePlate;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	public 			String				color;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	public 			String				manufacturer;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@JsonProperty("model")
	public			String				modelName;

	public CarDTO(Car entity) {
		super(entity);
	}
}