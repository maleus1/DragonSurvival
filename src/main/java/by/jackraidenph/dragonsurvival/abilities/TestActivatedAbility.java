package by.jackraidenph.dragonsurvival.abilities;

import by.jackraidenph.dragonsurvival.abilities.common.ActivatedDragonAbility;
import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class TestActivatedAbility extends ActivatedDragonAbility {

    public TestActivatedAbility(PlayerEntity playerDragon) {
        super(AbilityType.TEST_ACTIVATED_ABILITY_TYPE, playerDragon);
    }

    @Override
    public void onActivation() {
        this.startCooldown();
        World world = this.getPlayerDragon().world;
        double x = this.getPlayerDragon().getPosX();
        double y = this.getPlayerDragon().getPosY() + 0.5F;
        double z = this.getPlayerDragon().getPosZ();
    }
}
