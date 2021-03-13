package by.jackraidenph.dragonsurvival.abilities;

import by.jackraidenph.dragonsurvival.abilities.common.ActivatedDragonAbility;
import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import net.minecraft.entity.item.FireworkRocketEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class TestActivatedAbility extends ActivatedDragonAbility {

    public TestActivatedAbility(PlayerEntity playerDragon) {
        super(AbilityType.TEST_ACTIVATED_ABILITY_TYPE, playerDragon);
    }

    @Override
    public int getMaxCooldown() {
        return 100;
    }

    @Override
    public void tick() {

    }

    @Override
    public int getManaCost() {
        return 25;
    }

    @Override
    public void onActivation() {
        this.startCooldown();
        this.consumeMana();
        World world = this.getPlayerDragon().world;
        double x = this.getPlayerDragon().getPosX();
        double y = this.getPlayerDragon().getPosY() + 0.5F;
        double z = this.getPlayerDragon().getPosZ();

        world.addEntity(new FireworkRocketEntity(world, x, y, z, new ItemStack(Items.ACACIA_LOG)));
    }
}
