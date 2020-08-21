package calebcompass.calebcompass.util;

public enum Symbol {

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
	WAYPOINT("waypoint"),

	;

	private final String name;

	Symbol(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
