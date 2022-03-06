package thirtyvirus.skyblock.items;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.helpers.*;

public class dead_bush_of_love extends UberItem {

    public dead_bush_of_love(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) { Utilities.addEnchantGlint(item); }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) {
        lore.add(ChatColor.GRAY + "This item was given to the kind");
        lore.add(ChatColor.GRAY + "souls who helped so much testing");
        lore.add(ChatColor.GRAY + "SkyBlock! Much love " + ChatColor.RED + "❤");

    }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }

    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }

    public boolean rightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }

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