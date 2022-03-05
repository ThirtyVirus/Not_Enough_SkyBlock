package thirtyvirus.skyblock.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
import java.util.Random;

public class treecapitator extends UberItem {

    public treecapitator(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe, String raritySuffix) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe, raritySuffix);
    }
    public void onItemStackCreate(ItemStack item) { }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) {
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "A forceful Gold Axe which can");
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "break a large amount of logs in");
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "a single hit!");
        lore.add(ChatColor.RESET + "" + ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "2s");
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
    public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item) {
        Utilities.repairItem(item);
        return false;
    }
    public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) {

        // enforce the 2 second cooldown of the treecap "ability"
        if (Utilities.enforceCooldown(player, "treecap", 2, item, false)) return false;

        if (block.getType().name().contains("LOG") || block.getType().name().contains("LEAVES"))
            doBlockEater(player, block, 35);

        // repair axe each time it is used
        Utilities.repairItem(item);
        return true;
    }
    public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) { return false; }
    public boolean activeEffect(Player player, ItemStack item) { return false; }

    public void doBlockEater(Player player, Block startingBlock, int amount) {
        if (startingBlock.getType() == Material.AIR) return;
        if (Utilities.temporaryBlocks.contains(startingBlock)) return;

        Material targetMaterial = startingBlock.getType();

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
                    for (Block nearbyBlock : new ArrayList<Block>(Arrays.asList(upperBlock, lowerBlock, northBlock, eastBlock, southBlock, westBlock))) {
                        if (nearbyBlock.getType() == targetMaterial && !Utilities.temporaryBlocks.contains(nearbyBlock)) {
                            if (nearbyBlock.getType().name().contains("LOG")) {
                                Utilities.givePlayerItemSafely(player, new ItemStack(targetMaterial, 1));
                                nearbyBlock.getWorld().playSound(nearbyBlock.getLocation(), Sound.BLOCK_WOOD_BREAK, 0.3F, 2F);
                            }
                            if (nearbyBlock.getType().name().contains("LEAVES")) {
                                Random rand = new Random();
                                int n = rand.nextInt(200);
                                if (n > 189) Utilities.givePlayerItemSafely(player, new ItemStack(Material.OAK_SAPLING, 1));
                                if (n < 6) Utilities.givePlayerItemSafely(player, new ItemStack(Material.APPLE, 1));
                                nearbyBlock.getWorld().playSound(nearbyBlock.getLocation(), Sound.BLOCK_GRASS_BREAK, 0.3F, 2F);
                            }
                            nearbyBlock.setType(Material.AIR);

                            blocksToCheck.add(nearbyBlock);
                        }
                    }
                }
            }, i*2);
        }
    }
}
