package calebcompass.calebcompass.citizens;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;

public class CitizensComparers {

    public static boolean isEntityNPC(Entity entity) {
        return entity.hasMetadata("NPC");
    }

    public static String getNPCID(Entity entity) {
        NPC npc = CitizensAPI.getNPCRegistry().getNPC(entity);
        return String.valueOf(npc.getId());
    }
}
