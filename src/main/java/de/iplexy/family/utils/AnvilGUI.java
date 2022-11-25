package de.iplexy.family.utils;

import de.iplexy.family.groups.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.regex.Pattern;

import static de.iplexy.family.FamilySystem.getPlugin;

public class AnvilGUI {

    public static void openRankName(Player p) {
        new net.wesjd.anvilgui.AnvilGUI.Builder()
                .onComplete((player, text) -> {                                    //called when the inventory output slot is clicked
                    if (text.length() < 3 || text.length() > 16) {
                        player.sendMessage("The name must be between 3 and 16 characters");
                        return net.wesjd.anvilgui.AnvilGUI.Response.close();
                    } else if (Pattern.compile("[^A-Za-z0-9]").matcher(text).find()) {
                        player.sendMessage("The name may not contain special characters");
                        return net.wesjd.anvilgui.AnvilGUI.Response.close();
                    }
                    openRankWeight(p, text);
                    return net.wesjd.anvilgui.AnvilGUI.Response.close();
                })
                .text("§fName of rank")
                .itemLeft(new ItemStack(Material.NAME_TAG))
                .title("§fName of rank")
                .plugin(getPlugin())
                .open(p);
    }

    private static void openRankWeight(Player p, String rankName) {
        User user = new User(p.getUniqueId());
        new net.wesjd.anvilgui.AnvilGUI.Builder()
                .onComplete((player, text) -> {                                    //called when the inventory output slot is clicked
                    if (isNumeric(text) && Integer.parseInt(text) < 14 && Integer.parseInt(text) > 1) {

                        if (user.getFamily().createRank(rankName, Integer.parseInt(text))) {
                            player.sendMessage("You created the rank " + rankName);
                        } else {
                            player.sendMessage("There is already a rank with that weight");
                        }
                    } else {
                        player.sendMessage("§3" + text + "§7 is not a valid number between 2 and 13.");
                    }
                    return net.wesjd.anvilgui.AnvilGUI.Response.close();
                })
                .text("§fEnter weight here")
                .itemLeft(new ItemStack(Material.NAME_TAG))
                .title("§fWeight of Rank (2-13)")
                .plugin(getPlugin())
                .open(p);
    }

    public static void renameRank(Player p, Integer rankID, String rankName) {
        User user = User.getUser(p.getUniqueId());
        new net.wesjd.anvilgui.AnvilGUI.Builder()
                .onComplete((player, text) -> {                                    //called when the inventory output slot is clicked
                    if (text.length() < 3 || text.length() > 16) {
                        player.sendMessage("The name must be between 3 and 16 characters");
                        return net.wesjd.anvilgui.AnvilGUI.Response.close();
                    } else if (Pattern.compile("[^A-Za-z0-9]").matcher(text).find()) {
                        player.sendMessage("The name may not contain special characters");
                        return net.wesjd.anvilgui.AnvilGUI.Response.close();
                    }
                    user.getFamily().renameRank(rankID, text);
                    player.sendMessage("Der Rank " + rankName + " wurde zu " + text + " geändert.");
                    return net.wesjd.anvilgui.AnvilGUI.Response.close();
                })
                .text(rankName)
                .itemLeft(new ItemStack(Material.NAME_TAG))
                .title("§fRename " + rankName)
                .plugin(getPlugin())
                .open(p);
    }


    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
