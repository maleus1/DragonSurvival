package by.jackraidenph.dragonsurvival.init;

import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityTree;
import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import by.jackraidenph.dragonsurvival.util.DragonType;

public class AbilityTreeInit {

    public static void init(){}

    public static final AbilityTree CAVE_DRAGON_TREE =
            new AbilityTree(DragonType.CAVE)
            .addLevel(
                    new AbilityTree.AbilityTreeLevel(0)
                    .addAbility(0, AbilityType.TEST_ACTIVATED_ABILITY_TYPE)
                    .addAbility(1, AbilityType.TEST_CHARGEABLE_ABILITY_TYPE)
                    .addAbility(2, AbilityType.TEST_TOGGLEABLE_ABILITY_TYPE)
            );

    public static final AbilityTree SEA_DRAGON_TREE =
            new AbilityTree(DragonType.SEA)
            .addLevel(
                    new AbilityTree.AbilityTreeLevel(0)
                    .addAbility(0, AbilityType.TEST_ACTIVATED_ABILITY_TYPE)
                    .addAbility(1, AbilityType.TEST_CHARGEABLE_ABILITY_TYPE)
                    .addAbility(2, AbilityType.TEST_TOGGLEABLE_ABILITY_TYPE)
            );

    public static final AbilityTree FOREST_DRAGON_TREE =
            new AbilityTree(DragonType.FOREST)
            .addLevel(
                    new AbilityTree.AbilityTreeLevel(0)
                    .addAbility(0, AbilityType.TEST_ACTIVATED_ABILITY_TYPE)
                    .addAbility(1, AbilityType.TEST_CHARGEABLE_ABILITY_TYPE)
                    .addAbility(2, AbilityType.TEST_TOGGLEABLE_ABILITY_TYPE)
            );
}
