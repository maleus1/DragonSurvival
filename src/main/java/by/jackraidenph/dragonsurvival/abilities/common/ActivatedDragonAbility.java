package by.jackraidenph.dragonsurvival.abilities.common;

import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import net.minecraft.entity.player.PlayerEntity;

public abstract class ActivatedDragonAbility extends BasicDragonAbility {

    public ActivatedDragonAbility(AbilityType<? extends IDragonAbility> type, PlayerEntity playerDragon) {
        super(type, playerDragon);
    }

    public void onActivation() {

    }

    @Override
    public void onKeyPressed() {
        this.onActivation();
    }
}
