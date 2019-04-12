package br.com.parking.service.database.enumerations;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum WeekDay {
	MONDAY		("MONDAY"),
	TUESDAY		("TUESDAY"),
	WEDNESDAY	("WEDNESDAY"),
	THURSDAY	("THURSDAY"),
	FRIDAY		("FRIDAY"),
	SATURDAY	("SATURDAY"),
	SUNDAY		("SUNDAY"),
	HOLIDAY		("HOLIDAY"),
	VOID		("VOID");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String		enumeration;

	WeekDay(String value) {
		setEnumeration(value);
	}

	public boolean equals(String value) {
		return getEnumeration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumeration();
	}

	public static WeekDay setValue(String value) {
		return Arrays.stream(WeekDay.values()).filter(item -> item.getEnumeration().equalsIgnoreCase(value)).findAny().orElse(VOID);
	}
}