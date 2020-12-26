package calebcompass.calebcompass.citizens;

import calebcompass.calebcompass.SavePoints.SavePoint;
import calebcompass.calebcompass.mythicmobs.MythicComparer;
import calebcompass.calebcompass.mythicmobs.MythicInstance;
import calebcompass.calebcompass.util.CompassInstance;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class CitizensEvents extends BukkitRunnable {

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            ArrayList<Entity> nearByEntities = (ArrayList<Entity>) player.getNearbyEntities(CitizensInstance.getInstance().getMaxRange(), CitizensInstance.getInstance().getMaxRange(), CitizensInstance.getInstance().getMaxRange());
            for (int i = 0; i < nearByEntities.size(); i++) {
                Entity entity = nearByEntities.get(i);
                if (!CitizensComparers.isEntityNPC(entity)) continue;
                String npcName = CitizensComparers.getNPCID(entity);
                if (!CitizensInstance.getInstance().hasCustomOverride(npcName) && !CitizensInstance.getInstance().isDefaultNpcShow()) continue;
                SavePoint newPoint  = new SavePoint(entity.getLocation(), "NPC_" + i, CitizensInstance.getInstance().getRegularOverride(npcName), CitizensInstance.getInstance().getHoveredOverride(npcName));
                newPoint.setNPC(true);
                CompassInstance.getInstance().addSavePoint(player.getUniqueId(), newPoint);
            }
        }
    }
}
