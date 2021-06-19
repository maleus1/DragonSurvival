package by.jackraidenph.dragonsurvival.abilities.common.utils;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraftforge.common.util.INBTSerializable;

public class AbilityHolder implements INBTSerializable {
    private int level;
    private int index;
    private int stage;

    public AbilityHolder() {

    }

    public AbilityHolder(int level, int index, int stage) {
        this.level = level;
        this.index = index;
        this.stage = stage;
    }

    public static AbilityHolder createFromNBT(INBT nbt) {
        AbilityHolder abilityHolder = new AbilityHolder();
        abilityHolder.deserializeNBT(nbt);
        return abilityHolder;
    }

    public int getLevel() {
        return level;
    }

    public int getIndex() {
        return index;
    }

    public int getStage() {
        return stage;
    }

    @Override
    public INBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("level", this.getLevel());
        nbt.putInt("index", this.getIndex());
        nbt.putInt("stage", this.getStage());
        return nbt;
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        this.level = compoundNBT.getInt("level");
        this.index = compoundNBT.getInt("index");
        this.stage = compoundNBT.getInt("stage");
    }
}
