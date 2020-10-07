package calebcompass.calebcompass;

import calebcompass.calebcompass.util.CompassConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class CompassMoveEvent implements Listener {
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		CompassConstructor compass = new CompassConstructor(player);
		compass.updateCompass();
	}

}
