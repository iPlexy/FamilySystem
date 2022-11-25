package de.iplexy.family;

import de.iplexy.family.commands.FamilyCommand;
import de.iplexy.family.groups.Family;
import de.iplexy.family.listener.InventoryClickListener;
import de.iplexy.family.utils.ColorUtils;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class FamilySystem extends JavaPlugin {

    private static FamilySystem plugin;
    public static HeadDatabaseAPI headDatabaseAPI;
    public static String prefix;
    public static ColorUtils colorUtils;

    @Override
    public void onEnable() {
        plugin=this;
        long startupTimeStamp = System.currentTimeMillis();
        headDatabaseAPI = new HeadDatabaseAPI();
        colorUtils = new ColorUtils();
        prefix= colorUtils.applyColor("§8┃ §#45e9ffFamilies §8» §7");
        registerCommands();
        registerListener();
        Family.loadAllFamilies();
        long startupTime = System.currentTimeMillis() - startupTimeStamp;
        sendConsoleMessage("Plugin loaded in §e"+startupTime+"§7ms.");
    }

    @Override
    public void onDisable() {
        Family.saveAllFamilies();
    }

    private void registerCommands(){
        getPlugin().getCommand("Family").setExecutor(new FamilyCommand());
    }

    private void registerListener(){
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
    }


    public static FamilySystem getPlugin(){
        return plugin;

    }

    public static void sendConsoleMessage(String message){
        Bukkit.getConsoleSender().sendMessage("§f[§3FamilySystem§f] §7"+message);
    }


}
