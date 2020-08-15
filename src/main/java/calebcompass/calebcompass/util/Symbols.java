package calebcompass.calebcompass.util;

public enum Symbols {

	NORTH("north"),
	NORTH_EAST("north-east"),
	EAST("east"),
	SOUTH_EAST("south-east"),
	SOUTH("south"),
	SOUTH_WEST("south-west"),
	WEST("west"),
	NORTH_WEST("north-west"),

	FILLED("filled"),
	TRACKER("tracker"),

	;

	private final String name;

	Symbols(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
