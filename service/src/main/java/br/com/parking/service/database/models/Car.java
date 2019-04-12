package br.com.parking.service.database.models;

import br.com.parking.core.model.AbstractModel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = {@Index(name = "idx_car_license_plate", columnList = "license_plate", unique = true)})
public class Car extends AbstractModel {
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@Column(name = "license_plate", columnDefinition = "char(7)")
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
	public			String				modelName;
}