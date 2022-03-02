package thirtyvirus.skyblock.items;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
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

import java.util.*;

public class fairy_soul_compass extends UberItem {

    public fairy_soul_compass(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
        super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
    }
    public void onItemStackCreate(ItemStack item) { }
    public void getSpecificLorePrefix(List<String> lore, ItemStack item) {
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "When held, points towards the");
        lore.add("" + ChatColor.RESET + ChatColor.GRAY + "nearest undiscovered " +
                ChatColor.LIGHT_PURPLE + "Fairy Soul" + ChatColor.GRAY + "!");
    }
    public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }
    public boolean leftClickAirAction(Player player, ItemStack item) {
        // TODO remove this after done testing
        Utilities.givePlayerItemSafely(player, Utilities.getSkull("http://textures.minecraft.net/texture/b96923ad247310007f6ae5d326d847ad53864cf16c3565a181dc8e6b20be2387"));

        return false;
    }
    public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return leftClickAirAction(player, item); }

    public boolean rightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) {

        // test if the clicked block is a player head with the fairy soul texture
        if (block.getType() == Material.PLAYER_HEAD && isSkullCorrect(block, "http://textures.minecraft.net/texture/b96923ad247310007f6ae5d326d847ad53864cf16c3565a181dc8e6b20be2387")) {

            // test if the player has already obtained that fairy soul
            String loc = Utilities.toLocString(block.getLocation());
            String locs = Utilities.getStringFromItem(item, "souls");
            if (!loc.equals("") && locs != null && locs.contains(loc)) {
                Utilities.informPlayer(player, ChatColor.LIGHT_PURPLE + "You have already found the Fairy Soul!");
            }
            else {
                Utilities.informPlayer(player, "" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "SOUL! " +
                        ChatColor.RESET + ChatColor.WHITE + "You found a " + ChatColor.LIGHT_PURPLE + "Fairy Soul" +
                        ChatColor.WHITE + "!");
                locs = locs + " " + loc;
                Utilities.storeStringInItem(item, locs, "souls");
            }

        }

        return false;
    }

    public boolean shiftLeftClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean shiftRightClickAirAction(Player player, ItemStack item) { return false; }
    public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
    public boolean middleClickAction(Player player, ItemStack item) { return false; }
    public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item) { return false; }
    public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) { return false; }
    public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) { return false; }
    public boolean activeEffect(Player player, ItemStack item) {

        // get all of the tile entities in a radius of 5 chunks
        Collection<Chunk> chunks = getNearbyChunks(player.getLocation().getChunk(), 5);
        List<BlockState> entities = new ArrayList<>();
        for (Chunk chunk : chunks) entities.addAll(Arrays.asList(chunk.getTileEntities()));

        Block closest = null;
        for (BlockState state : entities) {
            if (state.getBlock().getType() != Material.PLAYER_HEAD) continue;
            if (closest == null || player.getLocation().distance(state.getBlock().getLocation()) < player.getLocation().distance(closest.getLocation())) {

                // test if the skull is a fairy soul
                if (isSkullCorrect(state.getBlock(), "http://textures.minecraft.net/texture/b96923ad247310007f6ae5d326d847ad53864cf16c3565a181dc8e6b20be2387")) {

                    // only target that soul if it is undiscovered
                    String loc = Utilities.toLocString(state.getLocation());
                    String locs = Utilities.getStringFromItem(item, "souls");
                    if (!(!loc.equals("") && locs != null && locs.contains(loc))) {
                        closest = state.getBlock();
                    }
                }

            }
        }
        if (closest != null) player.setCompassTarget(closest.getLocation());
        else player.setCompassTarget(player.getLocation());

        return false;
    }

    private boolean isSkullCorrect(Block b, String skullTexture) {
        // TODO actually implement this (needs NMS?)
//
//        Skull skull = (Skull) b.getState();
//        TileEntitySkull skullTile = (TileEntitySkull) ((CraftWorld)skull.getWorld()).getHandle().getTileEntity(new BlockPosition(skull.getX(), skull.getY(), skull.getZ()));
//        GameProfile profile = skullTile.gameProfile;
//        Collection<Property> textures = profile.getProperties().get("textures");
//        String text = "";
//
//        for (Property texture : textures) {
//            text = texture.getValue();
//        }
//
//        String decoded = Base64Coder.decodeString(text);
//        String textureNumber = decoded.replace("{textures:{SKIN:{url:\"", "").replace("\"}}}", "").replace("http://textures.minecraft.net/texture/", "").trim();
//
//        if(skullTexture.equalsIgnoreCase(textureNumber)){
//            return true;
//        }
//        return false;
        return true;
    }

    private static Collection<Chunk> getNearbyChunks(Chunk origin, int radius) {
        World world = origin.getWorld();

        int length = (radius * 2) + 1;
        Set<Chunk> chunks = new HashSet<>(length * length);

        int cX = origin.getX();
        int cZ = origin.getZ();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                chunks.add(world.getChunkAt(cX + x, cZ + z));
            }
        }
        return chunks;
    }
}
