package items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.helpers.UberAbility;
import thirtyvirus.uber.helpers.UberCraftingRecipe;
import thirtyvirus.uber.helpers.UberRarity;
import thirtyvirus.uber.helpers.Utilities;

import java.util.List;

public class runaans_bow extends UberItem {

    public runaans_bow(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) { }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) { }
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
    public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item) { return false; }
    public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) { return false; }
    public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) { return false; }
    public boolean activeEffect(Player player, ItemStack item) {

        for (Entity e : player.getNearbyEntities( 200, 200, 200)) {

            if (e instanceof Arrow && Utilities.getEntityTag(e, "homing").equals(player.getName())) {

                Entity other = getClosestEnemy(player, e.getLocation());
                if (other == null) return false;

                Location loc = other.getLocation().subtract(e.getLocation()).add(0,1,0);
                e.setVelocity(loc.toVector().normalize());
            }
        }

        return false;
    }

    private LivingEntity getClosestEnemy(Entity exception, Location location) {
        double closest = 100;
        LivingEntity closestEntity = null;
        if (location.getWorld() == null) return null;

        for (Entity e : location.getWorld().getNearbyEntities(location, 5,5,5)) {
            if (e instanceof LivingEntity) {
                // skip exception (usually the player that shot the homing arrow)
                if (e instanceof Player && e.equals(exception)) continue;

                double testClose = location.distance(e.getLocation());
                if (testClose < closest && !isObtructed(location, e.getLocation())) {
                    closestEntity = (LivingEntity) e;
                    closest = testClose;
                }
            }
        }

        return closestEntity;
    }

    private static boolean isObtructed(Location a, Location b) {
        // A to B
        Vector v = b.toVector().subtract(a.toVector());
        double j = Math.floor(v.length());
        v.multiply(1/v.length()); // convert v to a unit vector
        for (int i = 0; i<=j; i++) {
            v = b.toVector().subtract(a.toVector());
            v.multiply(1/v.length());
            Block block = a.getWorld().getBlockAt((a.toVector().add(v.multiply(i))).toLocation(a.getWorld()));
            if (!block.getType().equals(Material.AIR) && !block.getType().equals(Material.CAVE_AIR) && !block.getType().equals(Material.WATER)) {
                return true;
            }
        }
        return false;
    }
}
