package calebcompass.calebcompass.betonquest;

import calebcompass.calebcompass.SavePoints.SavePointConfig;
import calebcompass.calebcompass.util.CompassInstance;
import calebcompass.calebcompass.util.LangManager;
import calebcompass.calebcompass.util.MessageUtil;
import org.bukkit.entity.Player;
import org.betonquest.betonquest.Instruction;
import org.betonquest.betonquest.api.QuestEvent;
import org.betonquest.betonquest.exceptions.InstructionParseException;
import org.betonquest.betonquest.exceptions.QuestRuntimeException;
import org.betonquest.betonquest.utils.PlayerConverter;

public class TogglePoint extends QuestEvent {

    private boolean toToggle;
    private String name;

    public TogglePoint(Instruction instruction) throws InstructionParseException {
        super(instruction, false);
        name = instruction.getPart(1);
        if (!SavePointConfig.getInstance().pointExistsExplicit(name)) throw new InstructionParseException(MessageUtil.colourise(LangManager.getInstance().getString("waypoint-no-valid")));
        String ins = instruction.getPart(2);
        if (ins.equalsIgnoreCase("enable")) toToggle = true;
        if (ins.equalsIgnoreCase("disable")) toToggle = false;
        if (!ins.equalsIgnoreCase("disable") && !ins.equalsIgnoreCase("enable")) throw new InstructionParseException(LangManager.getInstance().getString("bq-missing-toggle"));

    }

    @Override
    protected Void execute(String playerID) throws QuestRuntimeException {
        Player player = PlayerConverter.getPlayer(playerID);
        SavePointConfig.getInstance().togglePlayerPoint(player.getUniqueId(), name, toToggle);
        CompassInstance.getInstance().saveData();
        SavePointConfig.getInstance().saveData();
        CompassInstance.getInstance().load();
        return null;
    }

}