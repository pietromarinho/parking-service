package br.com.parking.service.database.models;

import br.com.parking.core.model.AbstractModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {
	@Index(name = "idx_date_date_time", columnList = "date_time", unique = true),
	@Index(name = "idx_date_hour", columnList = "hour"),
	@Index(name = "idx_date_day", columnList = "day"),
	@Index(name = "idx_date_month", columnList = "month"),
	@Index(name = "idx_date_quarter", columnList = "quarter"),
	@Index(name = "idx_date_semester", columnList = "semester"),
	@Index(name = "idx_date_year", columnList = "year")
})
public class Date extends AbstractModel {
	@Getter
	@Setter
	@Column(name = "date_time")
	private 		LocalDateTime	dateTime;

	@Getter
	@Setter
	private			Integer			hour;

	@Getter
	@Setter
	private			Integer			day;

	@Getter
	@Setter
	private			Integer			month;

	@Getter
	@Setter
	private			Integer			quarter;

	@Getter
	@Setter
	private 		Integer			semester;

	@Getter
	@Setter
	private 		Integer			year;
}
