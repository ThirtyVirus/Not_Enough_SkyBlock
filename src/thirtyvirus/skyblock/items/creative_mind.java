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

import java.time.LocalDateTime;
import java.util.List;

public class creative_mind extends UberItem {

    private String default_to = "" + ChatColor.RED + "[" + ChatColor.WHITE + "YOUTUBE" + ChatColor.RED + "] thirtyvirus";
    private String default_from = "" + ChatColor.RED + "[ADMIN] Jayavarmen";
    private int default_edition = 1;
    private LocalDateTime time = LocalDateTime.now();

    public creative_mind(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) {
        Utilities.addEnchantGlint(item); }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) {
        String to = Utilities.getStringFromItem(item, "to");
        if (to == null) to = default_to;
        String from = Utilities.getStringFromItem(item, "from");
        if (from == null) from = default_from;
        int edition = Utilities.getIntFromItem(item, "edition");
        if (edition == 0) edition = default_edition;
        to = to.replaceAll(",", " ");
        from = from.replaceAll(",", " ");

        lore.add("" + ChatColor.RESET + ChatColor.GRAY + ChatColor.ITALIC + "Original, visionary, inventive");
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + ChatColor.ITALIC + "and innovative! I would even add");
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + ChatColor.ITALIC + "ingenious, clever! A");
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + ChatColor.ITALIC + "masterpiece!");
        lore.add("");
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "To: " + ChatColor.translateAlternateColorCodes('&', to));
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "From: " + ChatColor.translateAlternateColorCodes('&', from));
        lore.add("");
        lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + "Edition #" + edition);

        String formattedMonth = time.getMonth().toString().toLowerCase();
        if (formattedMonth.length() > 0) formattedMonth = formattedMonth.substring(0, 1).toUpperCase() + formattedMonth.substring(1);
        lore.add("" + ChatColor.RESET + ChatColor.DARK_GRAY + formattedMonth + " " + time.getYear());

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
