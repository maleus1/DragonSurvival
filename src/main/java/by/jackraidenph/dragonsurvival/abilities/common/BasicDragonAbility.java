package by.jackraidenph.dragonsurvival.abilities.common;

import by.jackraidenph.dragonsurvival.DragonSurvivalMod;
import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public abstract class BasicDragonAbility implements IDragonAbility {

    private PlayerEntity playerDragon;
    private AbilityType type;
    private int cooldownTimer;

    public BasicDragonAbility(AbilityType<? extends IDragonAbility> type, PlayerEntity playerDragon) {
        this.playerDragon = playerDragon;
        this.type = type;
    }

    @Override
    public PlayerEntity getPlayerDragon() {
        return this.playerDragon;
    }

    @Override
    public void setPlayerDragon(PlayerEntity playerDragon) {
        this.playerDragon = playerDragon;
    }

    @Override
    public void tick() {

    }

    @Override
    public void onKeyPressed() {

    }

    public int getMaxCooldown() {
        return 0;
    }

    public void decreaseCooldownTimer() {
        if (this.cooldownTimer > 0)
            this.cooldownTimer--;
    }

    public int getCooldown() {
        return this.cooldownTimer;
    }

    public void setCooldown(int newCooldown) {
        this.cooldownTimer = newCooldown;
    }

    public void startCooldown() {
        this.cooldownTimer = this.getMaxCooldown();
    }

    @Override
    public String getId() {
        return this.type.getId();
    }

    @Override
    public ResourceLocation getIcon() {
        return new ResourceLocation(DragonSurvivalMod.MOD_ID, "abilities/" + this.getId() + ".png");
    }
}
