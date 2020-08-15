package calebcompass.calebcompass.util;

import java.util.Map;
import java.util.HashMap;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

public class Util {

	private static final Map<Symbols, String> regular = new HashMap<>();

	private static final Map<Symbols, String> hovered = new HashMap<>();

	private static BarColor barColor;
	private static BarStyle barStyle;

	private static String symbolStart;
	private static String symbolEnd;

	public static Map<Symbols, String> getRegular() {
		return regular;
	}

	public static Map<Symbols, String> getHovered() {
		return hovered;
	}

	public static String getRegular(Symbols symbol) {
		return MessageUtil.colorize(regular.get(symbol));
	}

	public static String getHovered(Symbols symbol) {
		return MessageUtil.colorize(hovered.get(symbol));
	}

	public static BarColor getBarColor() {
		return barColor;
	}

	public static BarStyle getBarStyle() {
		return barStyle;
	}

	public static String getSymbolStart() {
		return MessageUtil.colorize(symbolStart);
	}

	public static String getSymbolEnd() {
		return MessageUtil.colorize(symbolEnd);
	}

	public static void setBarColor(String string) {
		try {
			barColor = BarColor.valueOf(string.toUpperCase());
		} catch (Exception ignored) {

		}
	}

	public static void setBarStyle(String string) {
		try {
			barStyle = BarStyle.valueOf(string.toUpperCase());
		} catch (Exception ignored) {

		}
	}

	public static void setSymbolStart(String start) {
		symbolStart = start;
	}

	public static void setSymbolEnd(String end) {
		symbolEnd = end;
	}

}
