package calebcompass.calebcompass.util;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CompassLocation {

	private UUID uuid;

	private Location origin;
	private Location target;

	private boolean isShowing;
	private boolean isTracking;

	public CompassLocation(Player player, Location loc1, Location loc2) {
		uuid = player.getUniqueId();

		origin = loc1;
		target = loc2;
	}

	public CompassLocation(UUID uuid, Location loc1, Location loc2) {
		this.uuid = uuid;

		origin = loc1;
		target = loc2;

		isShowing = true;
		isTracking = true;
	}

	public UUID getUUID() {
		return uuid;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public Location getOrigin() {
		return origin;
	}

	public void setOrigin(Location origin) {
		this.origin = origin;
	}

	public Location getTarget() {
		return target;
	}

	public void setTarget(Location target) {
		this.target = target;
	}

	public boolean isTracking() {
		return isTracking;
	}

	public void setTracking(boolean tracking) {
		isTracking = tracking;
	}

	public boolean isShowing() {
		return isShowing;
	}

	public void setShowing(boolean showing) {
		isShowing = showing;
	}

}
