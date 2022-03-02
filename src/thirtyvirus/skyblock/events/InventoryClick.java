package thirtyvirus.skyblock.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.UberItems;
import thirtyvirus.uber.UberMaterial;
import thirtyvirus.skyblock.items.uncrafting_table;
import thirtyvirus.uber.helpers.Utilities;

public class InventoryClick implements Listener {

    // update the crafting result on InventoryDragEvents
    @EventHandler
    private void inventoryDragEvent(InventoryDragEvent event) {
        // verify that the Player is in the UberItems uncrafting menu
        if (!event.getView().getTitle().equals("UberItems - UnCraft Item") || event.getView().getTopInventory().getLocation() != null) return;

        // make crafting grid *more* responsive
        Bukkit.getScheduler().scheduleSyncDelayedTask(UberItems.getInstance(), () -> checkUnCraft(event.getInventory()), 1);
    }

    // process click events in the UberItems Crafting Menu
    @EventHandler
    private void interactInCraftingMenu(InventoryClickEvent event) {
        // verify that the Player is in the UberItems uncrafting menu
        if (!event.getView().getTitle().equals("UberItems - UnCraft Item") || event.getView().getTopInventory().getLocation() != null) return;

        Player player = (Player) event.getWhoClicked();

        // cancel any clicks on GUI buttons
        if (uncrafting_table.customItems.contains(event.getCurrentItem())) event.setCancelled(true);

        // cancel any clicks in the uncrafted items if the base item still exists
        if (uncrafting_table.REVERSE_CRAFTING_MENU_EXCEPTIONS.contains(event.getRawSlot())) event.setCancelled(true);

        // make crafting grid *more* responsive
        Bukkit.getScheduler().scheduleSyncDelayedTask(UberItems.getInstance(), () -> checkUnCraft(event.getInventory()), 1);

        // verify that an item was clicked
        if (event.getCurrentItem() == null) return;

        // handle shift click
        if (event.getClick() == ClickType.SHIFT_LEFT) {
            if (event.getClickedInventory().equals(event.getView().getBottomInventory())) {
                if (event.getView().getTopInventory().getItem(19) == null) {
                    event.getView().getTopInventory().setItem(19, event.getCurrentItem());
                    event.setCurrentItem(null);
                }
                else {
                    event.setCancelled(true);
                }
            }
        }

        // process clicking the uncraft item button
        if (event.getRawSlot() == 21) {
            uncraftItem(event);
        }
    }


    // make the craftable UberItem appear in the crafted slot if the appropriate materials are there
    private static void checkUnCraft(Inventory i) {

        // prevent renamed chest dupe
        if (i.getLocation() != null) return;

        // verify that an item is in the uncraft slot
        ItemStack item = i.getItem(19);
        if (item == null) { emptyResultGrid(i); return; }

        // check if the item is an UberItem
        if (Utilities.isUber(item)) {
            UberItem uberItem = Utilities.getUber(item);

            // verify that the item has a crafting recipe
            if (!(uberItem.hasCraftingRecipe() && item.getAmount() >= uberItem.getCraftingRecipe().getCraftAmount())) { emptyResultGrid(i); return; }

            // set the items in the crafting grid to the recipe items
            for (int counter = 0; counter < 9; counter++) {
                i.setItem(uncrafting_table.REVERSE_CRAFTING_MENU_EXCEPTIONS.get(counter), uberItem.getCraftingRecipe().get(counter));
            }

        }
        // check if the item is an UberMaterial
        else if (Utilities.isUberMaterial(item)) {
            UberMaterial uberMaterial = Utilities.getUberMaterial(item);

            // verify that the item has a crafting recipe
            if (!(uberMaterial.hasCraftingRecipe() && item.getAmount() >= uberMaterial.getCraftingRecipe().getCraftAmount())) { emptyResultGrid(i); return; }

            // set the items in the crafting grid to the recipe items
            for (int counter = 0; counter < 9; counter++) {
                i.setItem(uncrafting_table.REVERSE_CRAFTING_MENU_EXCEPTIONS.get(counter), uberMaterial.getCraftingRecipe().get(counter));
            }

        }
    }
    private static void emptyResultGrid(Inventory i) {
        for (int counter = 0; counter < 9; counter++) {
            i.setItem(uncrafting_table.REVERSE_CRAFTING_MENU_EXCEPTIONS.get(counter), new ItemStack(Material.AIR));
        }
    }

    // attempt to uncraft the item in the leftmost slot
    private static void uncraftItem(InventoryClickEvent event) {
        // cancel vanilla event, because it allows me to process the itemstack properly
        event.setCancelled(true);

        // enforce clicking on the uncraft item only
        if (event.getCurrentItem() == uncrafting_table.UNCRAFTING_SLOT_ITEM) {
            event.setCancelled(true); return;
        }
        // enforce having open slots in the inventory
        if (event.getWhoClicked().getInventory().firstEmpty() == -1) {
            event.setCancelled(true); return;
        }

        ItemStack item = event.getInventory().getItem(19);
        boolean done = false;
        while (!done) {
            // verify that the item in the 19th slot is not null
            if (item == null) return;
            // do not loop if not a shift click
            if (event.getClick() != ClickType.SHIFT_LEFT) done = true;
            // do not loop if inventory full, stop uncrafting
            if (event.getWhoClicked().getInventory().firstEmpty() == -1) { done = true; return; }

            // check if the item is an UberItem
            if (Utilities.isUber(item)) {
                UberItem uberItem = Utilities.getUber(item);
                if (uberItem.hasCraftingRecipe() && item.getAmount() >= uberItem.getCraftingRecipe().getCraftAmount()) {
                    for (int counter = 0; counter < 9; counter++) {
                        Utilities.givePlayerItemSafely((Player)event.getWhoClicked(), uberItem.getCraftingRecipe().get(counter));
                    }
                    item.setAmount(item.getAmount() - uberItem.getCraftingRecipe().getCraftAmount());
                    if (event.getWhoClicked() instanceof Player) {
                        Player player = (Player)event.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
                    }
                }
                else done = true;
            }
            // check if the item is an UberMaterial
            else if (Utilities.isUberMaterial(item)) {
                UberMaterial uberMaterial = Utilities.getUberMaterial(item);
                if (uberMaterial.hasCraftingRecipe() && item.getAmount() >= uberMaterial.getCraftingRecipe().getCraftAmount()) {
                    for (int counter = 0; counter < 9; counter++) {
                        Utilities.givePlayerItemSafely((Player)event.getWhoClicked(), uberMaterial.getCraftingRecipe().get(counter));
                    }
                    item.setAmount(item.getAmount() - uberMaterial.getCraftingRecipe().getCraftAmount());
                    if (event.getWhoClicked() instanceof Player) {
                        Player player = (Player)event.getWhoClicked();
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1);
                    }
                }
                else done = true;
            }
            else done = true;
        }

        // update inventory on a 1 tick delay as to prevent visual bugs clientside
        Bukkit.getScheduler().scheduleSyncDelayedTask(UberItems.getInstance(), () -> ((Player) event.getWhoClicked()).updateInventory(), 1);
    }

}
