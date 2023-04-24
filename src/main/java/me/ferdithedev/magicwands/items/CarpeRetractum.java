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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class CarpeRetractum extends OBItem {


    public CarpeRetractum(JavaPlugin plugin) {
        super(plugin, Material.LEAD, "Carpe Retractum", 10, OBItemType.TOOL, OBItemRarity.UNIQUE, "§7Use it as a grappling hook to swing under the ceiling");
    }

    @Override
    public boolean function(Player player) {
        Block block = MagicWands.blockLookingAt(player);
        if(block != null) {
            Effects.ParticleEffectPart part1 = new Effects.ParticleEffectPart(Particle.WAX_ON,2,.1,.1,.1,null,0);
            Effects.ParticleEffectPart part2 = new Effects.ParticleEffectPart(Particle.REDSTONE,2,.1,.1,.1,new Particle.DustOptions(Color.fromRGB(255, 132, 0),1.2f),0);
            Effects.ParticleEffect particleEffect = new Effects.ParticleEffect(Arrays.asList(part1,part2));
            Effects.drawParticleLine(player.getEyeLocation(),block.getLocation(),particleEffect,.2);
            Effects.playSoundDistance(player.getLocation(),5, Sound.ENTITY_LEASH_KNOT_PLACE,1,0);
            player.setVelocity(Effects.getDirectionBetweenLocations(player.getEyeLocation(),block.getLocation()).multiply(0.2));
            return true;
        }
        return false;
    }
}
