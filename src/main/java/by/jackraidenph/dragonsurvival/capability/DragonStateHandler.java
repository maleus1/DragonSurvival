package by.jackraidenph.dragonsurvival.capability;

import by.jackraidenph.dragonsurvival.abilities.common.IDragonAbility;
import by.jackraidenph.dragonsurvival.abilities.common.utils.BlankDragonAbility;
import by.jackraidenph.dragonsurvival.util.DragonLevel;
import by.jackraidenph.dragonsurvival.util.DragonType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.Vec3d;


public class DragonStateHandler {
    private boolean isDragon;
    private boolean isHiding;
    private DragonType type = DragonType.NONE;
    private DragonLevel level = DragonLevel.BABY;
    private NonNullList<IDragonAbility> abilitySlots = NonNullList.withSize(5, new BlankDragonAbility());
    private int maxActiveAbilitySlots = 5;
    private int selectedAbilitySlot = 0;
    /**
     * Current health, must be equal to the player's health
     */
    private float health = level.initialHealth;
    private final DragonMovementData data = new DragonMovementData(0, 0, 0, Vec3d.ZERO, Vec3d.ZERO);
    private boolean hasWings;

    public boolean hasWings() {
        return hasWings;
    }

    public void setHasWings(boolean hasWings) {
        this.hasWings = hasWings;
    }

    public boolean isDragon() {
        return this.isDragon;
    }

    public void setIsDragon(boolean isDragon) {
        this.isDragon = isDragon;
    }

    public boolean isHiding() {
        return isHiding;
    }

    public void setIsHiding(boolean hiding) {
        isHiding = hiding;
    }

    public DragonLevel getLevel() {
        return this.level;
    }

    public void setLevel(DragonLevel level) {
        this.level = level;
    }

    public int getMaxActiveAbilitySlots() {
        return maxActiveAbilitySlots;
    }

    public void populateAbilities(PlayerEntity playerEntity) {
        for (IDragonAbility ability : this.abilitySlots)
            ability.setPlayerDragon(playerEntity);
    }

    public void setAbilityInSlot(IDragonAbility ability, int slot) {
        this.abilitySlots.set(slot, ability);
    }

    public IDragonAbility getAbilityFromSlot(int slot) {
        if (slot > this.maxActiveAbilitySlots)
            throw new IllegalArgumentException("Failed to set ability in the slot. The slot number is higher than the max slot count.");

        return this.abilitySlots.get(slot);
    }

    public void setAbilitySlotList(NonNullList<IDragonAbility> listToSet) {
        for (int i = 0; i < listToSet.size(); i++)
            this.setAbilityInSlot(listToSet.get(i), i);
    }

    public NonNullList<IDragonAbility> getAbilitySlots() {
        return this.abilitySlots;
    }

    public int getSelectedAbilitySlot() {
        return this.selectedAbilitySlot;
    }

    public void setSelectedAbilitySlot(int newSlot) {
        this.selectedAbilitySlot = newSlot;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    /**
     * Sets the level and initial health
     */
    public void setLevel(DragonLevel level, PlayerEntity playerEntity) {
        setLevel(level);
        playerEntity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(level.initialHealth);
        playerEntity.heal(playerEntity.getMaxHealth());
        setHealth(level.initialHealth);
    }

    public void setLevelAndHealth(DragonLevel level, float health, PlayerEntity playerEntity) {
        setLevel(level);
        playerEntity.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(health);
        playerEntity.heal(playerEntity.getMaxHealth());
        setHealth(health);
    }


    public void setMovementData(double bodyYaw, double headYaw, double headPitch, Vec3d headPos, Vec3d tailPos) {
        data.bodyYaw = bodyYaw;
        data.headYaw = headYaw;
        data.headPitch = headPitch;
        data.headPos = headPos;
        data.tailPos = tailPos;
    }

    public DragonMovementData getMovementData() {
        return this.data;
    }

    public DragonType getType() {
        return this.type;
    }

    public void setType(DragonType type) {
        this.type = type;
    }

    public static class DragonMovementData {
        public double bodyYaw;
        public double headYaw;
        public double headPitch;
        public Vec3d headPos;
        public Vec3d tailPos;

        public Vec3d headPosLastTick;
        public Vec3d tailPosLastTick;
        public double headYawLastTick;
        public double headPitchLastTick;
        public double bodyYawLastTick;

        public DragonMovementData(
                double bodyYaw,
                double headYaw,
                double headPitch,
                Vec3d headPos,
                Vec3d tailPos) {

            this.bodyYaw = bodyYaw;
            this.headYaw = headYaw;
            this.headPitch = headPitch;
            this.headPos = headPos;
            this.tailPos = tailPos;

            this.headPosLastTick = headPos;
            this.tailPosLastTick = tailPos;
            this.headYawLastTick = headYaw;
            this.headPitchLastTick = headPitch;
            this.bodyYawLastTick = bodyYaw;
        }

        void setMovementData(double bodyYaw, double headYaw, double headPitch, Vec3d headPos, Vec3d tailPos) {
            this.setMovementLastTick(this.bodyYaw, this.headYaw, this.headPitch, this.headPos, this.tailPos);
            this.bodyYaw = bodyYaw;
            this.headYaw = headYaw;
            this.headPitch = headPitch;
            this.headPos = headPos;
            this.tailPos = tailPos;
        }

        void setMovementLastTick(double bodyYawLastTick, double headYawLastTick, double headPitchLastTick, Vec3d headPosLastTick, Vec3d tailPosLastTick) {
            this.bodyYawLastTick = bodyYawLastTick;
            this.headYawLastTick = headYawLastTick;
            this.headPitchLastTick = headPitchLastTick;
            this.headPosLastTick = headPosLastTick;
            this.tailPosLastTick = tailPosLastTick;
        }
    }
}
