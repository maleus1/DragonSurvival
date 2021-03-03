package by.jackraidenph.dragonsurvival.abilities.common.utils;

import by.jackraidenph.dragonsurvival.util.DragonType;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.HashMap;

public class AbilityTree {

    private final DragonType dragonType;
    private ArrayList<AbilityTreeLevel> levels = Lists.newArrayList();

    public AbilityTree(DragonType dragonType) {
        this.dragonType = dragonType;
    }

    public DragonType getDragonType() {
        return this.dragonType;
    }

    public AbilityTreeLevel getLevel(int level) {
        return this.levels.get(level);
    }

    public ArrayList<AbilityTreeLevel> getLevelsList(int level) {
        return this.levels;
    }

    public AbilityTree addLevel(AbilityTreeLevel level) {
        this.levels.add(level);
        return this;
    }

    public AbilityTree removeLevel(AbilityTreeLevel level) {
        this.levels.remove(level);
        return this;
    }

    public static class AbilityTreeLevel {

        private final int index;
        private HashMap<Integer, AbilityType> abilityTypes = Maps.newHashMap();

        public AbilityTreeLevel(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public AbilityType getAbilityType(int index) {
            return this.abilityTypes.get(index);
        }

        public HashMap<Integer, AbilityType> getAbilityTypesList() {
            return this.abilityTypes;
        }

        public AbilityTreeLevel addAbility(Integer index, AbilityType type) {
            this.abilityTypes.put(index, type);
            return this;
        }

        public AbilityTreeLevel removeLevel(Integer index) {
            this.abilityTypes.remove(index);
            return this;
        }
    }
}
