package by.jackraidenph.dragonsurvival.abilities.common;

import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import net.minecraft.entity.player.PlayerEntity;

public abstract class ToggleableDragonAbility extends BasicDragonAbility {

    private boolean isActive = false;
    private PlayerEntity playerDragon;
    private AbilityType type;

    public ToggleableDragonAbility(AbilityType<? extends IDragonAbility> type, PlayerEntity playerDragon) {
        super(type, playerDragon);
    }

    public void startAbility() {

    }

    public void stopAbility() {

    }

    private void toggle() {
        this.isActive = !isActive;
    }

    @Override
    public void onKeyPressed() {

        if (!isActive) {
            startAbility();
        } else {
            stopAbility();
        }
        this.toggle();
    }
}
