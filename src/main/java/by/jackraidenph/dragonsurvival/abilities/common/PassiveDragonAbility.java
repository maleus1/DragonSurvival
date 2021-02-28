package by.jackraidenph.dragonsurvival.abilities.common;

import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import net.minecraft.entity.player.PlayerEntity;

public abstract class PassiveDragonAbility extends BasicDragonAbility {

    public PassiveDragonAbility(AbilityType<? extends IDragonAbility> type, PlayerEntity playerDragon) {
        super(type, playerDragon);
    }

    public void passiveEffect() {

    }
}
