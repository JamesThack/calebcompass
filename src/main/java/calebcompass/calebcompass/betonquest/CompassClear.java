package calebcompass.calebcompass.betonquest;

import org.bukkit.entity.Player;

import pl.betoncraft.betonquest.Instruction;
import pl.betoncraft.betonquest.api.QuestEvent;
import pl.betoncraft.betonquest.utils.PlayerConverter;
import pl.betoncraft.betonquest.exceptions.QuestRuntimeException;
import pl.betoncraft.betonquest.exceptions.InstructionParseException;

import calebcompass.calebcompass.util.CompassInstance;

public class CompassClear extends QuestEvent {

	public CompassClear(Instruction instruction) throws InstructionParseException {
		super(instruction, false);
	}

	@Override
	protected Void execute(String playerID) throws QuestRuntimeException {
		Player player = PlayerConverter.getPlayer(playerID);
		if (CompassInstance.getInstance().getCompassLocation(player) == null) return null;
		CompassInstance.getInstance().getCompassLocation(player).setTracking(false);
		return null;
	}

}
