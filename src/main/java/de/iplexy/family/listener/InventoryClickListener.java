package de.iplexy.family.listener;

import de.iplexy.family.groups.User;
import de.iplexy.family.utils.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static de.iplexy.family.FamilySystem.*;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getView().getTitle().contains(prefix+"Ranks of ")) {
            Player p = (Player) e.getWhoClicked();
            e.setCancelled(true);
            if (e.getRawSlot() == 31) {

                p.closeInventory();
                AnvilGUI.openRankName(p);

            }
            if(e.getRawSlot()>9&&e.getRawSlot()<26&&e.getCurrentItem()!=null&&e.getCurrentItem().getType().equals(Material.PAPER)){
                User user = new User(p.getUniqueId());
                String rank = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).replace("» ","").split(" ")[0];
                Integer rankID = Integer.parseInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).replace("» "+rank+" (","").replace(")", ""));
                if(e.getClick().equals(ClickType.RIGHT)){
                    if(user.getRankID()==14){
                        Inventory inv = Bukkit.createInventory(p, InventoryType.HOPPER,prefix+"§fEdit "+rank);

                        ItemStack infoItem = new ItemStack(Material.MOJANG_BANNER_PATTERN);
                        ItemMeta infoMeta = infoItem.getItemMeta();
                        infoMeta.setDisplayName(e.getCurrentItem().getItemMeta().getDisplayName());
                        infoItem.setItemMeta(infoMeta);
                        inv.setItem(2, infoItem);

                        ItemStack deleteItem = headDatabaseAPI.getItemHead("22955");
                        ItemMeta deleteMeta = deleteItem.getItemMeta();
                        deleteMeta.setDisplayName("§7» §cDelete "+rank);
                        deleteMeta.setLore(Arrays.asList("§7Click to delete the rank"));
                        deleteItem.setItemMeta(deleteMeta);
                        inv.setItem(0, deleteItem);

                        ItemStack renameItem = new ItemStack(Material.NAME_TAG);
                        ItemMeta renameMeta = renameItem.getItemMeta();
                        renameMeta.setDisplayName("§7» §6Rename "+rank);
                        renameMeta.setLore(Arrays.asList("§7Click to rename the rank"));
                        renameItem.setItemMeta(renameMeta);
                        inv.setItem(4, renameItem);

                        p.openInventory(inv);
                    }
                }
            }
        }
    }
}
