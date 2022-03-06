package thirtyvirus.skyblock.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionType;
import org.bukkit.util.Vector;
import thirtyvirus.uber.UberItem;
import thirtyvirus.uber.UberItems;
import thirtyvirus.uber.helpers.Utilities;

import java.util.List;
import java.util.Random;
import java.util.UUID;

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
            else if (UberItems.getItem("hook_shot").compare(item)) {
                UberItem uber = Utilities.getUber(item);

                // repair rod each time it is used
                Utilities.repairItem(item);

                // enforce premium vs lite, item rarity perms, item specific perms
                if (Utilities.enforcePermissions(player, uber)) return;

                // enforce 1.5s cooldown on the grappling hook
                if (Utilities.enforceCooldown(player, "grapple", 1.0, item, false)) {
                    Utilities.warnPlayer(player, "Whow! Slow down there!");
                    return;
                }

                Location l1 = player.getLocation();
                Location l2 = event.getHook().getLocation();
                Vector v = new Vector(l2.getX() - l1.getX(), 1, l2.getZ() - l1.getZ());
                v.multiply(3);
                v = new Vector(v.getX(), 1, v.getZ());
                player.setVelocity(v);
            }
        }
    }

    @EventHandler
    private void onPlayerPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player)event.getEntity();
        ItemStack item = event.getItem().getItemStack();

        if (item.getType() != Material.SNOWBALL) return;
        if (Utilities.isUber(item) || Utilities.isUberMaterial(item)) return;

        // process picking up snowballs to fill frosty the snow cannon
        ItemStack cannon = Utilities.searchFor(player.getInventory(), UberItems.getItem("frosty_the_snow_cannon"));
        if (cannon != null) {
            int ammo = Utilities.getIntFromItem(cannon, "ammo");
            if (ammo != 1000) {
                if (ammo + item.getAmount() <= 1000) {
                    ammo += item.getAmount();
                }
                else {
                    int leftover = 1000 - ammo;
                    item.setAmount(item.getAmount() - leftover);
                    ammo = 1000;
                    player.getInventory().addItem(item.clone());
                }
                event.getItem().remove();
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                event.setCancelled(true);
                Utilities.storeIntInItem(cannon, ammo, "ammo");
                UberItems.getItem("frosty_the_snow_cannon").updateLore(cannon);
                return;
            }
        }

        // process picking up snowballs to fill frosty the snow blaster
        ItemStack blaster = Utilities.searchFor(player.getInventory(), UberItems.getItem("frosty_the_snow_blaster"));
        if (blaster != null) {
            int ammo = Utilities.getIntFromItem(blaster, "ammo");
            if (ammo != 2000) {
                if (ammo + item.getAmount() <= 2000) {
                    ammo += item.getAmount();
                }
                else {
                    int leftover = 2000 - ammo;
                    item.setAmount(item.getAmount() - leftover);
                    ammo = 2000;
                    player.getInventory().addItem(item.clone());
                }
                event.getItem().remove();
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
                event.setCancelled(true);
                Utilities.storeIntInItem(blaster, ammo, "ammo");
                UberItems.getItem("frosty_the_snow_blaster").updateLore(blaster);
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

        // prevent throwing vanilla egg alongside custom ones
        if (UberItems.getItem("stone_platform").compare(event.getItem()) ||
                UberItems.getItem("bridge_egg").compare(event.getItem())) {
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

        // don't throw vanilla experience bottle when throwing grand, titanic, or colossal experience bottles
        if (UberItems.getItem("grand_experience_bottle").compare(event.getItem()) ||
                UberItems.getItem("titanic_experience_bottle").compare(event.getItem()) ||
                UberItems.getItem("colossal_experience_bottle").compare(event.getItem())) {
            event.setCancelled(true);
        }

        // don't create a filled map from Quality Map
        if (UberItems.getItem("quality_map").compare(event.getItem())) {
            event.setCancelled(true);
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

        if (UberItems.getItem("souls_rebound").compare(event.getBow())) {
            Utilities.tagEntity(event.getProjectile(), player.getUniqueId().toString(), "rebound");
        }

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

        // tag the target entity with souls rebound ability
        if (event.getEntity() instanceof Arrow && event.getHitBlock() == null && event.getHitEntity() instanceof LivingEntity &&
                !Utilities.getEntityTag(event.getEntity(), "rebound").equals("")) {

            // check if the mob already is effected by another souls rebound arrow
            String testForPrevious = Utilities.getEntityTag(event.getHitEntity(), "reboundtime");
            double time = (double)System.currentTimeMillis() / 1000;
            double oldTime = 0; if (!testForPrevious.equals("")) oldTime = Double.parseDouble(testForPrevious);
            if (time - 5 > oldTime) {
                Utilities.tagEntity(event.getHitEntity(), Utilities.getEntityTag(event.getEntity(), "rebound"), "rebound");
                Utilities.tagEntity(event.getHitEntity(), "0", "rdmg");
                Utilities.tagEntity(event.getHitEntity(), Double.toString(time), "reboundtime");
                Utilities.scheduleTask(()->soulsReboundAbility((LivingEntity)event.getHitEntity()), 100);
                event.getEntity().getWorld().playSound(event.getEntity().getLocation(), Sound.ENTITY_GHAST_HURT, 1, 3);
            }

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

    @EventHandler
    private void onBottleLand(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof ThrownExpBottle)) return;

        String exp = Utilities.getEntityTag(event.getEntity(), "uberexp");
        int experience = 0;
        switch (exp) {
            case "grand": experience = 3300; break;
            case "titanic": experience = 318122; break;
            case "colossal": experience = 607717; break;
        }
        if (experience != 0) {
            ExperienceOrb orb = event.getEntity().getWorld().spawn(event.getEntity().getLocation(), ExperienceOrb.class);
            orb.setExperience(experience);
        }

    }

    @EventHandler
    private void onEntityDamaged(EntityDamageByEntityEvent event) {

        // cancel damage from rebound arrow directly
        if (event.getDamager() instanceof Arrow) {
            if (!Utilities.getEntityTag(event.getDamager(), "rebound").equals("")) {
                event.getDamager().remove();
                event.setCancelled(true);
            }
        }

        // reduce fall damage when holding Hook Shot
        if (event.getEntity() instanceof Player && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player player = (Player)event.getEntity();
            if (UberItems.getItem("hook_shot").compare(player.getInventory().getItemInMainHand())) {
                event.setDamage(event.getDamage() * 0.5f);
            }
        }

        // cancel knockback if player is holding a Sheep Plushie
        if (event.getEntity() instanceof Player && Utilities.searchFor(((Player) event.getEntity()).getInventory(), UberItems.getItem("sheep_plushie")) != null) {
            Player player = (Player)event.getEntity();
            player.damage(event.getFinalDamage());
            event.setCancelled(true);
        }

        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player)event.getDamager();
        if (!(event.getEntity() instanceof LivingEntity)) return;
        LivingEntity entity = (LivingEntity)event.getEntity();

        // mob is being effected by Souls Rebound Ability
        if (Utilities.getEntityTag(entity, "rebound").equals(player.getUniqueId().toString())) {
            double damage = 0; String dmg = Utilities.getEntityTag(entity, "rdmg");
            if (!dmg.equals("")) damage = Double.parseDouble(dmg);
            damage += event.getFinalDamage();

            Utilities.tagEntity(entity, Double.toString(damage), "rdmg");
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlayerHit(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player)event.getEntity();
        if (!(event.getDamager() instanceof LivingEntity)) return;
        LivingEntity entity = (LivingEntity)event.getDamager();

        if (Utilities.hasFullSetBonus(player, "deflect")) {
            entity.damage(event.getFinalDamage() * 0.3);
        }
    }

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {

        // don't drop a kloonboat
        ItemStack item = event.getItemDrop().getItemStack();
        if (UberItems.getItem("kloonboat").compare(item)) {
            event.getItemDrop().remove();
            Utilities.scheduleTask(()-> event.getPlayer().getInventory().addItem(item.clone()), 1);
            event.getPlayer().getWorld().setStorm(true);
            event.getPlayer().getWorld().setWeatherDuration(20*60);
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

    private static void soulsReboundAbility(LivingEntity entity) {
        if (entity.isDead()) return;

        String s1 = Utilities.getEntityTag(entity, "rebound");
        String s2 = Utilities.getEntityTag(entity, "rdmg");

        Player player = Bukkit.getPlayer(UUID.fromString(s1));
        if (player == null) return;
        double damage = 0; if (!s2.equals("")) damage = Double.parseDouble(s2);
        damage *= 1.2;

        Utilities.informPlayer(player, "Dealt " + Math.round(damage) + " damage to enemy using Souls Rebound");
        player.playSound(player.getLocation(), Sound.ENTITY_GHAST_DEATH, 1, 3);
        entity.damage(damage);

        Utilities.tagEntity(entity, "", "reboundplayer");
        Utilities.tagEntity(entity, "", "rdmg");
    }

}
