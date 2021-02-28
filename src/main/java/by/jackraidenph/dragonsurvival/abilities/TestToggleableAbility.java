package by.jackraidenph.dragonsurvival.abilities;

import by.jackraidenph.dragonsurvival.DragonSurvivalMod;
import by.jackraidenph.dragonsurvival.abilities.common.ToggleableDragonAbility;
import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

import java.util.Random;

public class TestToggleableAbility extends ToggleableDragonAbility {

    private Effect potionEffect = Effects.SPEED;

    public TestToggleableAbility(PlayerEntity playerEntity) {
        super(AbilityType.TEST_TOGGLEABLE_ABILITY_TYPE, playerEntity);
    }

    @Override
    public void tick() {
        World world = this.getPlayerDragon().world;
        double x = this.getPlayerDragon().getPosX();
        double y = this.getPlayerDragon().getPosY() + 0.5F;
        double z = this.getPlayerDragon().getPosZ();
        Random rand = this.getPlayerDragon().world.rand;
        float xV = ((rand.nextFloat() - 0.5F) * 2.0F);
        float yV = ((rand.nextFloat() - 0.5F) * 2.0F);
        float zV = ((rand.nextFloat() - 0.5F) * 2.0F);
        world.addParticle(new RedstoneParticleData(xV, yV, zV, 1.0F), x + xV, y + yV, z + zV, xV, yV, zV);
    }

    @Override
    public void startAbility() {
        this.getPlayerDragon().addPotionEffect(new EffectInstance(potionEffect, Integer.MAX_VALUE, 5, false, false, false));
        DragonSurvivalMod.getTickHandler().addToTickList(this);
    }

    @Override
    public void stopAbility() {
        this.getPlayerDragon().removePotionEffect(potionEffect);
        DragonSurvivalMod.getTickHandler().removeFromTickList(this);
    }
}
