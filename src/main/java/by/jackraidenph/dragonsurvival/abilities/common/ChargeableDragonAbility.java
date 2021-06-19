package by.jackraidenph.dragonsurvival.abilities.common;

import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import net.minecraft.entity.player.PlayerEntity;

public abstract class ChargeableDragonAbility extends BasicDragonAbility {

    private int chargeTimer;

    public ChargeableDragonAbility(AbilityType<? extends IDragonAbility> type, PlayerEntity playerDragon) {
        super(type, playerDragon);
    }

    public void charge() {
        if (this.chargeTimer <= this.getMaxCharge())
            this.chargeTimer++;
    }

    public void stopCharge() {
        this.chargeTimer = 0;
    }

    public int getChargeTimer() {
        return chargeTimer;
    }

    public void setChargeTimer(int chargeTimer) {
        this.chargeTimer = chargeTimer;
    }

    public int getMaxCharge() {
        return 0;
    }

    public void doAction(int charge) {

    }

    @Override
    public void onKeyPressed() {
        super.onKeyPressed();
        if (this.getCooldown() == 0 && this.canConsumeMana())
            this.doAction(this.chargeTimer);
    }
}
