package calebcompass.calebcompass;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import calebcompass.calebcompass.util.*;

public class CompassMoveEvent implements Listener {

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		Location playerLoc = player.getLocation();
		CompassLocation location = CompassInstance.getInstance().getCompassLocation(player);
		BossBar bar = CompassInstance.getInstance().getBarFromPlayer(player);

		int yaw = Math.round((playerLoc.getYaw() + 360) / 9);
		if (bar == null) {
			bar = Bukkit.createBossBar(MessageUtil.getMessage(yaw, 365), Util.getBarColor(), Util.getBarStyle());
			bar.addPlayer(player);
			CompassInstance.getInstance().addBar(bar);
		}

		bar.setVisible(CompassInstance.getInstance().isCompassVisible(player));
		bar.setColor(Util.getBarColor());
		bar.setStyle(Util.getBarStyle());

		if (location != null && location.isTracking()) {
			double dist = location.getOrigin().distanceSquared(location.getTarget());
			double newDist = playerLoc.distanceSquared(location.getTarget());
			if (newDist > dist) location.setOrigin(playerLoc);

			double travel = newDist / dist;
			if (travel >= 1F) travel = 1F;
			bar.setProgress(1F - travel);

			if (checkCoords(playerLoc, location.getTarget())) {
				bar.setTitle(MessageUtil.getMessage(yaw, 500));
				return;
			}
			// our target location (Point B)
			Vector target = location.getTarget().toVector();
			// set the origin's direction to be the direction vector between point A and B.
			playerLoc.setDirection(target.subtract(playerLoc.toVector()));
			float playerYaw = playerLoc.getYaw();
			bar.setTitle(MessageUtil.getMessage(yaw, Math.round(playerYaw / 9)));
			return;
		}

		bar.setTitle(MessageUtil.getMessage(yaw, 500));
		bar.setProgress(1F);
	}

	private boolean checkCoords(Location loc1, Location loc2) {
		return loc1.getX() == loc2.getX() && loc1.getZ() == loc2.getZ();
	}

}
