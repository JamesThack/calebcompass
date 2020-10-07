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

	public static String colourise(String message) {
		Matcher matcher = HEX_PATTERN.matcher(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', message));
		StringBuffer buffer = new StringBuffer();

		while (matcher.find()) {
			matcher.appendReplacement(buffer, net.md_5.bungee.api.ChatColor.of(matcher.group(1).toUpperCase()).toString());
		}

		return matcher.appendTail(buffer).toString();
	}

}
