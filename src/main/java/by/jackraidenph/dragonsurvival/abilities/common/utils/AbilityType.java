package by.jackraidenph.dragonsurvival.abilities.common.utils;

import by.jackraidenph.dragonsurvival.DragonSurvivalMod;
import by.jackraidenph.dragonsurvival.abilities.TestActivatedAbility;
import by.jackraidenph.dragonsurvival.abilities.TestChargeableAbility;
import by.jackraidenph.dragonsurvival.abilities.TestToggleableAbility;
import by.jackraidenph.dragonsurvival.abilities.common.IDragonAbility;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.NonNullList;

public class AbilityType<T extends IDragonAbility> {

    public static void init(){}

    public static final AbilityType<TestToggleableAbility> TEST_TOGGLEABLE_ABILITY_TYPE = register(AbilityType.Builder.create(TestToggleableAbility::new, "test_toggleable"));
    public static final AbilityType<TestChargeableAbility> TEST_CHARGEABLE_ABILITY_TYPE = register(AbilityType.Builder.create(TestChargeableAbility::new, "test_chargeable"));
    public static final AbilityType<TestActivatedAbility> TEST_ACTIVATED_ABILITY_TYPE = register(AbilityType.Builder.create(TestActivatedAbility::new, "test_activated"));
    public static final AbilityType<BlankDragonAbility> BLANK_ABILITY = register(AbilityType.Builder.create(BlankDragonAbility::new, "blank"));

    private final IFactory<? extends T> factory;
    private final String id;

    public AbilityType(IFactory<? extends T> factory, String id) {
        this.factory = factory;
        this.id = id;
    }

    private static <T extends IDragonAbility> AbilityType<T> register(AbilityType.Builder<T> builder) {
        DragonSurvivalMod.ABILITIES_MAP.put(builder.id, builder.build());
        return builder.build();
    }

    public static NonNullList<IDragonAbility> toAbilityList(PlayerEntity playerEntity, NonNullList<AbilityType> typesList) {
        NonNullList<IDragonAbility> abilities = NonNullList.create();
        for (int i = 0; i < typesList.size(); i++) {
            abilities.add(i, typesList.get(i).factory.create(playerEntity));
        }
        return abilities;
    }

    public static NonNullList<AbilityType> toTypesList(NonNullList<IDragonAbility> abilityList) {
        NonNullList<AbilityType> abilities = NonNullList.create();
        for (int i = 0; i < abilityList.size(); i++) {
            abilities.add(i, DragonSurvivalMod.ABILITIES_MAP.get(abilityList.get(i).getId()));
        }
        return abilities;
    }

    public T create(PlayerEntity playerEntity) {
        return this.factory.create(playerEntity);
    }

    public String getId() {
        return this.id;
    }

    public interface IFactory<T extends IDragonAbility> {
        T create(PlayerEntity playerEntity);
    }

    public static final class Builder<T extends IDragonAbility> {
        private final IFactory<? extends T> factory;
        private final String id;

        private Builder(IFactory<? extends T> factoryIn, String id) {
            this.factory = factoryIn;
            this.id = id;
        }

        public static <T extends IDragonAbility> AbilityType.Builder<T> create(IFactory<? extends T> factoryIn, String id) {
            return new AbilityType.Builder<>(factoryIn, id);
        }

        public AbilityType<T> build() {
            return new AbilityType<>(this.factory, this.id);
        }
    }
}
