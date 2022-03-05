package thirtyvirus.skyblock.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ExperienceOrb;
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
import java.util.Random;

public class stonk extends UberItem {

    Random rand = new Random();

    public stonk(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe, String raritySuffix) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe, raritySuffix);
    }
    public void onItemStackCreate(ItemStack item) { item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6); }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) {
        lore.add("");
        lore.add(ChatColor.GRAY + "When mining End Stone with this");
        lore.add(ChatColor.GRAY + "pickaxe, Endermites won't spawn");
        lore.add(ChatColor.GRAY + "and experience will be dropped!");
    }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }

    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }

    public boolean rightClickAirAction(Player player, ItemStack item) {

        if (Utilities.enforceCooldown(player, "miningspeed", 120, item, true)) return false;

        item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 10);
        Utilities.informPlayer(player, ChatColor.GREEN + "You used your " + ChatColor.GOLD + "Mining Speed Boost" + ChatColor.GREEN + " Pickaxe Ability!");
        Utilities.scheduleTask(() -> {
            item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6);
            Utilities.warnPlayer(player, "Your Mining Speed Boost has expired!");
            }, 400);

        return true;
    }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { event.setCancelled(false); return rightClickAirAction(player, item); }

    public boolean shiftLeftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean shiftRightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean middleClickAction(Player player, ItemStack item) { return false; }
    public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item) {
        Utilities.repairItem(item);
        return false;
    }
    public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) {
        // repair pickaxe each time it is used
        Utilities.repairItem(item);
        if (block.getType() == Material.END_STONE)
            ((ExperienceOrb)block.getWorld().spawn(block.getLocation(), ExperienceOrb.class)).setExperience(rand.nextInt(3) + 1);

        return false;
    }
    public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) { return false; }
    public boolean activeEffect(Player player, ItemStack item) { return false; }
}
