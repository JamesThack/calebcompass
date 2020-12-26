package calebcompass.calebcompass;

import calebcompass.calebcompass.util.CompassConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CompassMoveEvent extends BukkitRunnable {

	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			CompassConstructor compass = new CompassConstructor(player);
			compass.updateCompass();
		}
	}
}
