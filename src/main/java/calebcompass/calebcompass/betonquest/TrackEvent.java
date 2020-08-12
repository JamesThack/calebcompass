package calebcompass.calebcompass.betonquest;

import calebcompass.calebcompass.events.CompassInstance;
import calebcompass.calebcompass.events.LocObs;
import org.bukkit.Location;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.QuestEvent;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import pl.betoncraft.betonquest.utils.LocationData;
import pl.betoncraft.betonquest.utils.PlayerConverter;

public class TrackEvent extends QuestEvent {

    private LocationData trackLoc;

    public TrackEvent(Instruction instruction) throws InstructionParseException {
        super(instruction, false);
        trackLoc = instruction.getLocation();

    }

    @Override
    protected Void execute(String playerID) throws QuestRuntimeException {
        Location telLoc = trackLoc.getLocation(playerID);
        Player player = PlayerConverter.getPlayer(playerID);
        if (CompassInstance.getInstance().getPlayerLocOb(player) == null) CompassInstance.getInstance().addLocOb(player, player.getLocation(), telLoc);
        CompassInstance.getInstance().getPlayerLocOb(player).setOrigin(player.getLocation());
        CompassInstance.getInstance().getPlayerLocOb(player).setCertainEnd(telLoc);
        CompassInstance.getInstance().getPlayerLocOb(player).setTracking(true);
        CompassInstance.getInstance().saveData();
        return null;
    }
}
