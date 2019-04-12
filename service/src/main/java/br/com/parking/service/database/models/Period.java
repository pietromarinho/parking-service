package br.com.parking.service.database.models;

import br.com.parking.core.model.AbstractModel;
import br.com.parking.service.database.enumerations.WeekDay;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(indexes = {@Index(name = "idx_period", columnList = "day, starts_at, ends_at", unique = true)})
public class Period extends AbstractModel {
	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@Enumerated(EnumType.STRING)
	private 		WeekDay 		day;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@Column(name = "ends_at")
	private			LocalTime		startsAt;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	@Column(name = "starts_at")
	private			LocalTime		endsAt;

	@Getter(AccessLevel.PUBLIC)
	@Setter(AccessLevel.PUBLIC)
	private 		Double			value;

	@Override
	public String toString() {
		return getStartsAt().format(DateTimeFormatter.ISO_LOCAL_TIME) + " - " + getEndsAt().format(DateTimeFormatter.ISO_LOCAL_TIME);
	}
}