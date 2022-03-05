package thirtyvirus.skyblock.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.helpers.UberAbility;
import thirtyvirus.uber.helpers.UberCraftingRecipe;
import thirtyvirus.uber.helpers.UberRarity;
import thirtyvirus.uber.helpers.Utilities;

import java.math.BigDecimal;
import java.util.List;

public class emerald_blade extends UberItem {

    public emerald_blade(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe, String raritySuffix) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe, raritySuffix);
    }
    public void onItemStackCreate(ItemStack item) { }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) {
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "A powerful blade made from pure");
        lore.add(ChatColor.RESET + "" + ChatColor.DARK_GREEN + "Emeralds" + ChatColor.GRAY  + ". This blade becomes ");
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "stronger as you carry more");
        lore.add(ChatColor.RESET + "" + ChatColor.GOLD + "gold" + ChatColor.GRAY + " in your inventory");
        lore.add("");

        float damageBuff = round((float)Utilities.getIntFromItem(item, "dmgbuff") / 32, 2);
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Current Damage Bonus: " + ChatColor.GREEN + damageBuff);
    }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }

    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean rightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { event.setCancelled(false); return false; }
    public boolean shiftLeftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean shiftRightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean middleClickAction(Player player, ItemStack item) { return false; }

    public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item) {
        float damageBuff = (float)Utilities.getIntFromItem(item, "dmgbuff") / 64;
        event.setDamage(event.getDamage() + damageBuff);
        return false;
    }

    public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) { return false; }
    public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) { return false; }

    // check inventory for the amount of gold, update damage buff as necessary
    public boolean activeEffect(Player player, ItemStack item) {
        int damagebuff = countItemsInInventory(player.getInventory(), new ItemStack(Material.GOLD_INGOT));
        Utilities.storeIntInItem(item, damagebuff, "dmgbuff");
        updateLore(item);

        return false;
    }

    // count the number of a certain item in an inventory
    public static int countItemsInInventory(Inventory inventory, ItemStack item) {
        int total = 0;
        for (int counter = 0; counter < 36; counter++){
            if (inventory.getItem(counter) == null) continue;
            ItemStack item1 = item.clone(); item1.setAmount(1);
            ItemStack item2 = inventory.getItem(counter).clone(); item2.setAmount(1);

            if (item1.equals(item2)){
                total += inventory.getItem(counter).getAmount();
            }

        }

        return total;
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

}
