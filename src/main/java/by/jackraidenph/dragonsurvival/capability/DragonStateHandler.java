package by.jackraidenph.dragonsurvival.capability;

import by.jackraidenph.dragonsurvival.abilities.common.IDragonAbility;
import by.jackraidenph.dragonsurvival.abilities.common.utils.BlankDragonAbility;
import by.jackraidenph.dragonsurvival.util.DragonLevel;
import by.jackraidenph.dragonsurvival.util.DragonType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Synchronize on login
 * {@link by.jackraidenph.dragonsurvival.handlers.SynchronizationController#onLoggedIn(PlayerEvent.PlayerLoggedInEvent)}
 * Synchronize on respawn
 * {@link by.jackraidenph.dragonsurvival.handlers.SynchronizationController#onPlayerRespawn(PlayerEvent.PlayerRespawnEvent)}
 * Synchronize movement on tick
 * {@link by.jackraidenph.dragonsurvival.handlers.SynchronizationController#onTick(TickEvent.PlayerTickEvent)}
 * Synchronize on tracking
 * {@link by.jackraidenph.dragonsurvival.handlers.SynchronizationController#onTrackingStart(PlayerEvent.StartTracking)}
 * Synchronize movement data
 * {@link by.jackraidenph.dragonsurvival.network.SynchronizeDragonCap}
 * Synchronize abilities data
 * {@link by.jackraidenph.dragonsurvival.network.SynchronizeDragonAbilities}
 * Store capability data in NBT
 * {@link by.jackraidenph.dragonsurvival.capability.CapabilityStorage}
 */
public class DragonStateHandler {
    private boolean isDragon;
    private boolean isHiding;
    private DragonType type = DragonType.NONE;
    private DragonLevel level = DragonLevel.BABY;
    private NonNullList<IDragonAbility> abilitySlots = NonNullList.withSize(5, new BlankDragonAbility());
    private int selectedAbilitySlot = 0;
    private int maxMana = 100;
    private int currentMana = 0;
    /**
     * Current health, must be equal to the player's health
     */
    private float health = level.initialHealth;
    private final DragonMovementData data = new DragonMovementData(0, 0, 0);
    private boolean hasWings;

    public boolean hasWings() {
        return hasWings;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public void replenishMana(int mana){
        this.setCurrentMana(this.getCurrentMana() + mana);
    }

    public void consumeMana(int mana){
        this.setCurrentMana(this.getCurrentMana() - mana);
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = MathHelper.clamp(currentMana, 0, this.getMaxMana());
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

    public void populateAbilities(PlayerEntity playerEntity) {
        for (IDragonAbility ability : this.abilitySlots)
            ability.setPlayerDragon(playerEntity);
    }

    public void setAbilityInSlot(IDragonAbility ability, int slot) {
        this.abilitySlots.set(slot, ability);
    }

    public IDragonAbility getAbilityFromSlot(int slot) {
        if ((slot > 5) || (slot < 0))
            throw new IllegalArgumentException("Failed to set ability in the slot. The slot number is inappropriate.");

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


    public void setMovementData(double bodyYaw, double headYaw, double headPitch) {
        data.bodyYaw = bodyYaw;
        data.headYaw = headYaw;
        data.headPitch = headPitch;
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

        public Vec3d headPosLastTick;
        public Vec3d tailPosLastTick;
        public double headYawLastTick;
        public double headPitchLastTick;
        public double bodyYawLastTick;

        public DragonMovementData(
                double bodyYaw,
                double headYaw,
                double headPitch) {

            this.bodyYaw = bodyYaw;
            this.headYaw = headYaw;
            this.headPitch = headPitch;

            this.headYawLastTick = headYaw;
            this.headPitchLastTick = headPitch;
            this.bodyYawLastTick = bodyYaw;
        }

        void setMovementData(double bodyYaw, double headYaw, double headPitch) {
            this.setMovementLastTick(this.bodyYaw, this.headYaw, this.headPitch);
            this.bodyYaw = bodyYaw;
            this.headYaw = headYaw;
            this.headPitch = headPitch;
        }

        void setMovementLastTick(double bodyYawLastTick, double headYawLastTick, double headPitchLastTick) {
            this.bodyYawLastTick = bodyYawLastTick;
            this.headYawLastTick = headYawLastTick;
            this.headPitchLastTick = headPitchLastTick;
        }
    }
}
