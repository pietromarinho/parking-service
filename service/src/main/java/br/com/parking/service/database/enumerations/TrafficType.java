package br.com.parking.service.database.enumerations;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

public enum TrafficType {
	IN		("IN"),
	OUT		("OUT"),
	VOID	("VOID");

	@Getter(value = AccessLevel.PRIVATE)
	@Setter(value = AccessLevel.PRIVATE)
	private		String		enumeration;

	TrafficType(String value) {
		setEnumeration(value);
	}

	public boolean equals(String value) {
		return getEnumeration().equalsIgnoreCase(value);
	}

	public String getValue() {
		return getEnumeration();
	}

	public static TrafficType setValue(String value) {
		return Arrays.stream(TrafficType.values()).filter(item -> item.getEnumeration().equalsIgnoreCase(value)).findAny().orElse(VOID);
	}
}