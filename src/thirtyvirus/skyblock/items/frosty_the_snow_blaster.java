package thirtyvirus.skyblock.items;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.helpers.*;

public class frosty_the_snow_blaster extends UberItem {

    private int maxAmmo = 2000;

    public frosty_the_snow_blaster(ItemStack itemStack, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(itemStack, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) {
        Utilities.addEnchantGlint(item);
        Utilities.storeIntInItem(item, maxAmmo, "ammo");
    }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) {
        lore.add(ChatColor.GRAY + "Holds and shoots Snowballs. When");
        lore.add(ChatColor.GRAY + "you pickup Snowballs, they");
        lore.add(ChatColor.GRAY + "increase the ammo held by this");
        lore.add(ChatColor.GRAY + "item. Blasts Snowballs at a");
        lore.add(ChatColor.GRAY + "higher velocity than a Snow");
        lore.add(ChatColor.GRAY + "Cannon!");
        lore.add("");
        int ammo = Utilities.getIntFromItem(item, "ammo");
        lore.add(ChatColor.GRAY + "Snowballs: " + ChatColor.WHITE + ammo + "/" + maxAmmo);
    }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) {
        lore.add(ChatColor.YELLOW + "Right click to shoot!");
        lore.add("");
    }

    public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }

    public boolean rightClickAirAction(Player player, ItemStack item) {

        int ammo = Utilities.getIntFromItem(item, "ammo");

        if (ammo > 0) {
            Snowball snowball = player.launchProjectile(Snowball.class);
            snowball.setVelocity(player.getLocation().getDirection().multiply(3));
            player.playSound(player.getLocation(), Sound.ENTITY_SNOWBALL_THROW, 1, 1);
            Utilities.storeIntInItem(item, ammo - 1, "ammo");
            updateLore(item);
        }
        else {
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_OFF, 1, 3);
        }

        return false;
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

}
