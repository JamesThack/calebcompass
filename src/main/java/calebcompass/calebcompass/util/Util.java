package calebcompass.calebcompass.util;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;

import java.util.HashMap;
import java.util.Map;

public class Util {

	private static final Map<Symbol, String> regular = new HashMap<>();

	private static final Map<Symbol, String> hovered = new HashMap<>();

	private static BarColor barColor;
	private static BarStyle barStyle;

	private static String symbolStart;
	private static String symbolEnd;

	public static Map<Symbol, String> getRegular() {
		return regular;
	}

	public static Map<Symbol, String> getHovered() {
		return hovered;
	}

	public static String getRegular(Symbol symbol) {
		return MessageUtil.colourise(regular.get(symbol));
	}

	public static String getHovered(Symbol symbol) {
		return MessageUtil.colourise(hovered.get(symbol));
	}

	public static BarColor getBarColor() {
		return barColor;
	}

	public static BarStyle getBarStyle() {
		return barStyle;
	}

	public static String getSymbolStart() {
		return MessageUtil.colourise(symbolStart);
	}

	public static String getSymbolEnd() {
		return MessageUtil.colourise(symbolEnd);
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
