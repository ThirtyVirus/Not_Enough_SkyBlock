package thirtyvirus.skyblock.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.helpers.*;

public class basket_of_seeds extends UberItem {

    private List<Material> growable = Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR);

    public basket_of_seeds(ItemStack itemStack, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(itemStack, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) { }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) { }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }

    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }

    public boolean rightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) {

        // TODO inventory / seed management
        // farmland crops (wheat, carrot, potato)
        if (block.getType() == Material.FARMLAND) {
            placeSeed(block, getCardinalDirection(player), block.getType(), Material.WHEAT);
            return false;
        }
        // sugarcane
        else if (block.getType() == Material.DIRT || block.getType() == Material.GRASS_BLOCK || block.getType() == Material.SAND) {
            placeSeed(block, getCardinalDirection(player), block.getType(), Material.SUGAR_CANE);
            return false;
        }
        else if (block.getType() == Material.JUNGLE_LOG) {
            placeBean(block.getRelative(event.getBlockFace()), getCardinalDirection(player));
            return false;
        }
        // nether wart
        else if (block.getType() == Material.SOUL_SAND) {
            placeSeed(block, getCardinalDirection(player), block.getType(), Material.NETHER_WART);
            return false;
        }
        else return false;
    }

    public boolean shiftLeftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean shiftRightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }

    public boolean middleClickAction(Player player, ItemStack item) { return false; }
    public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item) { return false; }
    public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) { return false; }
    public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) { return false; }
    public boolean activeEffect(Player player, ItemStack item) { return false; }

    private void placeSeed(Block current, BlockFace direction, Material blockType, Material seedType) {

        // test if the block is of the right type and has open space above
        // TODO inventory / seed management
        if (current.getType() == blockType && growable.contains(current.getRelative(BlockFace.UP).getType())) {

            // make sure block is watered if the crop is sugarcane
            if ((blockType == Material.DIRT || blockType == Material.GRASS_BLOCK || blockType == Material.SAND) && !isTouchingMaterial(current, Material.WATER)) return;

            current.getRelative(BlockFace.UP).setType(seedType);
            current.getWorld().playEffect(current.getRelative(BlockFace.UP).getLocation(), Effect.SMOKE, 1);
            current.getWorld().playSound(current.getRelative(BlockFace.UP).getLocation(), Sound.BLOCK_LAVA_POP, 1, 5);

            Utilities.scheduleTask(()-> placeSeed(current.getRelative(direction), direction, blockType, seedType), 1);
        }

    }
    private void placeBean(Block current, BlockFace direction) {
        if (!growable.contains(current.getType()) || !isTouchingMaterial(current, Material.JUNGLE_LOG)) return;

        current.setType(Material.COCOA);
        Directional blockData = (Directional) current.getBlockData();
        BlockFace log = searchForLog(current); if (log == null) return;
        blockData.setFacing(log);
        current.setBlockData(blockData);

        current.getWorld().playEffect(current.getLocation(), Effect.SMOKE, 1);
        current.getWorld().playSound(current.getLocation(), Sound.BLOCK_LAVA_POP, 1, 5);
        Utilities.scheduleTask(()-> placeBean(current.getRelative(direction), direction), 1);
    }

    private static BlockFace getCardinalDirection(Player player) {
        double rotation = (player.getLocation().getYaw() - 90.0F) % 360.0F;
        if (rotation < 0.0D) {
            rotation += 360.0D;
        }
        rotation -= 45.0D;

        if ((0.0D <= rotation) && (rotation < 90.0D))
            return BlockFace.NORTH;
        if ((90.0D <= rotation) && (rotation < 180.0D))
            return BlockFace.EAST;
        if ((180.0D <= rotation) && (rotation < 270.0D))
            return BlockFace.SOUTH;
        if ((270.0D <= rotation) && (rotation < 360.0D))
            return BlockFace.WEST;
        return null;
    }
    private static boolean isTouchingMaterial(Block block, Material material) {
        return (block.getRelative(BlockFace.NORTH).getType() == material ||
                block.getRelative(BlockFace.SOUTH).getType() == material ||
                block.getRelative(BlockFace.EAST).getType() == material ||
                block.getRelative(BlockFace.WEST).getType() == material);
    }
    private static BlockFace searchForLog(Block block) {
        if (block.getRelative(BlockFace.NORTH).getType() == Material.JUNGLE_LOG) return BlockFace.NORTH;
        else if (block.getRelative(BlockFace.SOUTH).getType() == Material.JUNGLE_LOG) return BlockFace.SOUTH;
        else if (block.getRelative(BlockFace.EAST).getType() == Material.JUNGLE_LOG) return BlockFace.EAST;
        else if (block.getRelative(BlockFace.WEST).getType() == Material.JUNGLE_LOG) return BlockFace.WEST;
        return null;
    }
}
