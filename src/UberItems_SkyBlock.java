import events.UberEvent;
import items.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

public class UberItems_SkyBlock extends JavaPlugin {

    public void onEnable() {

        // enforce UberItems dependancy
        if (Bukkit.getPluginManager().getPlugin("UberItems") == null) {
            this.getLogger().severe("UberItems SkyBlock requires UberItems! disabled because UberItems dependency not found");
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
        getServer().getPluginManager().registerEvents(new UberEvent(), this);
    }

    private void registerUberItems() {

        UberItems.putItem("plumbers_sponge", new plumbers_sponge(Material.SPONGE, "Plumber's Sponge", UberRarity.UNCOMMON,
                true, true, true,
                Collections.singletonList(new UberAbility("Drain", AbilityType.RIGHT_CLICK, "Instructions: 1. Place on water. 2. Drains other water. 3. Double-bill client. " + ChatColor.DARK_GRAY + "Thanks Plumber Joe!")),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.CHORUS_FRUIT),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.CHORUS_FRUIT),
                        new ItemStack(Material.SPONGE, 8),
                        new ItemStack(Material.CHORUS_FRUIT),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.CHORUS_FRUIT),
                        new ItemStack(Material.AIR)), false, 4)));

        UberItems.putItem("grappling_hook", new grappling_hook(Material.FISHING_ROD, "Grappling Hook", UberRarity.UNCOMMON,
                false, false, false, Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.STICK),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.STICK),
                        UberItems.getMaterial("enchanted_string").makeItem(1),
                        new ItemStack(Material.STICK),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_string").makeItem(1)), false, 1)));

        UberItems.putItem("aspect_of_the_end", new aspect_of_the_end(Material.DIAMOND_SWORD, "Aspect Of The End", UberRarity.RARE,
                false, false, false,
                Collections.singletonList(new UberAbility("Instant Transmission", AbilityType.RIGHT_CLICK, "Teleport " + ChatColor.GREEN + "8 blocks" + ChatColor.GRAY + " ahead of you and gain " + ChatColor.GREEN + "+50 " + ChatColor.WHITE + "✦ Speed" + ChatColor.GRAY + " for " + ChatColor.GREEN + "3 seconds")),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_ender_pearl").makeItem(16),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_ender_pearl").makeItem(16),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.OBSIDIAN, 64),
                        new ItemStack(Material.AIR)), false, 1)));

        UberItems.putItem("zombie_sword", new zombie_sword(Material.GOLDEN_SWORD, "Zombie Sword", UberRarity.RARE,
                false, false, false,
                Collections.singletonList(new UberAbility("Instant Heal", AbilityType.RIGHT_CLICK, "Heal for " +
                        ChatColor.RED + "20%" + ChatColor.GRAY + " and heal players within " +
                        ChatColor.GREEN + "7" + ChatColor.GRAY + " blocks for " + ChatColor.RED + "10%" + ChatColor.GRAY + ".")),
                        new UberCraftingRecipe(Arrays.asList(
                                new ItemStack(Material.AIR),
                                UberItems.getMaterial("zombie_heart").makeItem(1),
                                new ItemStack(Material.AIR),
                                new ItemStack(Material.AIR),
                                UberItems.getMaterial("zombie_heart").makeItem(1),
                                new ItemStack(Material.AIR),
                                new ItemStack(Material.AIR),
                                new ItemStack(Material.STICK),
                                new ItemStack(Material.AIR)), false, 1)));

        UberItems.putItem("ember_rod", new ember_rod(Material.BLAZE_ROD, "Ember Rod", UberRarity.EPIC,
                false, false, false,
                Collections.singletonList(new UberAbility("Fire Blast!", AbilityType.RIGHT_CLICK, "Shoot 3 FireBalls in rapid succession in front of you!", 30)),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("flammable_substance").makeItem(32),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.BLAZE_ROD),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.BLAZE_ROD),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR)), false, 1)));

        UberItems.putItem("treecapitator", new treecapitator(Material.GOLDEN_AXE, "Treecapitator", UberRarity.EPIC,
                false, false, false, Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.GOLD_BLOCK, 64),
                        new ItemStack(Material.GOLD_BLOCK, 64),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.GOLD_BLOCK, 64),
                        new ItemStack(Material.STICK),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.STICK),
                        new ItemStack(Material.AIR)), false, 1)));

        UberItems.putItem("stonk", new stonk(Material.GOLDEN_PICKAXE, "Stonk", UberRarity.EPIC,
                false, false, false,
                Collections.singletonList(new UberAbility("Mining Speed Boost", AbilityType.RIGHT_CLICK, "Grants " +
                        ChatColor.GREEN + "Efficiency 10" + ChatColor.GRAY + " for " + ChatColor.GREEN + "20s" + ChatColor.GRAY + ". " + ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "120s")),
                null));

        UberItems.putItem("juju_shortbow", new juju_shortbow(Material.BOW, "Juju Shortbow", UberRarity.EPIC,
                false, false, false,
                Collections.singletonList(new UberAbility("Instantly Shoots!", AbilityType.RIGHT_CLICK, "Hits " + ChatColor.RED + "3" + " mobs on impact. /newline Can damage endermen.")),
                null));

        UberItems.putItem("emerald_blade", new emerald_blade(Material.EMERALD, "Emerald Blade", UberRarity.EPIC,
                false, false, true, Collections.emptyList(), null));

        UberItems.putItem("builders_wand", new builders_wand(Material.BLAZE_ROD, "Builder's Wand", UberRarity.LEGENDARY,
                false, false, true,
                Collections.singletonList(new UberAbility("Contruction!", AbilityType.RIGHT_CLICK, "Right click the face of any block to extend all connected block faces. /newline " + ChatColor.DARK_GRAY + "(consumes blocks from your inventory)")),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.GOLD_BLOCK, 8),
                        new ItemStack(Material.CHEST),
                        new ItemStack(Material.GOLD_BLOCK, 8),
                        new ItemStack(Material.GOLD_BLOCK, 8),
                        new ItemStack(Material.STICK),
                        new ItemStack(Material.GOLD_BLOCK, 8),
                        new ItemStack(Material.GOLD_BLOCK, 8),
                        new ItemStack(Material.STICK),
                        new ItemStack(Material.GOLD_BLOCK, 8)), false, 1)));

        UberItems.putItem("runaans_bow", new runaans_bow(Material.BOW, "Runaan's Bow", UberRarity.LEGENDARY,
                false, false, true,
                Collections.singletonList(new UberAbility("Triple Shot", AbilityType.RIGHT_CLICK,
                        "Shoots 3 arrows at a time! The 2 /newline extra arrows deal " +
                                ChatColor.GREEN + "40%" + ChatColor.GRAY + " of the /newline damage and home to targets.")),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_bone").makeItem(64),
                        UberItems.getMaterial("enchanted_string").makeItem(64),
                        UberItems.getMaterial("enchanted_bone").makeItem(64),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_string").makeItem(64),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_bone").makeItem(64),
                        UberItems.getMaterial("enchanted_string").makeItem(64)), false, 1)));

        UberItems.putItem("aspect_of_the_dragons", new aspect_of_the_dragons(Material.DIAMOND_SWORD, "Aspect of the Dragons", UberRarity.LEGENDARY,
                false, false, false,
                Collections.singletonList(new UberAbility("Dragon Rage", AbilityType.RIGHT_CLICK, "All Monsters and Players /newline in front of you take " + ChatColor.GREEN + "4" + ChatColor.GRAY + " damage. Hit monsters take large knockback.")),
                null));

    }
    private void registerUberMaterials() {
        UberItems.putMaterial("enchanted_rotten_flesh", new UberMaterial(Material.ROTTEN_FLESH, "Enchanted Rotten Flesh", UberRarity.UNCOMMON, true, true, false, "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.ROTTEN_FLESH, 32),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.ROTTEN_FLESH, 32),
                        new ItemStack(Material.ROTTEN_FLESH, 32),
                        new ItemStack(Material.ROTTEN_FLESH, 32),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.ROTTEN_FLESH, 32),
                        new ItemStack(Material.AIR)), true, 1)));
        UberItems.putMaterial("zombie_heart", new UberMaterial(Material.POISONOUS_POTATO, "Zombie Heart", UberRarity.RARE, true, false, false, "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_rotten_flesh").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_rotten_flesh").makeItem(32),
                        UberItems.getMaterial("enchanted_rotten_flesh").makeItem(32),
                        UberItems.getMaterial("enchanted_rotten_flesh").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_rotten_flesh").makeItem(32),
                        new ItemStack(Material.AIR)), true, 1)));

        UberItems.putMaterial("enchanted_emerald", new UberMaterial(Material.EMERALD, "Enchanted Emerald", UberRarity.UNCOMMON, true, true, false, "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.EMERALD, 32),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.EMERALD, 32),
                        new ItemStack(Material.EMERALD, 32),
                        new ItemStack(Material.EMERALD, 32),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.EMERALD, 32),
                        new ItemStack(Material.AIR)), true, 1)));
        UberItems.putMaterial("zombie_emerald_block", new UberMaterial(Material.EMERALD_BLOCK, "Enchanted Emerald Block", UberRarity.RARE, true, false, false, "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_emerald").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_emerald").makeItem(32),
                        UberItems.getMaterial("enchanted_emerald").makeItem(32),
                        UberItems.getMaterial("enchanted_emerald").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_emerald").makeItem(32),
                        new ItemStack(Material.AIR)), true, 1)));

        UberItems.putMaterial("enchanted_bone", new UberMaterial(Material.BONE, "Enchanted Bone", UberRarity.UNCOMMON, true, true, false, "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.BONE, 32),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.BONE, 32),
                        new ItemStack(Material.BONE, 32),
                        new ItemStack(Material.BONE, 32),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.BONE, 32),
                        new ItemStack(Material.AIR)), true, 1)));
    }
}