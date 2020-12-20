package calebcompass.calebcompass.miscevents;

import calebcompass.calebcompass.CalebCompassCommand;
import calebcompass.calebcompass.SavePoints.SavePoint;
import calebcompass.calebcompass.SavePoints.SavePointConfig;
import calebcompass.calebcompass.util.CompassInstance;
import calebcompass.calebcompass.util.ConfigManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemFocus implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (ConfigManager.getInstance().getFocusItem() == null || !(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        Material focusItem = ConfigManager.getInstance().getFocusItem();
        if (!e.getPlayer().getInventory().getItemInMainHand().getType().equals(focusItem) && !e.getPlayer().getInventory().getItemInOffHand().getType().equals(focusItem)) return;
        Player player = e.getPlayer();
        SavePointConfig.getInstance().load();
        SavePoint point = CalebCompassCommand.getSavePointAtLoc(player);
        if (point == null) return;
        CompassInstance.getInstance().getCompassLocation(player).setTarget(point.getLoc1());
        CompassInstance.getInstance().getCompassLocation(player).setOrigin(player.getLocation());
        CompassInstance.getInstance().getCompassLocation(player).setTracking(true);
        CompassInstance.getInstance().saveData();
    }
}
