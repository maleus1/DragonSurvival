package by.jackraidenph.dragonsurvival.abilities.common.utils;

import by.jackraidenph.dragonsurvival.abilities.common.IDragonAbility;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.UUID;

public class BlankDragonAbility implements IDragonAbility {

    public BlankDragonAbility() {
    }

    public BlankDragonAbility(PlayerEntity playerEntity) {
    }

    @Override
    public String getId() {
        return "blank";
    }

    @Override
    public void onKeyPressed() {

    }

    @Override
    public void tick() {

    }

    @Override
    public PlayerEntity getPlayerDragon() {
        GameProfile MINECRAFT = new GameProfile(UUID.fromString("41C82C87-7AfB-4024-BA57-13D2C99CAE77"), "[Minecraft]");
        return new FakePlayer(ServerLifecycleHooks.getCurrentServer().getWorld(DimensionType.OVERWORLD), MINECRAFT);
    }

    @Override
    public void setPlayerDragon(PlayerEntity playerDragon) {

    }
}
