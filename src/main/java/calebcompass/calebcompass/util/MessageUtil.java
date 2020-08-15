package calebcompass.calebcompass.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {

	private static final String FORMAT = "▘⬟⬟⬟⬟▙⬟⬟⬟⬟▚⬟⬟⬟⬟▛⬟⬟⬟⬟▜⬟⬟⬟⬟▝⬟⬟⬟⬟▞⬟⬟⬟⬟▟⬟⬟⬟⬟▘⬟⬟⬟⬟▙⬟⬟⬟⬟▚⬟⬟⬟⬟▛⬟⬟⬟⬟▜⬟⬟⬟⬟▝⬟⬟⬟⬟▞⬟⬟⬟⬟▟⬟⬟⬟⬟▘⬟⬟⬟⬟▙⬟⬟⬟⬟▚⬟⬟⬟⬟▛⬟⬟⬟⬟▜⬟⬟⬟⬟▝⬟⬟⬟⬟▞⬟⬟⬟⬟▟⬟⬟⬟⬟";

	private static final Pattern HEX_PATTERN = Pattern.compile("&(#\\w{6})");

	public static String replaceHovered(String insert) {
		insert = insert.replace("▚", Util.getHovered(Symbol.SOUTH))
				.replace("▛", Util.getHovered(Symbol.SOUTH_WEST))
				.replace("▜", Util.getHovered(Symbol.WEST))
				.replace("▝", Util.getHovered(Symbol.NORTH_WEST))
				.replace("▞", Util.getHovered(Symbol.NORTH))
				.replace("▟", Util.getHovered(Symbol.NORTH_EAST))
				.replace("▘", Util.getHovered(Symbol.EAST))
				.replace("▙", Util.getHovered(Symbol.SOUTH_EAST))
				.replace("⬟", Util.getHovered(Symbol.FILLED))
				.replace("▓", Util.getHovered(Symbol.TRACKER));
		return insert;
	}

	public static String replaceRegular(String insert) {
		insert = insert.replace("▚", Util.getRegular(Symbol.SOUTH))
				.replace("▛", Util.getRegular(Symbol.SOUTH_WEST))
				.replace("▜", Util.getRegular(Symbol.WEST))
				.replace("▝", Util.getRegular(Symbol.NORTH_WEST))
				.replace("▞", Util.getRegular(Symbol.NORTH))
				.replace("▟", Util.getRegular(Symbol.NORTH_EAST))
				.replace("▘", Util.getRegular(Symbol.EAST))
				.replace("▙", Util.getRegular(Symbol.SOUTH_EAST))
				.replace("⬟", Util.getRegular(Symbol.FILLED))
				.replace("▓", Util.getRegular(Symbol.TRACKER));
		return insert;
	}

	public static String getMessage(int dir, int goDir) {
		if (dir >= 40) dir -= 40;
		String overallMessage = FORMAT;
		if (goDir <= 90) {
			StringBuilder newView = new StringBuilder(overallMessage);
			if (goDir - 30  < 10 && goDir - 30 > 0) newView.setCharAt(goDir - 30, '▓');
			newView.setCharAt(goDir + 10, '▓');
			newView.setCharAt(goDir + 50, '▓');
			overallMessage = newView.toString();
		}
		String over = overallMessage.substring(dir, dir + 21);
		return Util.getSymbolStart() + replaceRegular(over.substring(0, 10)) + replaceHovered(String.valueOf(over.charAt(10))) + replaceRegular(over.substring(11)) + Util.getSymbolEnd();
	}

    public static String colorize(String message) {
        Matcher matcher = HEX_PATTERN.matcher(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message));
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group(1).toUpperCase()).toString());
        }

        return matcher.appendTail(buffer).toString();
    }

}
