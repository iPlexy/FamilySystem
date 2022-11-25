package de.iplexy.family.utils;

import java.util.regex.Matcher;
import net.md_5.bungee.api.ChatColor;
import java.util.regex.Pattern;

public class ColorUtils
{
    private final Pattern hexPattern;
    
    public ColorUtils() {
        this.hexPattern = Pattern.compile("§#([A-Fa-f0-9]){6}");
    }
    
    public String applyColor(String message) {
        for (Matcher matcher = this.hexPattern.matcher(message); matcher.find(); matcher = this.hexPattern.matcher(message)) {
            final ChatColor hexColor = ChatColor.of(matcher.group().substring(1, matcher.group().length()));
            final String before = message.substring(0, matcher.start());
            final String after = message.substring(matcher.end());
            message = before + hexColor + after;
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
