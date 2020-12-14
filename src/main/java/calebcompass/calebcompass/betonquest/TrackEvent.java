package calebcompass.calebcompass.betonquest;

import calebcompass.calebcompass.util.CompassInstance;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.QuestEvent;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import pl.betoncraft.betonquest.utils.PlayerConverter;
import pl.betoncraft.betonquest.utils.location.CompoundLocation;

public class TrackEvent extends QuestEvent {

	private CompoundLocation trackLoc;

	public TrackEvent(Instruction instruction) throws InstructionParseException {
		super(instruction, false);
		trackLoc = instruction.getLocation();
	}

	@Override
	protected Void execute(String playerID) throws QuestRuntimeException {
		Location tracked = trackLoc.getLocation(playerID);
		Player player = PlayerConverter.getPlayer(playerID);

		if (CompassInstance.getInstance().getCompassLocation(player) == null) {
			CompassInstance.getInstance().addCompassLocation(player, player.getLocation(), tracked);
		}

		CompassInstance.getInstance().getCompassLocation(player).setOrigin(player.getLocation());
		CompassInstance.getInstance().getCompassLocation(player).setTarget(tracked);
		CompassInstance.getInstance().getCompassLocation(player).setTracking(true);
		CompassInstance.getInstance().saveData();
		return null;
	}

}
