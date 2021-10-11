package events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;
import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.UberItems;
import thirtyvirus.uber.helpers.Utilities;

import java.util.Random;

public class UberEvent implements Listener {

    Random rand = new Random();

    // process grappling hook ability
    @EventHandler(priority= EventPriority.HIGH)
    private void onFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.REEL_IN || event.getState() == PlayerFishEvent.State.CAUGHT_FISH || event.getState() == PlayerFishEvent.State.IN_GROUND) {
            Player player = event.getPlayer();
            ItemStack item = player.getInventory().getItemInMainHand();

            if (UberItems.getItem("grappling_hook").compare(item)) {
                UberItem uber = Utilities.getUber(item);

                // repair rod each time it is used
                Utilities.repairItem(item);

                // enforce premium vs lite, item rarity perms, item specific perms
                if (Utilities.enforcePermissions(player, uber)) return;

                // enforce 1.5s cooldown on the grappling hook
                if (Utilities.enforceCooldown(player, "grapple", 1.5, item, false)) {
                    Utilities.warnPlayer(player, "Whow! Slow down there!");
                    return;
                }

                Location l1 = player.getLocation();
                Location l2 = event.getHook().getLocation();
                Vector v = new Vector(l2.getX() - l1.getX(), 1, l2.getZ() - l1.getZ());
                player.setVelocity(v);
            }

        }

    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        // don't pull bow back with juju shortbow
        if ((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR)
                && UberItems.getItem("juju_shortbow").compare(event.getItem())) {
            event.getItem().setType(Material.STICK);
            Utilities.scheduleTask(() -> event.getItem().setType(Material.BOW), 2);
        }

    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        // 10% chance to spawn endermite when mining endstone if not using a stonk
        if (event.getBlock().getType() == Material.END_STONE && !UberItems.getItem("stonk").compare(event.getPlayer().getInventory().getItemInMainHand())) {
            if (rand.nextInt(100) > 90) event.getBlock().getWorld().spawnEntity(event.getBlock().getLocation().add(0.5,0,0.5), EntityType.ENDERMITE);
        }
    }

    @EventHandler
    private void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;
        Player player = (Player)event.getEntity();


        if (UberItems.getItem("runaans_bow").compare(event.getBow())) {
            shootAdditionalArrow(event, player, 0.1);
            shootAdditionalArrow(event, player, -0.1);
        }
    }

    @EventHandler
    private void onArrowLand(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow && !Utilities.getEntityTag(event.getEntity(), "homing").equals("")) {
            // delete any homing arrows that land
            if (event.getHitBlock() != null) event.getEntity().remove();
        }
    }

    private void shootAdditionalArrow(EntityShootBowEvent event, Player player, double whatAngle) {
        Arrow centerArrow = (Arrow) event.getProjectile();

        Arrow newArrow = event.getEntity().getWorld().spawnArrow(event.getEntity().getLocation().clone().add(0,1.5,0), rotateVector(event.getProjectile().getVelocity(), whatAngle), event.getForce() * 2, 0f);
        newArrow.setVelocity(newArrow.getVelocity().multiply(1.5)); newArrow.setShooter(event.getEntity());
        newArrow.setDamage(centerArrow.getDamage() * 0.4F);
        if (centerArrow.getBasePotionData().getType() != PotionType.UNCRAFTABLE) newArrow.setBasePotionData(centerArrow.getBasePotionData());
        newArrow.setFireTicks(centerArrow.getFireTicks()); newArrow.setCritical(centerArrow.isCritical());
        newArrow.setKnockbackStrength(centerArrow.getKnockbackStrength()); newArrow.setGlowing(centerArrow.isGlowing());
        Utilities.tagEntity(newArrow, player.getName(), "homing");
    }

    private Vector rotateVector(Vector vector, double whatAngle) {
        double sin = Math.sin(whatAngle);
        double cos = Math.cos(whatAngle);
        double x = vector.getX() * cos + vector.getZ() * sin;
        double z = vector.getX() * -sin + vector.getZ() * cos;

        return vector.setX(x).setZ(z);
    }

}
