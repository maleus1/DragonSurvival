package by.jackraidenph.dragonsurvival.abilities.common;

import by.jackraidenph.dragonsurvival.DragonSurvivalMod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public interface IDragonAbility {

    String getId();

    ResourceLocation getIcon();

    int getManaCost();

    void onKeyPressed();

    void tick();

    public PlayerEntity getPlayerDragon();

    public void setPlayerDragon(PlayerEntity playerDragon);
}
