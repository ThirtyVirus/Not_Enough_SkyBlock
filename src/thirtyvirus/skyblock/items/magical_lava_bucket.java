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
import org.bukkit.inventory.ItemStack;
import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.helpers.UberAbility;
import thirtyvirus.uber.helpers.UberCraftingRecipe;
import thirtyvirus.uber.helpers.UberRarity;
import thirtyvirus.uber.helpers.Utilities;

import java.util.List;

public class magical_lava_bucket extends UberItem {

    public magical_lava_bucket(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) {
        Utilities.addEnchantGlint(item); }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) {
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "This Magical Lava Bucket holds a");
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "tiny volcano, Easy infinite");
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "lava!");
    }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }
    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return leftClickAirAction(player, item); }

    public boolean rightClickAirAction(Player player, ItemStack item) {
        if (player.getInventory().getItemInMainHand().equals(item))
            Utilities.scheduleTask(()-> player.getInventory().setItemInMainHand(item.clone()), 1);
        if (player.getInventory().getItemInOffHand().equals(item))
            Utilities.scheduleTask(()-> player.getInventory().setItemInOffHand(item.clone()), 1);
        return false; }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return rightClickAirAction(player, item); }

    public boolean shiftLeftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean shiftRightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean middleClickAction(Player player, ItemStack item) { return false; }
    public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item) { return false; }
    public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) { return false; }
    public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) { return false; }
    public boolean activeEffect(Player player, ItemStack item) { return false; }
}
