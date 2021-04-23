import items.example_uber_item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import thirtyvirus.uber.UberItems;
import thirtyvirus.uber.UberMaterial;
import thirtyvirus.uber.helpers.AbilityType;
import thirtyvirus.uber.helpers.UberAbility;
import thirtyvirus.uber.helpers.UberCraftingRecipe;
import thirtyvirus.uber.helpers.UberRarity;

import java.util.Arrays;
import java.util.Collections;

public class UberItems_Hypixel_SkyBlock extends JavaPlugin {

    public void onEnable() {

        // enforce UberItems dependancy
        if (Bukkit.getPluginManager().getPlugin("UberItems") == null) {
            this.getLogger().severe("UberItems Addons requires UberItems! disabled because UberItems dependency not found");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // register events and UberItems
        registerEvents();
        registerUberMaterials();
        registerUberItems();

        // post confirmation in chat
        getLogger().info(getDescription().getName() + " V: " + getDescription().getVersion() + " has been enabled");
    }
    public void onDisable() {
        // posts exit message in chat
        getLogger().info(getDescription().getName() + " V: " + getDescription().getVersion() + " has been disabled");
    }
    private void registerEvents() {

    }

    // NEW UBER ITEM CHECKLIST

    // - make a new class file, named with all lowercase lettering and underscores for spaces
    // - copy the UberItemTemplate class contents into the new class, extend UberItem
    // - make a putItem entry, follow the format of previous items and make sure to give a unique id
    // - write the unique item ability code in the appropriate method

    // - add the following line of code just after executing the item's ability:
    //      onItemUse(player, item); // confirm that the item's ability has been successfully used

    // - if the ability needs a cooldown, prefix it's code with a variation of the following line of code:
    //      if (!Utilities.enforceCooldown(getMain(), player, "name", 1, item, true)) return;

    // - if the item needs work done on create (like adding enchantments, adding other data) refer to onItemStackCreate
    // - if the item needs a prefix or suffix in its description,
    //   refer to the getSpecificLorePrefix and getSpecificLoreSuffix functions, then add the following:
    //      lore.add(ChatColor.RESET + "text goes here");

    // - if you need to store & retrieve ints and strings from items, you can use the following functions:
    //      Utilities.storeIntInItem(getMain(), item, 1, "number tag");
    //      if (Utilities.getIntFromItem(getMain(), item, "number tag") == 1) // { blah blah blah }
    //      (the same case for strings, just storeStringInItem and getStringFromItem)

    private void registerUberItems() {
        UberItems.putItem("example_uber_item", new example_uber_item(Material.DIAMOND_SHOVEL, "Example Uber Item",
                UberRarity.UNFINISHED, false, false, true,
                Collections.singletonList(new UberAbility("Example Ability", AbilityType.RIGHT_CLICK, "Adds an enchantment glint to items when they are clicked onto this item in the inventory... because why not? xD")),
                new UberCraftingRecipe(Arrays.asList(
                        UberItems.getMaterial("enchanted_chest").makeItem(1),
                        UberItems.getMaterial("enchanted_diamond").makeItem(1),
                        UberItems.getMaterial("enchanted_chest").makeItem(1),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.STICK, 8),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.STICK, 8),
                        new ItemStack(Material.AIR)), false, 1)));
    }
    private void registerUberMaterials() {
        UberItems.putMaterial("enchanted_sponge", new UberMaterial(Material.SPONGE,
                "Enchanted Sponge", UberRarity.RARE, true, false, false,
                "idk why I chose sponge, but hey this demonstrates how to make a custom UberMaterial lol",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.SPONGE, 32),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.SPONGE, 32),
                        new ItemStack(Material.SPONGE, 32),
                        new ItemStack(Material.SPONGE, 32),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.SPONGE, 32),
                        new ItemStack(Material.AIR)), false, 1)));
    }
}