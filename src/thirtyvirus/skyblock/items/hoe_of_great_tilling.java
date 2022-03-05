package thirtyvirus.skyblock.items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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

import java.util.List;
import java.util.Random;

public class hoe_of_great_tilling extends UberItem {

    private Random rand = new Random();

    public hoe_of_great_tilling(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe, String raritySuffix) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe, raritySuffix);
    }
    public void onItemStackCreate(ItemStack item) { }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) {
        lore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Tils a " + ChatColor.BLUE + "3x3" + ChatColor.GRAY + " area of farmland at a time");
    }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }

    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean rightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) {

        // make sure the player is using the tool on a farmable blovk
        if (block.getType() != Material.DIRT && block.getType() != Material.GRASS_BLOCK && block.getType() != Material.COARSE_DIRT && block.getType() != Material.PODZOL) return false;

        tilRadius(block, 3);

        return true;
    }
    public boolean shiftLeftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean shiftRightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean middleClickAction(Player player, ItemStack item) { return false; }
    public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item) {
        Utilities.repairItem(item);
        return false;
    }
    public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) { return false; }
    public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) { return false; }
    public boolean activeEffect(Player player, ItemStack item) { return false; }

    public void tilRadius(Block block, int radius) {
        if (radius % 2 == 0) radius++;
        int halfish = ((radius - 1) / 2);

        Location origin = block.getLocation().subtract(halfish, 0, halfish);
        for (int x = 0; x < radius; x++) {
            for (int z = 0; z < radius; z++) {
                Block currentBlock = block.getWorld().getBlockAt(origin.clone().add(x,0,z));

                // only convert the block if it is of the proper type
                if (currentBlock.getType() == Material.DIRT || currentBlock.getType() == Material.COARSE_DIRT || currentBlock.getType() == Material.GRASS_BLOCK || currentBlock.getType() == Material.PODZOL) {

                    // only til block if the above material isn't solid, break naturally if transparent block is there
                    if (!currentBlock.getRelative(BlockFace.UP).getType().isSolid()) {
                        currentBlock.setType(Material.FARMLAND);
                        currentBlock.getRelative(BlockFace.UP).breakNaturally();
                    }
                }

            }
        }


    }
}
