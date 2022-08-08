package calebcompass.calebcompass.util;

import calebcompass.calebcompass.SavePoints.SavePoint;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class CompassLocation {

	private UUID uuid;

	private Location origin;
	private Location target;

	private Player player;

	private boolean isShowing;
	private boolean isTracking;

	private ArrayList<SavePoint> activePoints;

	public CompassLocation(Player player, Location loc1, Location loc2) {
		uuid = player.getUniqueId();

		this.player = player;

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

	public ArrayList<SavePoint> getTrueActivePoints() {
		return activePoints;
	}

	public ArrayList<SavePoint> getActivePoints() {
		ArrayList<SavePoint> returnPoints = new ArrayList<>();
		player = Bukkit.getPlayer(uuid);
		if (player != null && activePoints != null) {
			for (SavePoint cur : activePoints) {
				if (cur == null ) continue;
				if (player.getLocation().getWorld().equals(cur.getLoc1().getWorld()) && (cur.getLoc1().distance(player.getLocation()) <= cur.getMaxRange() || cur.getMaxRange() <=0)) {
					returnPoints.add(cur);
				}
			}
		}
		return returnPoints;
	}

	public void addActivePoint(SavePoint newPoint) {
		this.activePoints.add(newPoint);
	}

	public void removeActivePoint(SavePoint lePoint) {
		this.activePoints.remove(lePoint);
	}

	public void setActivePoints(ArrayList<SavePoint> activePoints) {
		this.activePoints = activePoints;
	}
}
