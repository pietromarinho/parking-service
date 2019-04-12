package br.com.parking.service.database.models;

import br.com.parking.core.model.AbstractModel;
import br.com.parking.service.database.enumerations.TrafficType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(indexes = {
	@Index(name = "idx_register", columnList = "date_id, car_id, period_id, type", unique = true),
	@Index(name = "idx_register_date", columnList = "date_id"),
	@Index(name = "idx_register_car", columnList = "car_id"),
	@Index(name = "idx_register_period", columnList = "period_id"),
	@Index(name = "idx_register_type", columnList = "type")
})
public class Register extends AbstractModel {
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name = "date_id"), @JoinColumn(name = "date_seq") }, foreignKey = @ForeignKey(name = "fk_date"))
	private 		Date			date;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name = "car_id"), @JoinColumn(name = "car_seq") }, foreignKey = @ForeignKey(name = "fk_car"))
	private			Car				car;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name = "period_id"), @JoinColumn(name = "period_seq") }, foreignKey = @ForeignKey(name = "fk_period"))
	private 		Period			period;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@Enumerated(EnumType.STRING)
	private 		TrafficType		type;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@ColumnDefault("TRUE")
	private 		boolean			valid;
}