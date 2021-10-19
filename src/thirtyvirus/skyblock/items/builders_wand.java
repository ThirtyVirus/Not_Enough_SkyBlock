package thirtyvirus.skyblock.items;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import thirtyvirus.skyblock.nes;
import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.helpers.UberAbility;
import thirtyvirus.uber.helpers.UberCraftingRecipe;
import thirtyvirus.uber.helpers.UberRarity;
import thirtyvirus.uber.helpers.Utilities;

import java.util.ArrayList;
import java.util.List;

public class builders_wand extends UberItem {
	// TODO make the wand obey area build permissions

	public builders_wand(Material material, String name, UberRarity rarity, boolean stackable, boolean oneTimeUse, boolean hasActiveEffect, List<UberAbility> abilities, UberCraftingRecipe craftingRecipe) {
		super(material, name, rarity, stackable, oneTimeUse, hasActiveEffect, abilities, craftingRecipe);
	}
	public void onItemStackCreate(ItemStack item) {
		Utilities.addEnchantGlint(item);
	}
	public void getSpecificLorePrefix(List<String> lore, ItemStack item) { }
	public void getSpecificLoreSuffix(List<String> lore, ItemStack item) { }

	public boolean leftClickAirAction(Player player, ItemStack item) { return false; }
	public boolean leftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
	public boolean rightClickAirAction(Player player, ItemStack item) { return false; }

	public boolean rightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) {
		Utilities.scheduleTask(()->fillConnectedFaces(player, block, event.getBlockFace(), item), 1);
		return true;
	}

	public boolean shiftLeftClickAirAction(Player player, ItemStack item) { return false; }
	public boolean shiftLeftClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return false; }
	public boolean shiftRightClickAirAction(Player player, ItemStack item) { return false; }
	public boolean shiftRightClickBlockAction(Player player, PlayerInteractEvent event, Block block, ItemStack item) { return rightClickBlockAction(player, event, block, item); }
	public boolean middleClickAction(Player player, ItemStack item) { return false; }

	public boolean hitEntityAction(Player player, EntityDamageByEntityEvent event, Entity target, ItemStack item) { return false; }
	public boolean breakBlockAction(Player player, BlockBreakEvent event, Block block, ItemStack item) { return false; }
	public boolean clickedInInventoryAction(Player player, InventoryClickEvent event, ItemStack item, ItemStack addition) { return false; }
	public boolean activeEffect(Player player, ItemStack item) { return false; }
	
	// main logic for builder's wand
	public void fillConnectedFaces(Player player, Block origin, BlockFace face, ItemStack item) {
		Material fillMaterial = origin.getType();
		int blocksInInventory = countBlocks(player.getInventory(), origin.getType());
		boolean needBlocks = (player.getGameMode() != GameMode.CREATIVE);
		int blockLimit = 2048; if (blocksInInventory < blockLimit && needBlocks) blockLimit = blocksInInventory;
		ArrayList<Block> blocks = new ArrayList<>(); blocks.add(origin);
		Location l; World w = player.getWorld(); Vector[] check = null; Vector translate = null;
		int blocksPlaced = 0;

		List<BlockState> blockStates = new ArrayList<>();
		blockStates.add(origin.getState());

		// establish which blocks to check, depending on the block face's axis
		switch (face) {
			case NORTH: //Z-
			case SOUTH: //Z+
				check = new Vector[] {
						new Vector(-1,-1,0), new Vector(-1,0,0), new Vector(-1,1,0),
						new Vector(0,-1,0), new Vector(0,1,0), new Vector(1,-1,0),
						new Vector(1,0,0), new Vector(1,1,0) }; break;
			case EAST: //X+
			case WEST: //X-
				check = new Vector[] {
						new Vector(0,-1,-1), new Vector(0,-1,0), new Vector(0,-1,1),
						new Vector(0,0,-1), new Vector(0,0,1), new Vector(0,1,-1),
						new Vector(0,1,0), new Vector(0,1,1) }; break;
			case UP: //Y+
			case DOWN: //Y-
				check = new Vector[] {
						new Vector(-1,0,-1), new Vector(-1,0,0), new Vector(-1,0,1),
						new Vector(0,0,-1), new Vector(0,0,1), new Vector(1,0,-1),
						new Vector(1,0,0), new Vector(1,0,1) }; break;
		}
		switch (face) {
			case NORTH: translate = new Vector(0,0,-1); break;
			case SOUTH: translate = new Vector(0,0,1); break;
			case EAST: translate = new Vector(1,0,0); break;
			case WEST: translate = new Vector(-1,0,0); break;
			case UP: translate = new Vector(0,1,0); break;
			case DOWN: translate = new Vector(0,-1,0); break;
		}

		// place blocks
		while(blocks.size() > 0 && blockLimit > 0) {
			// search surrounding matching blocks for those that are "connected" on this axis
			l = blocks.get(0).getLocation();
			for (Vector vector : check) {
				if (w.getBlockAt(l.clone().add(vector)).getType() == fillMaterial &&
						w.getBlockAt(l.clone().add(vector).clone().add(translate)).getType() == Material.AIR)
					blocks.add(w.getBlockAt(l.clone().add(vector)));
			}

			// place new material at the selected block
			Block fillBlock = w.getBlockAt(l.clone().add(translate));
			if (canPlaceBlock(player, fillBlock.getLocation())) {

				blocks.removeIf(blocks.get(0)::equals);
				if (fillBlock.getType() != fillMaterial) {
					blockStates.add(fillBlock.getState());
					fillBlock.setType(fillMaterial);
					blockLimit -= 1; blocksPlaced++;
				}

				if (needBlocks && blocksPlaced == blockLimit) break;
			}
			else { blocks.removeIf(blocks.get(0)::equals); blockLimit -= 1; }
		}

		// finalize block place action + take blocks from inv if in survival
		if (blocksPlaced != 0) {
			if (needBlocks) removeBlocks(player.getInventory(), origin.getType(), blocksPlaced);

			player.playSound(player.getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 1, 1);
			player.getWorld().playEffect(player.getEyeLocation(), Effect.SMOKE, 0);
		}

		nes.putWandOops(player, blockStates);
	}
	
	// counts amount of blocks of type m in inventory inv
	public int countBlocks(Inventory inv, Material m) {
		int blockAmount = 0;
		
		for (ItemStack item : inv){
			if (item != null){
				if (item.getType() == m){
					blockAmount += item.getAmount();
				}
			}
		}
		return blockAmount;
	}
	
	// remove blockAmount blocks of type m from inv
	public void removeBlocks(Inventory inv, Material m, int blockAmount){
		inv.removeItem(new ItemStack (m, blockAmount));
	}
	
	public boolean canPlaceBlock(Player player, Location l) {

		return true;
		
		//BlockBreakEvent e = new BlockBreakEvent(l.getWorld().getBlockAt(l), player);
		//Bukkit.getServer().getPluginManager().callEvent(e);
		////return !e.isCancelled();
		
	}
}