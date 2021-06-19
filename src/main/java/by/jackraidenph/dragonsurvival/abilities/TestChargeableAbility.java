package by.jackraidenph.dragonsurvival.abilities;

import by.jackraidenph.dragonsurvival.abilities.common.ChargeableDragonAbility;
import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.Random;

public class TestChargeableAbility extends ChargeableDragonAbility {

    public TestChargeableAbility(PlayerEntity playerDragon) {
        super(AbilityType.TEST_CHARGEABLE_ABILITY_TYPE, playerDragon);
    }

    @Override
    public void doAction(int charge) {
        System.out.println(charge);

        World world = this.getPlayerDragon().world;
        double x = this.getPlayerDragon().getPosX();
        double y = this.getPlayerDragon().getPosY() + 0.5F;
        double z = this.getPlayerDragon().getPosZ();

        if (this.getChargeTimer() > 0) {
            for (int i = 0; i < this.getChargeTimer(); i++) {
                Random rand = this.getPlayerDragon().world.rand;
                float xV = ((rand.nextFloat() - 0.5F) * 2.0F);
                float yV = ((rand.nextFloat() - 0.5F) * 2.0F);
                float zV = ((rand.nextFloat() - 0.5F) * 2.0F);
                world.addParticle(new RedstoneParticleData(xV, yV, zV, 1.0F), x + xV, y + yV, z + zV, xV, yV, zV);
            }
        }

        if (this.getChargeTimer() == this.getMaxCharge()) {
            world.createExplosion(this.getPlayerDragon(), x, y, z, 12, Explosion.Mode.DESTROY);
            this.stopCharge();
        }

    }

    @Override
    public int getMaxCharge() {
        return 60;
    }

    @Override
    public void onKeyPressed() {
        this.doAction(this.getChargeTimer());
    }
}
