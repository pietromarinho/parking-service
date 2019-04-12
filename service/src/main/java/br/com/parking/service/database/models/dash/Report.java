package br.com.parking.service.database.models.dash;

import br.com.parking.core.model.AbstractModel;
import br.com.parking.service.database.models.Car;
import br.com.parking.service.database.models.Date;
import br.com.parking.service.database.models.Period;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(indexes = @Index(name = "idx_report", columnList = "date_id, car_id, period_id", unique = true))
public class Report extends AbstractModel {
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
	private			double			value;
}
