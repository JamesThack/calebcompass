package calebcompass.calebcompass.towny;

import calebcompass.calebcompass.SavePoints.SavePoint;
import calebcompass.calebcompass.util.CompassInstance;
import calebcompass.calebcompass.util.LocationMethods;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Town;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TownyEvents  {

    public static void addTownyPoints(Player passed) {
        int count = 0;
        for (Town cur : TownyAPI.getInstance().getTowns()) {
            try {
                Location loc = cur.getSpawn();
                count +=1;
                TownObject town = TownyConfig.getInstance().getTown(cur.getName());
                SavePoint newPoint;
                int maxDist = TownyConfig.getInstance().getMaxRange();
                if (town == null) newPoint  = new SavePoint(loc, "Towny_" + count, TownyConfig.getInstance().getDefaultRegular(), TownyConfig.getInstance().getDefaultHovered());
                else {
                    newPoint = new SavePoint(loc, "Towny_" + count, town.getNormalOverride(), town.getHoveredOverride());
                    maxDist = town.getRange();
                }
                newPoint.setTowny(true);
                CompassInstance.getInstance().addSavePoint(passed.getUniqueId(), newPoint);
                newPoint.setMaxRange(maxDist);
            } catch (TownyException e) {
                e.printStackTrace();
            }
        }
    }

}
