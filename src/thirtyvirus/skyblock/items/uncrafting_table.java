package thirtyvirus.skyblock.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
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
import thirtyvirus.uber.helpers.*;

public class uncrafting_table extends UberItem {

    public static final ItemStack EMPTY_SLOT_ITEM = Utilities.nameItem(Material.BLACK_STAINED_GLASS_PANE, " ");
    public static final ItemStack UNCRAFTING_SLOT_ITEM = Utilities.loreItem(Utilities.nameItem(Material.ANVIL, ChatColor.RED + "UnCraft Item"), Arrays.asList(ChatColor.GRAY + "Disassemble item into the", ChatColor.GRAY + "materials required to craft it", "", ChatColor.RED + "WARNING: CANNOT BE UNDONE"));
    public static final List<Integer> REVERSE_CRAFTING_MENU_EXCEPTIONS = Arrays.asList(14,15,16,23,24,25,32,33,34);

    public static final List<ItemStack> customItems = Arrays.asList(EMPTY_SLOT_ITEM, UNCRAFTING_SLOT_ITEM);

    public uncrafting_table(ItemStack itemStack, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(itemStack, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) { Utilities.addEnchantGlint(item); }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) { }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }

    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return leftClickAirAction(player, item); }
    public boolean rightClickAirAction(Player player, ItemStack item) {
        player.openInventory(createReverseCraftingMenu());
        Utilities.playSound(ActionSound.CLICK, player);
        return true;
    }
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

    // create the "main" custom crafting menu
    private static Inventory createReverseCraftingMenu() {
        Inventory i = Bukkit.createInventory(null, 45, "UberItems - UnCraft Item");

        for (int counter = 0; counter < 45; counter++) {
            if (!(REVERSE_CRAFTING_MENU_EXCEPTIONS.contains(counter) || counter == 19)) i.setItem(counter, EMPTY_SLOT_ITEM);
        }
        i.setItem(21, UNCRAFTING_SLOT_ITEM);

        return i;
    }
}
