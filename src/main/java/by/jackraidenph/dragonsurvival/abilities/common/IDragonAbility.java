package by.jackraidenph.dragonsurvival.abilities.common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public interface IDragonAbility {

    String getId();

    int getLevel();

    default int getMaxLevel() {
        return 0;
    }

    ResourceLocation getIcon();

    int getManaCost();

    void onKeyPressed();

    void tick();

    void frame(float partialTicks);

    public PlayerEntity getPlayerDragon();

    public void setPlayerDragon(PlayerEntity playerDragon);
}
