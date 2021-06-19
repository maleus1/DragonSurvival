package by.jackraidenph.dragonsurvival.abilities.common.utils;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public class AbilityHolderContainer implements INBTSerializable {

    private List<AbilityHolder> abilityHolders = Lists.newArrayList();

    public AbilityHolderContainer() {

    }

    public static AbilityHolderContainer createFromNBT(INBT nbt) {
        AbilityHolderContainer abilityHolderContainer = new AbilityHolderContainer();
        abilityHolderContainer.deserializeNBT(nbt);
        return abilityHolderContainer;
    }

    public void unlockAbility(int level, int index, int stage) {
        AbilityHolder abilityHolder = new AbilityHolder(level, index, stage);
        if (!abilityHolders.contains(abilityHolder))
            abilityHolders.add(abilityHolder);
    }

    public void lockAbility(int level, int index, int stage) {
        AbilityHolder abilityHolder = new AbilityHolder(level, index, stage);
        abilityHolders.remove(abilityHolder);
    }

    public boolean isAbilityUnlocked(int level, int index) {
        return abilityHolders.stream().anyMatch(x -> ((x.getLevel() == level) && (x.getIndex() == index)));
    }

    public List<Integer> getSkillsForLevel(int level) {
        List<Integer> skills_indexes = Lists.newArrayList();
        for (AbilityHolder abilityHolder : abilityHolders)
            if (abilityHolder.getLevel() == level)
                skills_indexes.add(abilityHolders.get(level).getIndex());
        return skills_indexes;
    }

    public int getStageForSkill(int level, int index) {
        return abilityHolders.stream().filter(holder -> ((holder.getLevel() == level) && (holder.getIndex() == index))).findFirst().map(AbilityHolder::getStage).orElse(0);
    }

    @Override
    public INBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        int size = abilityHolders.size();
        nbt.putInt("holder_list_size", size);
        for (int i = 0; i < size; i++) {
            AbilityHolder abilityHolder = abilityHolders.get(i);
            nbt.put("abilityholder_" + i, abilityHolder.serializeNBT());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        int size = compoundNBT.getInt("holder_list_size");
        for (int i = 0; i < size; i++) {
            AbilityHolder abilityHolder = AbilityHolder.createFromNBT(((CompoundNBT) nbt).get("abilityholder_" + i));
            if (!abilityHolders.contains(abilityHolder))
                abilityHolders.add(abilityHolder);
        }
    }
}
