package calebcompass.calebcompass.betonquest;

import calebcompass.calebcompass.CalebCompass;
import calebcompass.calebcompass.SavePoints.SavePointConfig;
import calebcompass.calebcompass.util.CompassInstance;
import calebcompass.calebcompass.util.CompassLocation;
import org.bukkit.entity.Player;
import org.betonquest.betonquest.Instruction;
import org.betonquest.betonquest.api.QuestEvent;
import org.betonquest.betonquest.exceptions.InstructionParseException;
import org.betonquest.betonquest.exceptions.QuestRuntimeException;
import org.betonquest.betonquest.utils.PlayerConverter;

public class Focus extends QuestEvent {

    private String name;

    public Focus(Instruction instruction) throws InstructionParseException {
        super(instruction, false);
        name = instruction.getPart(1);
        if (!SavePointConfig.getInstance().pointExistsExplicit(name)) throw new InstructionParseException("Enter a valid waypoint");

    }

    @Override
    protected Void execute(String playerID) throws QuestRuntimeException {
        Player player = PlayerConverter.getPlayer(playerID);
        CompassLocation loc = CompassInstance.getInstance().getCompassLocation(player);
        if (player.getWorld().equals(SavePointConfig.getInstance().getPointFromName(name).getLoc1().getWorld())) {
            if (loc == null) {
                loc = new CompassLocation(player, player.getLocation(), SavePointConfig.getInstance().getPointFromName(name).getLoc1());
                CompassInstance.getInstance().addCompassLocation(loc);
            }
            loc.setTarget(SavePointConfig.getInstance().getPointFromName(name).getLoc1());
            loc.setOrigin(player.getLocation());
            loc.setTracking(true);
            CompassInstance.getInstance().saveData();
        }
        return null;
    }

}
