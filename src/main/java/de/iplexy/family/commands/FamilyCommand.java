package de.iplexy.family.commands;

import de.iplexy.family.groups.Family;
import de.iplexy.family.FamilySystem;
import de.iplexy.family.groups.User;
import de.iplexy.family.utils.FamilySerialzier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.StringUtil;

import java.nio.Buffer;
import java.util.*;

import static de.iplexy.family.FamilySystem.getPlugin;
import static de.iplexy.family.FamilySystem.prefix;

public class FamilyCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            FamilySystem.sendConsoleMessage("This command is only for players.");
            return true;
        }
        Player p = (Player) sender;
        User user = new User(p.getUniqueId());
        if (args[0].equalsIgnoreCase("create")) {
            if (args[1] != null) {
                if (!user.hasFamily()) {
                    Family family = new Family(args[1], p);
                    p.sendMessage("Family created");
                    FamilySerialzier.serialize(family);
                    family.cacheFamily();
                } else {
                    p.sendMessage("You are already in a family");
                }
            }
        } else if (args[0].equalsIgnoreCase("ranks")) {
            if (user.hasFamily()) {
                Family family = user.getFamily();
                Inventory inv = Bukkit.createInventory(p, 36, prefix+"Ranks of " + user.getFamily().getName());
                List<Integer> frameSlots = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 28, 29, 30, 32, 33, 34, 35);
                ItemStack addItem;
                ItemMeta addMeta;
                if (user.getRankID().equals(14)) {
                    addItem = FamilySystem.headDatabaseAPI.getItemHead("9885");
                    addMeta = addItem.getItemMeta();
                    addMeta.setDisplayName("§7» §aCreate a rank");
                    addMeta.setLore(Arrays.asList("§7Click here to create a new rank"));
                } else {
                    addItem = FamilySystem.headDatabaseAPI.getItemHead("19276");
                    addMeta = addItem.getItemMeta();
                    addMeta.setDisplayName("§7» §cCreate a rank");
                    addMeta.setLore(Arrays.asList("§7You don't have enough permissions to do that"));
                }
                addMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                addItem.setItemMeta(addMeta);
                inv.setItem(31, addItem);
                ItemStack frameItem = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
                ItemMeta frameMeta = frameItem.getItemMeta();
                frameMeta.setDisplayName("§7");
                frameMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                frameItem.setItemMeta(frameMeta);
                for(Integer slot : frameSlots){
                    inv.setItem(slot,frameItem);
                }
                for (Integer id : family.getRanks().keySet().stream().sorted(Comparator.reverseOrder()).toList()) {
                    String rank = family.getRanks().get(id);
                    ItemStack itm = new ItemStack(Material.PAPER);
                    ItemMeta meta = itm.getItemMeta();
                    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    meta.setDisplayName("§7» §a" + rank + " §7(§3" + id + "§7)");
                    List<String> lore = new ArrayList<>();
                    lore.add("§7« §eLeft click §7to see all §3" + rank);
                    if (user.getRankID().equals(14)) {
                        lore.add("§7» §cRight click §7to edit §3" + rank);
                    }
                    meta.setLore(lore);
                    itm.setItemMeta(meta);
                    inv.addItem(itm);
                }
                p.openInventory(inv);
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        final List<String> completions = new ArrayList<>();
        List<String> subcommands = new ArrayList<>();
        if (args.length == 1) {
            subcommands = Arrays.asList("delete", "create", "invite", "ranks");
        } else {
            subcommands = Arrays.asList("Lol");
        }
        StringUtil.copyPartialMatches(args[0], subcommands, completions);
        Collections.sort(completions);
        return completions;
    }

}
