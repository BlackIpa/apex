package com.apex.enums;

public enum Region {
	AMERICAS("Americas"),
	EMEA("Europe, Middle East and Africa"),
	APAC_NORTH("Asia-Pacific North"),
	APAC_SOUTH("Asia-Pacific South");

	private final String displayName;

	Region(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
