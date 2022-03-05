package thirtyvirus.skyblock.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
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

public class aspect_of_the_dragons extends UberItem {

    public aspect_of_the_dragons(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe, String raritySuffix) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe, raritySuffix);
    }
    public void onItemStackCreate(ItemStack item) { }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) { }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }

    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }

    // teleport ability
    public boolean rightClickAirAction(Player player, ItemStack item) {
        knockbackAttack(player, 4,10);
        player.getWorld().playSound(player.getLocation().add(0,1,0), Sound.ENTITY_ENDER_DRAGON_GROWL, 1,1);

        return true;
    }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return rightClickAirAction(player, item); }
    public boolean shiftLeftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }

    public boolean shiftRightClickAirAction(Player player, ItemStack item) { return rightClickAirAction(player, item); }
    public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return rightClickAirAction(player, item); }
    public boolean middleClickAction(Player player, ItemStack item) { return false; }
    public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item) {
        Utilities.repairItem(item);
        return false;
    }
    public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) {
        Utilities.repairItem(item);
        return false;
    }
    public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) { return false; }
    public boolean activeEffect(Player player, ItemStack item) { return false; }

    private void knockbackAttack(Player player, int damage, float range) {
        Vector looking = player.getLocation().getDirection();
        List<Entity> entities = player.getNearbyEntities(range, range, range);

        for (Entity e : entities) {
            // test if the entity can be damaged and isnt the player
            if (e instanceof LivingEntity && !e.equals(player)) {
                ((LivingEntity) e).damage(damage);
                Vector direction = e.getLocation().toVector().subtract(player.getLocation().toVector());
                double angle = looking.angle(direction);

                // scale damage depending on how close you are to the mob
                double distance = player.getLocation().distance(e.getLocation());
                double kb = 5 - (5 * distance / (range - 1) / 2);

                if (angle < 1) {
                    double x = direction.getX() / Math.abs(direction.getX());
                    double y = direction.getY();
                    double z = direction.getZ() / Math.abs(direction.getZ());
                    direction = new Vector(x, y, z);

                    direction.multiply(kb);
                    e.setVelocity(direction);
                }
            }
        }
    }
}
