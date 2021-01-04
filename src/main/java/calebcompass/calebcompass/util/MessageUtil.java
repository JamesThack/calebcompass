package calebcompass.calebcompass.util;

import calebcompass.calebcompass.SavePoints.SavePoint;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageUtil {

    public static String colourise(String message) {
		return message.replace("&", "§");
    }

    public static String replaceVariable(String message, String placeholder, String replacement) {
        Pattern p = Pattern.compile(placeholder);
        Matcher m = p.matcher(message);
        return m.replaceAll(replacement);
    }


}