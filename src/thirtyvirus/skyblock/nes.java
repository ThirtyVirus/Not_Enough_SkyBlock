package thirtyvirus.skyblock;

import org.bukkit.*;
import thirtyvirus.skyblock.commands.GiveCreativeMind;
import thirtyvirus.skyblock.commands.WandOops;
import thirtyvirus.skyblock.events.InventoryClick;
import thirtyvirus.skyblock.events.InventoryClose;
import thirtyvirus.skyblock.events.UberEvent;
import thirtyvirus.skyblock.helpers.utils;
import thirtyvirus.skyblock.items.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import thirtyvirus.uber.UberItems;
import thirtyvirus.uber.UberMaterial;
import thirtyvirus.uber.helpers.*;

import java.util.*;

public class nes extends JavaPlugin {

    public void onEnable() {

        // enforce UberItems dependancy
        if (Bukkit.getPluginManager().getPlugin("UberItems") == null) {
            this.getLogger().severe("Not Enough SkyBlock requires UberItems! disabled because UberItems dependency not found");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // register commands, events, UberItems, and UberMaterials
        registerCommands();
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

    // register commands
    private void registerCommands() {
        getCommand("wandoops").setExecutor(new WandOops());
        getCommand("givecreativemind").setExecutor(new GiveCreativeMind());
    }
    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new UberEvent(), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        getServer().getPluginManager().registerEvents(new InventoryClose(), this);
    }

    private void registerUberItems() {

        UberItems.putItem("stone_platform", new stone_platform(Material.EGG, "Stone Platform", UberRarity.COMMON,
                false, true, false,
                Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.STONE_BRICKS, 8),
                        new ItemStack(Material.STONE_BRICKS, 8),
                        new ItemStack(Material.STONE_BRICKS, 8),
                        new ItemStack(Material.STONE_BRICKS, 8),
                        new ItemStack(Material.EGG),
                        new ItemStack(Material.STONE_BRICKS, 8),
                        new ItemStack(Material.STONE_BRICKS, 8),
                        new ItemStack(Material.STONE_BRICKS, 8),
                        new ItemStack(Material.STONE_BRICKS, 8)), false, 1)));

        UberItems.putItem("bridge_egg", new bridge_egg(Material.EGG, "Bridge Egg", UberRarity.COMMON,
                true, true, false,
                Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.STONE),
                        new ItemStack(Material.STONE),
                        new ItemStack(Material.STONE),
                        new ItemStack(Material.STONE),
                        new ItemStack(Material.EGG, 5),
                        new ItemStack(Material.STONE),
                        new ItemStack(Material.STONE),
                        new ItemStack(Material.STONE),
                        new ItemStack(Material.STONE)), false, 1)));

        UberItems.putItem("rookie_hoe", new rookie_hoe(Material.STONE_HOE, "Rookie Hoe", UberRarity.COMMON,
                false, false, false,
                Collections.emptyList(), null, "HOE"));

        UberItems.putItem("enchanted_bone_meal", new enchanted_bone_meal(Material.BONE_MEAL, "Enchanted Bone Meal", UberRarity.UNFINISHED,
                true, true, false,
                Collections.emptyList(), null));

        UberItems.putItem("magical_water_bucket", new magical_water_bucket(Material.WATER_BUCKET, "Magical Water Bucket", UberRarity.COMMON,
                false, false, false,
                Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.IRON_INGOT),
                        UberItems.getMaterial("enchanted_ice").makeItem(1),
                        new ItemStack(Material.IRON_INGOT),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.IRON_INGOT),
                        new ItemStack(Material.AIR)), false, 1)));

        UberItems.putItem("magical_lava_bucket", new magical_lava_bucket(Material.LAVA_BUCKET, "Magical Lava Bucket", UberRarity.UNCOMMON,
                false, false, false,
                Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.IRON_INGOT),
                        UberItems.getMaterial("enchanted_netherrack").makeItem(1),
                        new ItemStack(Material.IRON_INGOT),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.IRON_INGOT),
                        new ItemStack(Material.AIR)), false, 1)));

        UberItems.putItem("plumbers_sponge", new plumbers_sponge(Material.SPONGE, "Plumber's Sponge", UberRarity.UNCOMMON,
                true, true, true,
                Collections.singletonList(new UberAbility("Drain", AbilityType.RIGHT_CLICK, "Instructions: 1. Place on water. 2. Drains other water. 3. Double-bill client. " + ChatColor.DARK_GRAY + "Thanks Plumber Joe!")),
                null));

        UberItems.putItem("sweet_axe", new sweet_axe(Material.IRON_AXE, "Sweet Axe", UberRarity.UNCOMMON,
                false, false, false,
                Collections.emptyList(), null, "AXE"));

        UberItems.putItem("gift_compass", new gift_compass(Material.COMPASS, "Gift Compass", UberRarity.UNFINISHED,
                false, false, true,
                Collections.emptyList(), null));

        UberItems.putItem("fairy_soul_compass", new fairy_soul_compass(Material.COMPASS, "Fairy Soul Compass", UberRarity.UNFINISHED,
                false, false, true,
                Collections.emptyList(), null));

        UberItems.putItem("grappling_hook", new grappling_hook(Material.FISHING_ROD, "Grappling Hook", UberRarity.UNCOMMON,
                false, false, false, Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        UberItems.getMaterial("enchanted_string").makeItem(1),
                        UberItems.getMaterial("enchanted_string").makeItem(1),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_string").makeItem(1),
                        new ItemStack(Material.STICK),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.STICK)), false, 1)));

        UberItems.putItem("hook_shot", new hook_shot(Material.FISHING_ROD, "Hook Shot", UberRarity.RARE,
                false, false, false,
                Collections.singletonList(new UberAbility("Improved Hydraulics", AbilityType.NONE, "Take " + ChatColor.GREEN + "50%" + ChatColor.GRAY + " less fall damage when /newline holding this item.")),
                new UberCraftingRecipe(Arrays.asList(
                        UberItems.getMaterial("tarantula_silk").makeItem(1),
                        UberItems.getMaterial("tarantula_silk").makeItem(1),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("tarantula_silk").makeItem(1),
                        UberItems.getItem("grappling_hook").makeItem(1),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.STICK)), false, 1)));

        UberItems.putItem("hoe_of_great_tilling", new hoe_of_great_tilling(Material.STONE_HOE, "Hoe of Great Tilling", UberRarity.UNCOMMON,
                false, false, false,
                Collections.emptyList(), null, "HOE"));

        UberItems.putItem("hoe_of_greater_tilling", new hoe_of_greater_tilling(Material.DIAMOND_HOE, "Hoe of Greater Tilling", UberRarity.RARE,
                false, false, false,
                Collections.emptyList(), null, "HOE"));

        UberItems.putItem("frosty_the_snow_cannon", new frosty_the_snow_cannon(Utilities.getSkull("http://textures.minecraft.net/texture/1fff9d348ebcda66747192554c378ad7f12fe4f6d79cbc62d4dfb666971e2bf"),
                "Frosty the Snow Cannon", UberRarity.RARE, false, false, false,
                Collections.emptyList(), null));

        UberItems.putItem("frosty_the_snow_blaster", new frosty_the_snow_blaster(Utilities.getSkull("http://textures.minecraft.net/texture/e25e333b25c5d5b243369516f5888bca38c1aa0e1d98092738187a74406b54a"),
                "Frosty the Snow Blaster", UberRarity.EPIC, false, false, false,
                Collections.emptyList(), null));

        UberItems.putItem("grand_experience_bottle", new grand_experience_bottle(Material.EXPERIENCE_BOTTLE, "Grand Experience Bottle", UberRarity.UNCOMMON,
                true, true, false, Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_lapis_lazuli").makeItem(1),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_lapis_lazuli").makeItem(1),
                        new ItemStack(Material.GLASS_BOTTLE),
                        UberItems.getMaterial("enchanted_lapis_lazuli").makeItem(1),
                        UberItems.getMaterial("enchanted_lapis_lazuli").makeItem(1),
                        UberItems.getMaterial("enchanted_lapis_lazuli").makeItem(1),
                        UberItems.getMaterial("enchanted_lapis_lazuli").makeItem(1)), false, 1)));

        UberItems.putItem("titanic_experience_bottle", new titanic_experience_bottle(Material.EXPERIENCE_BOTTLE, "Titanic Experience Bottle", UberRarity.RARE,
                true, true, false, Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_lapis_block").makeItem(1),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_lapis_block").makeItem(1),
                        new ItemStack(Material.GLASS_BOTTLE),
                        UberItems.getMaterial("enchanted_lapis_block").makeItem(1),
                        UberItems.getMaterial("enchanted_lapis_block").makeItem(1),
                        UberItems.getMaterial("enchanted_lapis_block").makeItem(1),
                        UberItems.getMaterial("enchanted_lapis_block").makeItem(1)), false, 1)));

        UberItems.putItem("colossal_experience_bottle", new colossal_experience_bottle(Material.EXPERIENCE_BOTTLE, "Colossal Experience Bottle", UberRarity.EPIC,
                true, true, false, Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("colossal_experience_bottle_upgrade").makeItem(1),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        UberItems.getItem("titanic_experience_bottle").makeItem(1),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR)), false, 1)));

        UberItems.putItem("sheep_plushie", new sheep_plushie(Utilities.getSkull("http://textures.minecraft.net/texture/32b6adb3b6e83c074e7d0ebaff14d6acbbabeb06694d6ee38cc8dd1bf6630bc6"), "Sheep Plushie", UberRarity.RARE,
                false, false, false, Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_wool").makeItem(16),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_wool").makeItem(16),
                        UberItems.getMaterial("enchanted_slime_block").makeItem(64),
                        UberItems.getMaterial("enchanted_wool").makeItem(16),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_wool").makeItem(16),
                        new ItemStack(Material.AIR)), false, 1), "ACCESSORY"));

        UberItems.putItem("aspect_of_the_end", new aspect_of_the_end(Material.DIAMOND_SWORD, "Aspect Of The End", UberRarity.RARE,
                false, false, false,
                Collections.singletonList(new UberAbility("Instant Transmission", AbilityType.RIGHT_CLICK, "Teleport " + ChatColor.GREEN + "8 blocks" + ChatColor.GRAY + " ahead of you and gain " + ChatColor.GREEN + "+50 " + ChatColor.WHITE + "✦ Speed" + ChatColor.GRAY + " for " + ChatColor.GREEN + "3 seconds")),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_eye_of_ender").makeItem(16),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_eye_of_ender").makeItem(16),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_diamond").makeItem(1),
                        new ItemStack(Material.AIR)), false, 1), "SWORD"));

        UberItems.putItem("zombie_sword", new zombie_sword(Material.IRON_SWORD, "Zombie Sword", UberRarity.RARE,
                false, false, false,
                Collections.singletonList(new UberAbility("Instant Heal", AbilityType.RIGHT_CLICK, "Heal for " +
                        ChatColor.RED + "1.5 hearts" + ChatColor.GRAY + " and heal players within " +
                        ChatColor.GREEN + "7" + ChatColor.GRAY + " blocks for " + ChatColor.RED + "0.5 hearts" + ChatColor.GRAY +
                        ". " + ChatColor.DARK_GRAY + "Charges: " + ChatColor.YELLOW + "4" + ChatColor.DARK_GRAY + " / " + ChatColor.GREEN + "15s")),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("zombie_heart").makeItem(1),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("zombie_heart").makeItem(1),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.STICK),
                        new ItemStack(Material.AIR)), false, 1), "SWORD"));

        UberItems.putItem("uncrafting_table", new uncrafting_table(Utilities.getSkull("http://textures.minecraft.net/texture/2cdc0feb7001e2c10fd5066e501b87e3d64793092b85a50c856d962f8be92c78"), "Uncrafting Table", UberRarity.EPIC,
                false, false, false,
                Collections.singletonList(new UberAbility("914 - Coarse", AbilityType.RIGHT_CLICK, "Dismantle items into their constituent parts without damage")),
                new UberCraftingRecipe(Arrays.asList(
                        UberItems.getMaterial("enchanted_oak_wood").makeItem(64),
                        UberItems.getMaterial("enchanted_iron_block").makeItem(4),
                        UberItems.getMaterial("enchanted_oak_wood").makeItem(64),
                        UberItems.getMaterial("enchanted_iron_block").makeItem(4),
                        new ItemStack(Material.CRAFTING_TABLE),
                        UberItems.getMaterial("enchanted_iron_block").makeItem(4),
                        UberItems.getMaterial("enchanted_oak_wood").makeItem(64),
                        UberItems.getMaterial("enchanted_iron_block").makeItem(4),
                        UberItems.getMaterial("enchanted_oak_wood").makeItem(64)), false, 1)));

        UberItems.putItem("ornate_zombie_sword", new ornate_zombie_sword(Material.GOLDEN_SWORD, "Ornate Zombie Sword", UberRarity.EPIC,
                false, false, false,
                Collections.singletonList(new UberAbility("Instant Heal", AbilityType.RIGHT_CLICK, "Heal for " +
                        ChatColor.RED + "2 hearts" + ChatColor.GRAY + " and heal players within " +
                        ChatColor.GREEN + "7" + ChatColor.GRAY + " blocks for " + ChatColor.RED + "1 heart" + ChatColor.GRAY +
                        ". " + ChatColor.DARK_GRAY + "Charges: " + ChatColor.YELLOW + "5" + ChatColor.DARK_GRAY + " / " + ChatColor.GREEN + "15s")),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_gold_block").makeItem(1),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("golden_powder").makeItem(1),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        UberItems.getItem("zombie_sword").makeItem(1),
                        new ItemStack(Material.AIR)), false, 1), "SWORD"));

        UberItems.putItem("florid_zombie_sword", new florid_zombie_sword(Material.GOLDEN_SWORD, "Florid Zombie Sword", UberRarity.LEGENDARY,
                false, false, false,
                Collections.singletonList(new UberAbility("Instant Heal", AbilityType.RIGHT_CLICK, "Heal for " +
                        ChatColor.RED + "2.5 hearts" + ChatColor.GRAY + " and heal players within " +
                        ChatColor.GREEN + "8" + ChatColor.GRAY + " blocks for " + ChatColor.RED + "1.5 hearts" + ChatColor.GRAY +
                        ". " + ChatColor.DARK_GRAY + "Charges: " + ChatColor.YELLOW + "7" + ChatColor.DARK_GRAY + " / " + ChatColor.GREEN + "15s")),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("healing_tissue").makeItem(12),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("healing_tissue").makeItem(12),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        UberItems.getItem("ornate_zombie_sword").makeItem(1),
                        new ItemStack(Material.AIR)), false, 1), "SWORD"));

        UberItems.putItem("ember_rod", new ember_rod(Material.BLAZE_ROD, "Ember Rod", UberRarity.EPIC,
                false, false, false,
                Collections.singletonList(new UberAbility("Fire Blast!", AbilityType.RIGHT_CLICK, "Shoot 3 FireBalls in rapid succession in front of you!", 30)),
                null, "SWORD"));

        UberItems.putItem("jungle_axe", new jungle_axe(Material.WOODEN_AXE, "Jungle Axe", UberRarity.UNCOMMON,
                false, false, false, Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        UberItems.getMaterial("enchanted_jungle_wood").makeItem(1),
                        UberItems.getMaterial("enchanted_jungle_wood").makeItem(1),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_jungle_wood").makeItem(1),
                        new ItemStack(Material.STICK),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.STICK),
                        new ItemStack(Material.AIR)), false, 1), "AXE"));

        UberItems.putItem("weird_tuba", new weird_tuba(Material.HOPPER, "Weird Tuba", UberRarity.UNFINISHED,
                false, false, false,
                Collections.singletonList(new UberAbility("Howl", AbilityType.RIGHT_CLICK, "You and 4 nearby players gain: /newline " + ChatColor.RED + "+30 Strength /newline "
                        + ChatColor.WHITE + "+30 Speed /newline " + ChatColor.GRAY + "for " + ChatColor.GREEN + "20" + ChatColor.GRAY + " seconds. /newline " + ChatColor.DARK_GRAY + "Effect doesn't stack.", 20)),
                new UberCraftingRecipe(Arrays.asList(
                        UberItems.getMaterial("enchanted_iron").makeItem(20),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_iron").makeItem(20),
                        UberItems.getMaterial("enchanted_iron").makeItem(20),
                        UberItems.getMaterial("golden_tooth").makeItem(20),
                        UberItems.getMaterial("enchanted_iron").makeItem(20),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_iron").makeItem(20),
                        new ItemStack(Material.AIR)), false, 1)));

        UberItems.putItem("basket_of_seeds", new basket_of_seeds(Utilities.getSkull("http://textures.minecraft.net/texture/7a6bf916e28ccb80b4ebfacf98686ad6af7c4fb257e57a8cb78c71d19dccb2"), "Basket of Seeds", UberRarity.UNFINISHED,
                false, false, false,
                Arrays.asList(new UberAbility("Seed Storage", AbilityType.LEFT_CLICK, "Place seeds in the basket."),
                new UberAbility("Farmer's Delight", AbilityType.RIGHT_CLICK, "Automatically seed a row of farmland.")),
                null));

        UberItems.putItem("treecapitator", new treecapitator(Material.GOLDEN_AXE, "Treecapitator", UberRarity.EPIC,
                false, false, false, Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        UberItems.getMaterial("enchanted_obsidian").makeItem(64),
                        UberItems.getMaterial("enchanted_obsidian").makeItem(64),
                        UberItems.getMaterial("enchanted_obsidian").makeItem(64),
                        UberItems.getMaterial("enchanted_obsidian").makeItem(64),
                        UberItems.getItem("jungle_axe").makeItem(1),
                        UberItems.getMaterial("enchanted_obsidian").makeItem(64),
                        UberItems.getMaterial("enchanted_obsidian").makeItem(64),
                        UberItems.getMaterial("enchanted_obsidian").makeItem(64),
                        UberItems.getMaterial("enchanted_obsidian").makeItem(64)), false, 1), "AXE"));

        UberItems.putItem("stonk", new stonk(Material.GOLDEN_PICKAXE, "Stonk", UberRarity.EPIC,
                false, false, false,
                Collections.singletonList(new UberAbility("Mining Speed Boost", AbilityType.RIGHT_CLICK, "Grants " +
                        ChatColor.GREEN + "Efficiency 10" + ChatColor.GRAY + " for " + ChatColor.GREEN + "20s" + ChatColor.GRAY + ". " + ChatColor.DARK_GRAY + "Cooldown: " + ChatColor.GREEN + "120s")),
                null, "PICKAXE"));

        UberItems.putItem("juju_shortbow", new juju_shortbow(Material.BOW, "Juju Shortbow", UberRarity.EPIC,
                false, false, false,
                Collections.singletonList(new UberAbility("Instantly Shoots!", AbilityType.RIGHT_CLICK, "Hits " + ChatColor.RED + "3" + " mobs on impact. /newline Can damage endermen.")),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_eye_of_ender").makeItem(32),
                        UberItems.getMaterial("enchanted_string").makeItem(64),
                        UberItems.getMaterial("null_ovoid").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_string").makeItem(64),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_quartz_block").makeItem(32),
                        UberItems.getMaterial("enchanted_string").makeItem(64)), false, 1), "BOW"));

        UberItems.putItem("emerald_blade", new emerald_blade(Material.EMERALD, "Emerald Blade", UberRarity.EPIC,
                false, false, true, Collections.emptyList(),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_emerald_block").makeItem(1),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("enchanted_emerald_block").makeItem(1),
                        new ItemStack(Material.AIR)), false, 1), "SWORD"));

        UberItems.putItem("phantom_rod", new phantom_rod(Material.FISHING_ROD, "Phantom Rod", UberRarity.LEGENDARY,
                false, false, true,
                Collections.singletonList(new UberAbility("Phantom Impel", AbilityType.LEFT_CLICK, "Terrify hooked enemies pushing them away and dealing massive damage")), null, "FISHING WEAPON"));

        UberItems.putItem("builders_wand", new builders_wand(Material.BLAZE_ROD, "Builder's Wand", UberRarity.LEGENDARY,
                false, false, true,
                Collections.singletonList(new UberAbility("Contruction!", AbilityType.RIGHT_CLICK, "Right click the face of any block to extend all connected block faces. /newline " + ChatColor.DARK_GRAY + "(consumes blocks from your inventory)")),
                null));

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
                        UberItems.getMaterial("enchanted_string").makeItem(64)), false, 1), "BOW"));

        UberItems.putItem("souls_rebound", new souls_rebound(Material.BOW, "Souls Rebound", UberRarity.EPIC,
                false, false, false,
                Collections.emptyList(), null, "BOW"));

        UberItems.putItem("aspect_of_the_dragons", new aspect_of_the_dragons(Material.DIAMOND_SWORD, "Aspect of the Dragons", UberRarity.LEGENDARY,
                false, false, false,
                Collections.singletonList(new UberAbility("Dragon Rage", AbilityType.RIGHT_CLICK, "All Monsters and Players /newline in front of you take " + ChatColor.GREEN + "4" + ChatColor.GRAY + " damage. Hit monsters take large knockback.")),
                null, "SWORD"));

        UberItems.putItem("creative_mind", new creative_mind(Material.PAINTING, "Creative Mind", UberRarity.SPECIAL,
                false, false, false,
                Collections.emptyList(), null));

        UberItems.putItem("game_breaker", new game_breaker(Material.TNT, "Game Breaker", UberRarity.SPECIAL,
                false, false, false,
                Collections.emptyList(), null));

        UberItems.putItem("quality_map", new quality_map(Material.MAP, "Quality Map", UberRarity.SPECIAL,
                false, false, false,
                Collections.emptyList(), null));

        // AMROR SETS
        UberItems.putUberArmorSet(cactus_armor.class, "Cactus", UberRarity.COMMON, UberItems.ArmorType.LEATHER, Color.fromRGB(0, 255, 0),
                Collections.singletonList(new UberAbility("Deflect", AbilityType.FULL_SET_BONUS, "Rebound" + ChatColor.GREEN + " 33.3% " + ChatColor.GRAY + "of the damage you take back at your enemy.")),
                null, null,null,null,
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR)), false, 1),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS)), false, 1),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.CACTUS)), false, 1),
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.CACTUS),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR)), false, 1));

    }
    private void registerUberMaterials() {
        UberItems.putMaterial("enchanted_ice", new UberMaterial(Material.ICE, "Enchanted Ice",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.ICE)));

        UberItems.putMaterial("enchanted_wool", new UberMaterial(Material.WHITE_WOOL, "Enchanted Wool",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.WHITE_WOOL)));

        UberItems.putMaterial("enchanted_netherrack", new UberMaterial(Material.NETHERRACK, "Enchanted Netherrack",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.NETHERRACK)));

        UberItems.putMaterial("enchanted_lapis_lazuli", new UberMaterial(Material.LAPIS_LAZULI, "Enchanted Lapis Lazuli",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.LAPIS_LAZULI)));

        UberItems.putMaterial("enchanted_lapis_block", new UberMaterial(Material.LAPIS_BLOCK, "Enchanted Lapis Block",
                UberRarity.RARE, true, true, false, "",
                utils.gser(UberItems.getMaterial("enchanted_lapis_lazuli").makeItem(32))));

        UberItems.putMaterial("enchanted_rotten_flesh", new UberMaterial(Material.ROTTEN_FLESH, "Enchanted Rotten Flesh",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.ROTTEN_FLESH)));

        UberItems.putMaterial("enchanted_flint", new UberMaterial(Material.FLINT, "Enchanted Flint",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.FLINT)));

        UberItems.putMaterial("revenant_flesh", new UberMaterial(Material.ROTTEN_FLESH, "Revenant Flesh",
                UberRarity.UNCOMMON, true, true, false, "", null));

        UberItems.putMaterial("revenant_viscera", new UberMaterial(Material.COOKED_PORKCHOP, "Revenant Viscera",
                UberRarity.RARE, true, true, false, "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("revenant_flesh").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("revenant_flesh").makeItem(32),
                        UberItems.getMaterial("enchanted_string").makeItem(32),
                        UberItems.getMaterial("revenant_flesh").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("revenant_flesh").makeItem(32),
                        new ItemStack(Material.AIR)), true, 1)));

        UberItems.putMaterial("tarantula_web", new UberMaterial(Material.STRING, "Tarantula Web",
                UberRarity.UNCOMMON, true, true, false, "", null));

        UberItems.putMaterial("tarantula_silk", new UberMaterial(Material.COBWEB, "Tarantula Silk",
                UberRarity.RARE, true, true, false, "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("tarantula_web").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("tarantula_web").makeItem(32),
                        UberItems.getMaterial("enchanted_flint").makeItem(32),
                        UberItems.getMaterial("tarantula_web").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("tarantula_web").makeItem(32),
                        new ItemStack(Material.AIR)), true, 1)));

        UberItems.putMaterial("healing_tissue", new UberMaterial(Material.ROTTEN_FLESH, "Healing Tissue",
                UberRarity.RARE, false, true, false, "", null));

        UberItems.putMaterial("enchanted_oak_wood", new UberMaterial(Material.OAK_LOG, "Enchanted Oak Wood",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.OAK_LOG)));

        UberItems.putMaterial("enchanted_jungle_wood", new UberMaterial(Material.JUNGLE_LOG, "Enchanted Jungle Wood",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.JUNGLE_LOG)));

        UberItems.putMaterial("zombie_heart", new UberMaterial(Utilities.getSkull("http://textures.minecraft.net/texture/71d7c816fc8c636d7f50a93a0ba7aaeff06c96a561645e9eb1bef391655c531"),
                "Zombie's Heart", UberRarity.RARE, false, false, false, "",
                utils.gser(UberItems.getMaterial("enchanted_rotten_flesh").makeItem(32))));

        UberItems.putMaterial("colossal_experience_bottle_upgrade", new UberMaterial(Utilities.getSkull("http://textures.minecraft.net/texture/348a7ea198ec4efd8b56bcda8aa4230039e04d1338ee98fa85897bd4f342d632"),
                "Colossal Experience Bottle Upgrade", UberRarity.SPECIAL, false, false, false, "Craft with " + ChatColor.BLUE +
                "Titanic /newline " + ChatColor.BLUE + "Experience Bottle" + ChatColor.GRAY + " to " + ChatColor.AQUA + "double " + ChatColor.GRAY + "the experience it contains.",
                null));

        UberItems.putMaterial("enchanted_emerald", new UberMaterial(Material.EMERALD, "Enchanted Emerald", UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.EMERALD)));

        UberItems.putMaterial("enchanted_emerald_block", new UberMaterial(Material.EMERALD_BLOCK, "Enchanted Emerald Block",
                UberRarity.RARE, true, false, false, "",
                utils.gser(UberItems.getMaterial("enchanted_emerald").makeItem(32))));

        UberItems.putMaterial("enchanted_bone", new UberMaterial(Material.BONE, "Enchanted Bone",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.BONE)));

        UberItems.putMaterial("enchanted_slimeball", new UberMaterial(Material.SLIME_BALL, "Enchanted Slimeball",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.SLIME_BALL)));

        UberItems.putMaterial("enchanted_slime_block", new UberMaterial(Material.SLIME_BLOCK, "Enchanted Slime Block",
                UberRarity.RARE, true, true, false, "",
                utils.gser(UberItems.getMaterial("enchanted_slimeball").makeItem(32))));

        UberItems.putMaterial("enchanted_string", new UberMaterial(Material.STRING, "Enchanted String",
                UberRarity.UNCOMMON, true, true, false, "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.STRING, 64),
                        new ItemStack(Material.STRING, 64),
                        new ItemStack(Material.STRING, 64),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.AIR)), true, 1)));

        UberItems.putMaterial("enchanted_obsidian", new UberMaterial(Material.OBSIDIAN, "Enchanted Obsidian",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.OBSIDIAN)));

        UberItems.putMaterial("enchanted_quartz", new UberMaterial(Material.QUARTZ, "Enchanted Quartz",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.QUARTZ)));

        UberItems.putMaterial("enchanted_quartz_block", new UberMaterial(Material.QUARTZ_BLOCK, "Enchanted Quartz Block",
                UberRarity.RARE, true, true, false, "",
                utils.gser(UberItems.getMaterial("enchanted_quartz").makeItem(32))));

        UberItems.putMaterial("enchanted_iron", new UberMaterial(Material.IRON_INGOT, "Enchanted Iron",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.IRON_INGOT)));

        UberItems.putMaterial("enchanted_iron_block", new UberMaterial(Material.IRON_BLOCK, "Enchanted Iron Block",
                UberRarity.RARE, true, true, false, "",
                utils.gser(UberItems.getMaterial("enchanted_iron").makeItem(32))));

        UberItems.putMaterial("enchanted_gold", new UberMaterial(Material.GOLD_INGOT, "Enchanted Gold",
                UberRarity.UNCOMMON, true, true, false, "",
                utils.gser(Material.GOLD_INGOT)));

        UberItems.putMaterial("enchanted_gold_block", new UberMaterial(Material.GOLD_BLOCK, "Enchanted Gold Block",
                UberRarity.RARE, true, true, false, "",
                utils.gser(UberItems.getMaterial("enchanted_gold").makeItem(32))));

        UberItems.putMaterial("enchanted_eye_of_ender", new UberMaterial(Material.ENDER_EYE, "Enchanted Eye of Ender",
                UberRarity.UNCOMMON, true, true, false, "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.BLAZE_POWDER, 16),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.BLAZE_POWDER, 16),
                        UberItems.getMaterial("enchanted_ender_pearl").makeItem(16),
                        new ItemStack(Material.BLAZE_POWDER, 16),
                        new ItemStack(Material.AIR),
                        new ItemStack(Material.BLAZE_POWDER, 16),
                        new ItemStack(Material.AIR)), true, 1)));

        UberItems.putMaterial("enchanted_egg", new UberMaterial(Material.EGG, "Enchanted Egg",
                UberRarity.RARE, true, true, false, "This item can be used as a minion upgrade for chicken minions. Guarantees that each chicken will drop an egg after they spawn. /newline Can also be used to craft low-rarity pets.",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.EGG, 16),
                        new ItemStack(Material.EGG, 16),
                        new ItemStack(Material.EGG, 16),
                        new ItemStack(Material.EGG, 16),
                        new ItemStack(Material.EGG, 16),
                        new ItemStack(Material.EGG, 16),
                        new ItemStack(Material.EGG, 16),
                        new ItemStack(Material.EGG, 16),
                        new ItemStack(Material.EGG, 16)), true, 1)));

        UberItems.putMaterial("wolf_tooth", new UberMaterial(Material.GHAST_TEAR, "Wolf Tooth",
                UberRarity.UNCOMMON, true, true, false, "", null));

        UberItems.putMaterial("golden_tooth", new UberMaterial(Material.GOLD_NUGGET, "Golden Tooth",
                UberRarity.RARE, true, true, false, "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("wolf_tooth").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("wolf_tooth").makeItem(32),
                        UberItems.getMaterial("enchanted_gold").makeItem(32),
                        UberItems.getMaterial("wolf_tooth").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("wolf_tooth").makeItem(32),
                        new ItemStack(Material.AIR)), true, 1)));

        UberItems.putMaterial("null_sphere", new UberMaterial(Material.FIREWORK_STAR, "Null Sphere",
                UberRarity.UNCOMMON, true, true, false, "", null));

        UberItems.putMaterial("null_ovoid", new UberMaterial(Material.ENDERMAN_SPAWN_EGG, "Null Ovoid",
                UberRarity.RARE, true, true, false, "",
                new UberCraftingRecipe(Arrays.asList(
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("null_sphere").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("null_sphere").makeItem(32),
                        UberItems.getMaterial("enchanted_obsidian").makeItem(32),
                        UberItems.getMaterial("null_sphere").makeItem(32),
                        new ItemStack(Material.AIR),
                        UberItems.getMaterial("null_sphere").makeItem(32),
                        new ItemStack(Material.AIR)), true, 1)));

        UberItems.putMaterial("golden_powder", new UberMaterial(Material.GLOWSTONE_DUST, "Golden Powder",
                UberRarity.EPIC, true, true, false, "", null));
    }
}