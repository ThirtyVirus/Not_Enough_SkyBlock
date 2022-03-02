package thirtyvirus.skyblock.items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
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

public class stone_platform extends UberItem {

    public stone_platform(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) { }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) {
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "Creates a stone platform in the ");
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "air after travelling 10 blocks.");
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "Perfect for building below your");
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "island!");
    }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }
    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return leftClickAirAction(player, item); }

    public boolean rightClickAirAction(Player player, ItemStack item) {
        Egg egg = player.launchProjectile(Egg.class);
        countBlocks(player, egg);
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

    private static void countBlocks(Player launcher, Egg egg) {

        // delay placing platform until 10 blocks away from player
        if (launcher.getEyeLocation().distance(egg.getLocation()) < 10) {
            Utilities.scheduleTask(()-> countBlocks(launcher, egg), 2);
        }
        // place stone platform
        else {
            if (egg.getLocation().getWorld() == null) return;
            fillBlocks(egg.getLocation().add(-3,0,-3), egg.getLocation().add(3,0,3), Material.STONE_BRICKS);
            egg.remove();
        }

    }

    private static void fillBlocks(Location l1, Location l2, Material fillMaterial) {
        if (l1.getWorld() == null || l2.getWorld() == null || !l2.getWorld().equals(l2.getWorld())) return;

        int minX = (int) Math.min(l1.getX(), l2.getX());
        int minY = (int) Math.min(l1.getY(), l2.getY());
        int minZ = (int) Math.min(l1.getZ(), l2.getZ());

        int maxX = (int) Math.max(l1.getX(), l2.getX());
        int maxY = (int) Math.max(l1.getY(), l2.getY());
        int maxZ = (int) Math.max(l1.getZ(), l2.getZ());

        int x, y, z;

        for (x = minX; x <= maxX; x++)
            for (y = minY; y <= maxY; y++)
                for (z = minZ; z <= maxZ; z++)
                    if (!l1.getWorld().getBlockAt(x, y, z).getType().isSolid())
                        l1.getWorld().getBlockAt(x, y, z).setType(fillMaterial);

    }
}
