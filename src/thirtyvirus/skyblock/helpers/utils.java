package thirtyvirus.skyblock.helpers;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import thirtyvirus.skyblock.nes;
import thirtyvirus.uber.helpers.UberCraftingRecipe;
import thirtyvirus.uber.helpers.Utilities;

import java.util.*;

import static thirtyvirus.uber.helpers.Utilities.*;

public final class utils {

    private static Map<Player, List<BlockState>> wandOops = new HashMap<>();

    // "Generate Standard Enchanted Recipe"
    // generate the standard recipe for an enchanted Uber Material (160 of the base item)
    public static UberCraftingRecipe gser(ItemStack ingredient) {
        ItemStack newIngredient = ingredient.clone();
        newIngredient.setAmount(32);

        return new UberCraftingRecipe(Arrays.asList(
                new ItemStack(Material.AIR),
                newIngredient,
                new ItemStack(Material.AIR),
                newIngredient,
                newIngredient,
                newIngredient,
                new ItemStack(Material.AIR),
                newIngredient,
                new ItemStack(Material.AIR)), true, 1);
    }
    public static UberCraftingRecipe gser(Material material) {
        return gser(new ItemStack(material));
    }

    // save a builder's wand action for the player
    // places the first element of the list as a blockstate from before the wand usage
    public static void putWandOops(Player player, List<BlockState> blocks) {
        wandOops.put(player, blocks);
    }

    // restore the most recent builder's wand action for the player
    // assumes that the first element of the list is the blockstate from before the wand usage
    public static void restoreWandOops(Player player) {
        if (!wandOops.containsKey(player)) {
            warnPlayer(player, "No restore point saved, sorry!");
            return;
        }

        List<BlockState> blocks = wandOops.get(player);
        BlockState returnPoint = blocks.get(0); World world = blocks.get(0).getWorld();

        for (int counter = 1; counter < blocks.size(); counter++) {
            world.getBlockAt(blocks.get(counter).getLocation()).setType(blocks.get(counter).getType());
            Bukkit.getLogger().info(blocks.get(counter).getType().toString());
        }


        wandOops.remove(player);
        Utilities.informPlayer(player, "Builder's Wand use undone, removed " + (blocks.size() - 1) + " blocks");

        if (player.getGameMode() == GameMode.SURVIVAL)
            Utilities.givePlayerItemSafely(player, new ItemStack(returnPoint.getType(), blocks.size() - 1));
    }

    // enforces an ability cooldown, but with multiple charges
    public static boolean enforceCharges(Player player, String key, int charges, double seconds, ItemStack item, boolean throwError) {
        double time = (double) System.currentTimeMillis() / 1000;

        // get times each previous charge was used
        int shortestWait = (int)seconds;
        for (int counter = 0; counter < charges; counter++) {
            int lastTime = getIntFromItem(item, key + counter);

            // add "time last used" key if not already there, automatically assume charge can be used
            if (lastTime == 0) {
                storeIntInItem(item, (int) time, key + counter);
                return false;
            }
            // was at least one charge last used longer than "seconds" seconds ago?
            else {
                if (time - seconds > lastTime) { // yes, allow the action (plus update the last time used)
                    storeIntInItem(item, (int) time, key + counter);
                    return false;
                } else { // no, check for another charge
                    int timeLeft = (int) time - lastTime;
                    timeLeft = (int) seconds - timeLeft;
                    if (shortestWait > timeLeft) shortestWait = timeLeft;
                }
            }

        }

        // no charges were available within the last 'seconds' seconds, enforce cooldown
        if (throwError) warnPlayer(player, "This ability is on cooldown for " + shortestWait + "s.");
        return true;
    }

}
