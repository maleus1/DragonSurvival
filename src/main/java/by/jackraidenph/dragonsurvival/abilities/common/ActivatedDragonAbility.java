package by.jackraidenph.dragonsurvival.abilities.common;

import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class ActivatedDragonAbility extends BasicDragonAbility {

    private PlayerEntity playerDragon;
    private AbilityType type;

    public ActivatedDragonAbility(AbilityType<? extends IDragonAbility> type, PlayerEntity playerDragon) {
        super(type, playerDragon);
    }

    @Override
    public String getId() {
        return type.getId();
    }

    public void onActivation() {

    }

    @Override
    public void onKeyPressed() {
        this.onActivation();
    }
}
