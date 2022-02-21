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

public class bridge_egg extends UberItem {

    public bridge_egg(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) { }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) {
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "Creates a path of stone blocks");
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "as it travels through the air!");
    }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }
    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return leftClickAirAction(player, item); }

    public boolean rightClickAirAction(Player player, ItemStack item) {
        Egg egg = player.launchProjectile(Egg.class);
        Utilities.scheduleTask(()->placeBridge(egg, 25), 3);
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

    private void placeBridge(Egg egg, int counter) {
        if (egg.isDead() || counter <= 0) return;

        Location old = egg.getLocation().clone();
        Utilities.scheduleTask(()->fillBlocks(old.clone().add(-1,-2,-1), old.clone().add(1,-2,1), Material.STONE), 3);
        Utilities.scheduleTask(()->placeBridge(egg, counter - 1), 1);
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
                    if (!l1.getWorld().getBlockAt(l1).getType().isSolid())
                        l1.getWorld().getBlockAt(x, y, z).setType(fillMaterial);

    }

}
