package thirtyvirus.skyblock.items;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Waterlogged;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class plumbers_sponge extends UberItem {

    public plumbers_sponge(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) { }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) { }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }

    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean rightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) {
        doBlockEater(event.getClickedBlock().getRelative(event.getBlockFace()), 35);
        return true;
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

    public void doBlockEater(Block startingBlock, int amount) {
        if (startingBlock.getType() == Material.AIR) return;
        if (Utilities.temporaryBlocks.contains(startingBlock)) return;

        ArrayList<Block> blocksToCheck = new ArrayList<>();
        blocksToCheck.add(startingBlock);
        for (int i = 0; i <= amount; i++) {
            Utilities.scheduleTask(() -> {
                ArrayList<Block> preClonedList = new ArrayList<>(blocksToCheck);
                for (Block block : preClonedList) {
                    blocksToCheck.remove(block);
                    Block upperBlock = block.getRelative(BlockFace.UP);
                    Block lowerBlock = block.getRelative(BlockFace.DOWN);
                    Block northBlock = block.getRelative(BlockFace.NORTH);
                    Block eastBlock = block.getRelative(BlockFace.EAST);
                    Block southBlock = block.getRelative(BlockFace.SOUTH);
                    Block westBlock = block.getRelative(BlockFace.WEST);
                    for (Block nearbyBlock : new ArrayList<>(Arrays.asList(upperBlock, lowerBlock, northBlock, eastBlock, southBlock, westBlock))) {
                        if (!Utilities.temporaryBlocks.contains(nearbyBlock)) {
                            if (nearbyBlock.getType() == Material.SEAGRASS || nearbyBlock.getType() == Material.TALL_SEAGRASS || nearbyBlock.getType() == Material.KELP_PLANT || nearbyBlock.getType() == Material.KELP || nearbyBlock.getType() == Material.BUBBLE_COLUMN) {
                                block.breakNaturally();
                                nearbyBlock.setType(Material.AIR);
                                blocksToCheck.add(nearbyBlock);
                            }
                            else if (nearbyBlock.getType() == Material.WATER) {
                                nearbyBlock.setType(Material.AIR);
                                nearbyBlock.getWorld().playSound(nearbyBlock.getLocation(), Sound.ENTITY_PUFFER_FISH_FLOP, 0.3F, 2F);
                                blocksToCheck.add(nearbyBlock);
                            }
                            if (nearbyBlock.getBlockData() instanceof Waterlogged) {
                                Waterlogged waterlogged = (Waterlogged)nearbyBlock.getBlockData();
                                waterlogged.setWaterlogged(false);
                                nearbyBlock.getWorld().playSound(nearbyBlock.getLocation(), Sound.ENTITY_PUFFER_FISH_FLOP, 0.3F, 2F);
                                blocksToCheck.add(nearbyBlock);
                            }
                        }
                    }
                }
            }, i*2);
        }
    }
}
