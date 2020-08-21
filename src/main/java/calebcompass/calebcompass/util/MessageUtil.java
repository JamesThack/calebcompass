package calebcompass.calebcompass.util;

import calebcompass.calebcompass.SavePoints.SavePoint;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
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
				.replace("▓", Util.getHovered(Symbol.WAYPOINT))
				.replace("▨", Util.getHovered(Symbol.TRACKER));
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
				.replace("▓", Util.getRegular(Symbol.WAYPOINT))
				.replace("▨", Util.getRegular(Symbol.TRACKER));
		return insert;
	}

	public static String getMessage(int dir, int goDir, ArrayList<SavePoint> extraPoints, Location playerLoc) {
		if (dir >= 40) dir -= 40;
		String overallMessage = FORMAT;



		if (extraPoints != null) {
			for (SavePoint cur : extraPoints) {
				if (!cur.getLoc1().getWorld().equals(playerLoc.getWorld())) continue;
				// our target location (Point B)
				Vector target = cur.getLoc1().toVector();
				// set the origin's direction to be the direction vector between point A and B.
				playerLoc.setDirection(target.subtract(playerLoc.toVector()));
				float playerYaw = playerLoc.getYaw();
				int goDir2 = Math.round(playerYaw / 9);
				StringBuilder newView = new StringBuilder(overallMessage);
				if (goDir2 - 30  < 10 && goDir2 - 30 > 0) newView.setCharAt(goDir2 - 30, '▓');
				newView.setCharAt(goDir2 + 10, '▓');
				newView.setCharAt(goDir2 + 50, '▓');
				overallMessage = newView.toString();
			}
		}

		if (goDir <= 90) {
			StringBuilder newView = new StringBuilder(overallMessage);
			if (goDir - 30  < 10 && goDir - 30 > 0) newView.setCharAt(goDir - 30, '▨');
			newView.setCharAt(goDir + 10, '▨');
			newView.setCharAt(goDir + 50, '▨');
			overallMessage = newView.toString();
		}


		String over = overallMessage.substring(dir, dir + 21);
		return Util.getSymbolStart() + replaceRegular(over.substring(0, 10)) + replaceHovered(String.valueOf(over.charAt(10))) + replaceRegular(over.substring(11)) + Util.getSymbolEnd();
	}

	public static String colorize(String message) {
		return message.replace("&", "§");
	}

}
