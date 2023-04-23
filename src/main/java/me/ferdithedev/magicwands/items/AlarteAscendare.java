package me.ferdithedev.magicwands.items;

import me.ferdithedev.magicwands.MagicWands;
import me.ferdithedev.overblock.obitems.OBItem;
import me.ferdithedev.overblock.obitems.OBItemRarity;
import me.ferdithedev.overblock.obitems.OBItemType;
import me.ferdithedev.overblock.util.Effects;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collections;

public class AlarteAscendare extends OBItem {
    public AlarteAscendare(JavaPlugin plugin) {
        super(plugin, Material.STICK, "Alarte Ascendare", 10, OBItemType.TOOL, OBItemRarity.COMMON, "§7Shoots the target high into air.");
    }

    @Override
    public boolean function(Player player) {
        Player lookingAt = MagicWands.entityLookingAt(player);
        if(lookingAt != null) {
            final int[] time = {0};
            Effects.ParticleEffect particleEffect = new Effects.ParticleEffect(Collections.singletonList(new Effects.ParticleEffectPart(Particle.DUST_COLOR_TRANSITION, 10, 0.2, 0.2, 0.2, new Particle.DustTransition(Color.fromRGB(255, 253, 128), Color.fromRGB(255, 255, 255), 1f), 0)));
            BukkitRunnable particles = new BukkitRunnable() {
                @Override
                public void run() {
                    particleEffect.spawn(lookingAt.getLocation().add(0,0.1,0));
                    time[0]++;
                    if(time[0] == 15) {
                        cancel();
                    }
                }
            };
            particles.runTaskTimerAsynchronously(MagicWands.getInstance(),0,1);
            Effects.playSoundDistance(player.getLocation(),8, Sound.ENTITY_ELDER_GUARDIAN_CURSE,0.2f,2);
            Effects.playSoundDistance(player.getLocation(),8,Sound.BLOCK_BEACON_ACTIVATE,0.3f,2f);
            lookingAt.setVelocity(new Vector(0,1.75,0));
            return true;
        }
        return false;
    }
}
