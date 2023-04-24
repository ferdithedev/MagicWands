package me.ferdithedev.magicwands;

import me.ferdithedev.magicwands.items.*;
import me.ferdithedev.overblock.api.OBAPI;
import me.ferdithedev.overblock.obitems.ItemPackage;
import me.ferdithedev.overblock.obitems.OBItem;
import me.ferdithedev.overblock.util.Effects;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class MagicWands extends JavaPlugin {

    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        List<OBItem> items = new ArrayList<>();
        items.add(new Accio(this));
        items.add(new AlarteAscendare(this));
        items.add(new AvadaKedavra(this));
        items.add(new Avifors(this));

        items.add(new Bombarda(this));
        items.add(new BombardaMaxima(this));
        items.add(new BrackiumEmendo(this));

        items.add(new CarpeRetractum(this));
        items.add(new CruciatusCurse(this));
        ItemPackage itemPackage = new ItemPackage(this,"MagicWands","§r§a§lMagic Wands", Material.STICK,items,"§r§7Fun new wands inspired by the harry potter universe");
        OBAPI.registerItemPackage(itemPackage);
    }

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static Player entityLookingAt(Player viewer) {
        RayTraceResult result = viewer.getWorld().rayTraceEntities(viewer.getEyeLocation(),viewer.getEyeLocation().getDirection(), 10, 2, e -> e instanceof Player && !e.equals(viewer));
        if(result != null) {
            if(result.getHitEntity() != null) {
                return (Player) result.getHitEntity();
            }
        }
        return null;
    }

    public static Block blockLookingAt(Player viewer) {
        RayTraceResult result = viewer.getWorld().rayTraceBlocks(viewer.getEyeLocation(),viewer.getEyeLocation().getDirection(),10, FluidCollisionMode.SOURCE_ONLY);
        if(result != null) {
            if(result.getHitBlock() != null) {
                return result.getHitBlock();
            }
        }
        return null;
    }

    public static Vector rotateVector(Vector v, float yawDegrees, float pitchDegrees) {
        double yaw = Math.toRadians(-1 * (yawDegrees + 90));
        double pitch = Math.toRadians(-pitchDegrees);

        double cosYaw = Math.cos(yaw);
        double cosPitch = Math.cos(pitch);
        double sinYaw = Math.sin(yaw);
        double sinPitch = Math.sin(pitch);

        double initialX, initialY, initialZ;
        double x, y, z;

        initialX = v.getX();
        initialY = v.getY();
        x = initialX * cosPitch - initialY * sinPitch;
        y = initialX * sinPitch + initialY * cosPitch;

        initialZ = v.getZ();
        initialX = x;
        z = initialZ * cosYaw - initialX * sinYaw;
        x = initialZ * sinYaw + initialX * cosYaw;

        return new Vector(x, y, z);
    }

    public static void spellSounds(Player player, Location eyes, Location lightningTarget, Effects.ParticleEffect particleEffect) {
        Effects.playSoundDistance(player.getLocation(),8, Sound.ENTITY_ELDER_GUARDIAN_CURSE,0.2f,2);
        Effects.playSoundDistance(player.getLocation(),8,Sound.BLOCK_BEACON_ACTIVATE,0.3f,2f);
        Effects.playSoundDistance(player.getLocation(),8,Sound.ENTITY_WITHER_DEATH,0.5f,2f);
        Effects.playSoundDistance(player.getLocation(),8,Sound.ENTITY_ENDER_DRAGON_HURT,5f,2f);
        Effects.playSoundDistance(player.getLocation(),8,Sound.ENTITY_ENDER_DRAGON_GROWL,0.2f,2f);
        Effects.playSoundDistance(player.getLocation(),8,Sound.ITEM_TOTEM_USE,0.3f,2f);

        int stepAmount = 4;
        double stepLength = eyes.distance(lightningTarget) / stepAmount;
        List<Location> locations = new ArrayList<>();
        locations.add(eyes);
        Random r = new Random();

        for(int i = 1; i < stepAmount; i++) {
            Vector vector = MagicWands.rotateVector(new Vector(i*stepLength, -1 + r.nextDouble() * 2, -1 + r.nextDouble() * 2), eyes.getYaw(), eyes.getPitch());
            locations.add(eyes.clone().add(vector));
        }
        locations.add(lightningTarget);

        for(int i = 0; i < locations.size()-1; i++) {
            Effects.drawParticleLine(locations.get(i),locations.get(i+1), particleEffect,0.2);
            particleEffect.spawn(locations.get(i));
        }
    }
}
