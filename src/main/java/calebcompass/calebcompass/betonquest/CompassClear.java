package calebcompass.calebcompass.betonquest;

import org.betonquest.betonquest.Instruction;
import org.betonquest.betonquest.api.QuestEvent;
import org.betonquest.betonquest.exceptions.InstructionParseException;
import org.betonquest.betonquest.exceptions.QuestRuntimeException;
import org.betonquest.betonquest.utils.PlayerConverter;
import org.bukkit.entity.Player;
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
		CompassInstance.getInstance().saveData();
		return null;
	}

}
