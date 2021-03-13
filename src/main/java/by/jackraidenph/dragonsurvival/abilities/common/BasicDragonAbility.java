package by.jackraidenph.dragonsurvival.abilities.common;

import by.jackraidenph.dragonsurvival.DragonSurvivalMod;
import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import by.jackraidenph.dragonsurvival.capability.DragonStateProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.text.NumberFormat;

public abstract class BasicDragonAbility implements IDragonAbility {

    private PlayerEntity playerDragon;
    private AbilityType type;
    private int cooldownTimer;
    private ResourceLocation iconTexture;
    private NumberFormat nf = NumberFormat.getInstance();

    public BasicDragonAbility(AbilityType<? extends IDragonAbility> type, PlayerEntity playerDragon) {
        this.playerDragon = playerDragon;
        this.type = type;
        this.iconTexture = new ResourceLocation(DragonSurvivalMod.MODID, "textures/ability/" + this.getId() + ".png");
        this.nf.setMaximumFractionDigits(1);
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
    public int getManaCost() {
        return 0;
    }

    @Override
    public void onKeyPressed() {
        if (!this.canConsumeMana())
            this.getPlayerDragon().sendStatusMessage(
                    new TranslationTextComponent("ds.skill_mana_check_failure").applyTextStyle(TextFormatting.DARK_AQUA),
                    true);
        if (this.getCooldown() != 0) {
            this.getPlayerDragon().sendStatusMessage(
                    new TranslationTextComponent("ds.skill_cooldown_check_failure").appendText(" " + nf.format(this.getCooldown() / 20.0F) + "s").applyTextStyle(TextFormatting.RED),
                    true);
        }
    }

    public boolean canConsumeMana() {
        return DragonStateProvider.getCurrentMana(this.getPlayerDragon()) >= this.getManaCost();
    }

    public void consumeMana() {
        DragonStateProvider.consumeMana(this.getPlayerDragon(), this.getManaCost());
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
        DragonSurvivalMod.getTickHandler().addToCoolDownList(this);
    }

    @Override
    public String getId() {
        return this.type.getId();
    }

    @Override
    public ResourceLocation getIcon() {
        return this.iconTexture;
    }
}
