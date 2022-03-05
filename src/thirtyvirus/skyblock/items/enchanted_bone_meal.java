package thirtyvirus.skyblock.items;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Sapling;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.UberItems;
import thirtyvirus.uber.helpers.UberAbility;
import thirtyvirus.uber.helpers.UberCraftingRecipe;
import thirtyvirus.uber.helpers.UberRarity;
import thirtyvirus.uber.helpers.Utilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class enchanted_bone_meal extends UberItem {

    private final List<Material> AIR = Arrays.asList(Material.AIR, Material.CAVE_AIR, Material.VOID_AIR);
    private final Map<Material, TreeType> TREES = new HashMap<Material, TreeType>() {{
        put(Material.BROWN_MUSHROOM, TreeType.BROWN_MUSHROOM);
        put(Material.RED_MUSHROOM, TreeType.RED_MUSHROOM);
        put(Material.OAK_SAPLING, TreeType.TREE);
        put(Material.SPRUCE_SAPLING, TreeType.REDWOOD);
        put(Material.BIRCH_SAPLING, TreeType.BIRCH);
        put(Material.ACACIA_SAPLING, TreeType.ACACIA);
        put(Material.JUNGLE_SAPLING, TreeType.SMALL_JUNGLE);
    }};

    public enchanted_bone_meal(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) {
        Utilities.addEnchantGlint(item); }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) {
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "Instantly grows crops and");
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "saplings.");
    }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }
    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return leftClickAirAction(player, item); }

    public boolean rightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) {
        // disable vanilla bone meal use
        event.setCancelled(true);

        // handle sugar cane
        if (block.getType() == Material.SUGAR_CANE) {
            Bukkit.getLogger().info("ello1");
            Block second = block.getRelative(BlockFace.UP);
            if (AIR.contains(second.getType())) {
                second.setType(Material.SUGAR_CANE);
                Block third = second.getRelative(BlockFace.UP);
                if (AIR.contains(third.getType())) third.setType(Material.SUGAR_CANE);
                return true;
            }
            return false;
        }
        // handle all "trees"
        else if (TREES.containsKey(block.getType())) {
            tryPlacingTree(player, block, block.getType(), TREES.get(block.getType()));
            return true;
        }
        // handle most crops (the ones with growth stages in one block space)
        else if (block.getBlockData() instanceof Ageable) {
            Ageable a = (Ageable)block.getBlockData();

            // verify that the bone meal isn't being used on a crop that is already fully grown
            if (a.getAge() >= a.getMaximumAge()) return false;

            // grow the crop and consume the bone meal
            a.setAge(a.getMaximumAge());
            block.setBlockData(a);
            return true;
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

    private void tryPlacingTree(Player player, Block block, Material material, TreeType treeType) {
        block.setType(Material.AIR);
        block.getWorld().generateTree(block.getLocation(), treeType);
        Utilities.scheduleTask(()->{
            if (AIR.contains(block.getType())) {
                block.setType(material);
                player.getInventory().addItem(UberItems.getItem("enchanted_bone_meal").makeItem(1));
            }
        },1);
    }
}
