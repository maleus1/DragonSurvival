package by.jackraidenph.dragonsurvival.abilities.common.utils;

import by.jackraidenph.dragonsurvival.util.DragonType;
import com.google.common.collect.Lists;

import java.util.LinkedList;

public class AbilityTree {

    private final DragonType dragonType;
    private LinkedList<AbilityTreeLevel> levels = Lists.newLinkedList();

    public AbilityTree(DragonType dragonType) {
        this.dragonType = dragonType;
    }

    public DragonType getDragonType() {
        return this.dragonType;
    }

    public AbilityTreeLevel getLevel(int index) {
        return this.levels.get(index);
    }

    public LinkedList<AbilityTreeLevel> getLevelsList() {
        return this.levels;
    }

    public AbilityTree addLevel(AbilityTreeLevel level) {
        this.levels.add(level);
        return this;
    }

    public AbilityTree removeLevel(int index) {
        this.levels.remove(index);
        return this;
    }

    public static class AbilityTreeLevel {

        private final int index;
        private LinkedList<AbilityType> abilityTypes = Lists.newLinkedList();

        public AbilityTreeLevel(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public AbilityType getAbilityType(int index) {
            return this.abilityTypes.get(index);
        }

        public LinkedList<AbilityType> getAbilityTypesList() {
            return this.abilityTypes;
        }

        public AbilityTreeLevel addAbility(int index, AbilityType type) {
            this.abilityTypes.add(index, type);
            return this;
        }

        public AbilityTreeLevel removeLevel(int index) {
            this.abilityTypes.remove(index);
            return this;
        }
    }
}
