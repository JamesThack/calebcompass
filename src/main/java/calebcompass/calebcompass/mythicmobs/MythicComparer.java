package calebcompass.calebcompass.mythicmobs;

import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import org.bukkit.entity.Entity;

public class MythicComparer {

    private static BukkitAPIHelper api = new BukkitAPIHelper();

    public static boolean isEntityMythic(Entity entity) {
        return api.isMythicMob(entity);
    }

    public static String getMythicMobName(Entity entity) {
        return api.getMythicMobInstance(entity).getType().getInternalName();
    }
}
