package thirtyvirus.skyblock.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;
import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.UberItems;
import thirtyvirus.uber.helpers.Utilities;

import java.util.List;
import java.util.Random;

public class UberEvent implements Listener {

    Random rand = new Random();

    @EventHandler(priority = EventPriority.HIGH)
    private void onFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // process grappling hook ability
        if (event.getState() == PlayerFishEvent.State.REEL_IN || event.getState() == PlayerFishEvent.State.CAUGHT_FISH || event.getState() == PlayerFishEvent.State.IN_GROUND) {

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
    private void onHookHitEntity(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof FishHook)) return;
        FishHook hook = (FishHook)event.getEntity();

        if (!(hook.getShooter() instanceof Player)) return;
        Player shooter = (Player)hook.getShooter();
        ItemStack item = shooter.getInventory().getItemInMainHand();

        // process phantom rod ability
        if (UberItems.getItem("phantom_rod").compare(shooter.getInventory().getItemInMainHand()) && event.getHitEntity() instanceof LivingEntity) {
            Utilities.storeStringInItem(item, event.getHitEntity().getUniqueId().toString(), "caught");
        }

    }

    @EventHandler
    private void onEntitySpawn(PlayerInteractEvent event) {

        // prevent throwing vanilla egg alongside custom one
        if (UberItems.getItem("stone_platform").compare(event.getItem())) {
            event.setCancelled(true);
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
            if (rand.nextInt(100) > 90)
                event.getBlock().getWorld().spawnEntity(event.getBlock().getLocation().add(0.5, 0, 0.5), EntityType.ENDERMITE);
        }
    }

    @EventHandler
    private void onBowShoot(EntityShootBowEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) return;
        Player player = (Player) event.getEntity();


        if (UberItems.getItem("runaans_bow").compare(event.getBow())) {
            Utilities.repairItem(event.getBow());
            Utilities.directEntity(shootAdditionalArrow(event, player, 0.1), player, 5, 5, 5);
            Utilities.directEntity(shootAdditionalArrow(event, player, -0.1), player, 5, 5, 5);
        }
    }

    @EventHandler
    private void onArrowLand(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow && !Utilities.getEntityTag(event.getEntity(), "homing").equals("")) {
            // delete any homing arrows that land
            if (event.getHitBlock() != null) event.getEntity().remove();
        }

        if (event.getEntity() instanceof Arrow && !Utilities.getEntityTag(event.getEntity(), "juju").equals("")) {

            // damage 3 nearby mobs on impact
            List<Entity> nearby = event.getEntity().getNearbyEntities(4, 4, 4);
            int max = 3;
            for (Entity e : nearby) {
                if (e instanceof LivingEntity) {
                    LivingEntity en = (LivingEntity) e;
                    en.damage(((Arrow) event.getEntity()).getDamage());

                    // prevent enderman from teleporting away if applicable
                    if (e instanceof Enderman) {
                        Enderman enderman = (Enderman) e;
                        enderman.damage(((Arrow) event.getEntity()).getDamage());
                        freezeEntity(enderman, event.getEntity().getLocation().add(0, -1 * getDistanceFromGround(event.getEntity()) + 1, 0), 2);
                    }

                    max--;
                }
                if (max <= 0) break;
            }
            event.getEntity().remove();

        }
    }

    private Arrow shootAdditionalArrow(EntityShootBowEvent event, Player player, double whatAngle) {
        Arrow centerArrow = (Arrow) event.getProjectile();

        Arrow newArrow = event.getEntity().getWorld().spawnArrow(event.getEntity().getLocation().clone().add(0, 1.5, 0), rotateVector(event.getProjectile().getVelocity(), whatAngle), event.getForce() * 2, 0f);
        newArrow.setVelocity(newArrow.getVelocity().multiply(1.5));
        newArrow.setShooter(event.getEntity());
        newArrow.setDamage(centerArrow.getDamage() * 0.4F);
        if (centerArrow.getBasePotionData().getType() != PotionType.UNCRAFTABLE)
            newArrow.setBasePotionData(centerArrow.getBasePotionData());
        newArrow.setFireTicks(centerArrow.getFireTicks());
        newArrow.setCritical(centerArrow.isCritical());
        newArrow.setKnockbackStrength(centerArrow.getKnockbackStrength());
        newArrow.setGlowing(centerArrow.isGlowing());
        Utilities.tagEntity(newArrow, player.getName(), "homing");

        return newArrow;
    }

    private Vector rotateVector(Vector vector, double whatAngle) {
        double sin = Math.sin(whatAngle);
        double cos = Math.cos(whatAngle);
        double x = vector.getX() * cos + vector.getZ() * sin;
        double z = vector.getX() * -sin + vector.getZ() * cos;

        return vector.setX(x).setZ(z);
    }

    private static void freezeEntity(Entity e, Location l, int counter) {

        e.teleport(l);
        if (counter > 0) Utilities.scheduleTask(() -> freezeEntity(e, l, counter - 1), 2);
    }

    private static int getDistanceFromGround(Entity e) {
        Location loc = e.getLocation().clone();
        double y = loc.getBlockY();
        int distance = 0;
        for (double i = y; i >= 0; i--) {
            loc.setY(i);
            if (loc.getBlock().getType().isSolid()) break;
            distance++;
        }
        return distance;

    }

}
