package calebcompass.calebcompass.mythicmobs;

import calebcompass.calebcompass.SavePoints.SavePoint;
import calebcompass.calebcompass.util.CompassInstance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class MythicEvents extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ArrayList<Entity> nearByEntities = (ArrayList<Entity>) player.getNearbyEntities(MythicInstance.getInstance().getMaxRange(), MythicInstance.getInstance().getMaxRange(), MythicInstance.getInstance().getMaxRange());
            for (int i = 0; i < nearByEntities.size(); i++) {
                Entity entity = nearByEntities.get(i);
                if (!MythicComparer.isEntityMythic(entity)) continue;
                String mobName = MythicComparer.getMythicMobName(entity);
                if (!MythicInstance.getInstance().hasCustomOverride(MythicComparer.getMythicMobName(entity)) && !MythicInstance.getInstance().isDefaultMobShow()) continue;
                SavePoint newPoint  = new SavePoint(entity.getLocation(), "Mythic_Mob_" + i, MythicInstance.getInstance().getRegularOverride(mobName), MythicInstance.getInstance().getHoveredOverride(mobName));
                newPoint.setMythic(true);
                CompassInstance.getInstance().addSavePoint(player.getUniqueId(), newPoint);
            }
        }
    }
}


