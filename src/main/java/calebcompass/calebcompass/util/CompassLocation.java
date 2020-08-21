package calebcompass.calebcompass.util;

import java.util.ArrayList;
import java.util.UUID;

import calebcompass.calebcompass.SavePoints.SavePoint;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CompassLocation {

	private UUID uuid;

	private Location origin;
	private Location target;

	private boolean isShowing;
	private boolean isTracking;

	private ArrayList<SavePoint> activePoints;

	public CompassLocation(Player player, Location loc1, Location loc2) {
		uuid = player.getUniqueId();

		origin = loc1;
		target = loc2;

		activePoints = new ArrayList<SavePoint>();
	}

	public CompassLocation(UUID uuid, Location loc1, Location loc2) {
		this.uuid = uuid;

		origin = loc1;
		target = loc2;

		isShowing = true;
		isTracking = true;

		activePoints = new ArrayList<SavePoint>();
	}

	public UUID getUUID() {
		return uuid;
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

	public ArrayList<SavePoint> getActivePoints() {
		return activePoints;
	}

	public void addActivePoint(SavePoint newPoint) {
		this.activePoints.add(newPoint);
	}

	public void removeActivePoint(SavePoint lePoint) {
		this.activePoints.remove(lePoint);
	}
}
