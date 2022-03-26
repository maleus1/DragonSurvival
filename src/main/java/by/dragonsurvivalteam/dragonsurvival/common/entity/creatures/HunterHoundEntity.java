package by.dragonsurvivalteam.dragonsurvival.common.entity.creatures;

import by.dragonsurvivalteam.dragonsurvival.common.DragonEffects;
import by.dragonsurvivalteam.dragonsurvival.common.EffectInstance2;
import by.dragonsurvivalteam.dragonsurvival.common.entity.goals.AlertExceptHunters;
import by.dragonsurvivalteam.dragonsurvival.common.entity.goals.FollowMobGoal;
import by.dragonsurvivalteam.dragonsurvival.config.ConfigHandler;
import by.dragonsurvivalteam.dragonsurvival.util.Functions;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class HunterHoundEntity extends WolfEntity implements DragonHunter{
	public static final DataParameter<Integer> variety = EntityDataManager.defineId(HunterHoundEntity.class, DataSerializers.INT);

	public HunterHoundEntity(EntityType<? extends WolfEntity> type, World world){
		super(type, world);
	}

	protected void registerGoals(){
		super.registerGoals();
		this.goalSelector.availableGoals.removeIf(prioritizedGoal -> {
			Goal goal = prioritizedGoal.getGoal();
			return (goal instanceof net.minecraft.entity.ai.goal.SitGoal || goal instanceof net.minecraft.entity.ai.goal.FollowOwnerGoal || goal instanceof net.minecraft.entity.ai.goal.BreedGoal || goal instanceof net.minecraft.entity.ai.goal.BegGoal);
		});
		this.targetSelector.availableGoals.removeIf(prioritizedGoal -> {
			Goal goal = prioritizedGoal.getGoal();
			return (goal instanceof NearestAttackableTargetGoal || goal instanceof net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal || goal instanceof HurtByTargetGoal);
		});
		this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 0, true, false, livingEntity -> (livingEntity.hasEffect(Effects.BAD_OMEN) || livingEntity.hasEffect(DragonEffects.EVIL_DRAGON))));
		this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, MonsterEntity.class, 0, true, false, livingEntity -> (livingEntity instanceof net.minecraft.entity.monster.IMob && !(livingEntity instanceof DragonHunter))));
		targetSelector.addGoal(4, new HurtByTargetGoal(this, ShooterEntity.class).setAlertOthers());
		this.goalSelector.addGoal(7, new FollowMobGoal<>(KnightEntity.class, this, 15));
		this.goalSelector.addGoal(8, new AlertExceptHunters(this, KnightEntity.class, ShooterEntity.class, SquireEntity.class));
	}

	protected void defineSynchedData(){
		super.defineSynchedData();
		this.entityData.define(variety, 0);
	}

	public void addAdditionalSaveData(CompoundNBT compoundNBT){
		super.addAdditionalSaveData(compoundNBT);
		compoundNBT.putInt("Variety", this.entityData.get(variety));
	}

	public void readAdditionalSaveData(CompoundNBT compoundNBT){
		super.readAdditionalSaveData(compoundNBT);
		this.entityData.set(variety, compoundNBT.getInt("Variety"));
	}

	public boolean doHurtTarget(Entity entity){
		if(ConfigHandler.COMMON.houndDoesSlowdown.get() && entity instanceof LivingEntity){
			if(((LivingEntity)entity).hasEffect(Effects.MOVEMENT_SLOWDOWN)){
				((LivingEntity)entity).addEffect(new EffectInstance2(Effects.MOVEMENT_SLOWDOWN, 200, 1));
			}else{
				((LivingEntity)entity).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200));
			}
		}
		return super.doHurtTarget(entity);
	}

	public ILivingEntityData finalizeSpawn(IServerWorld serverWorld, DifficultyInstance difficultyInstance, SpawnReason reason,
		@Nullable
			ILivingEntityData livingEntityData,
		@Nullable
			CompoundNBT compoundNBT){
		this.entityData.set(variety, this.random.nextInt(8));
		return super.finalizeSpawn(serverWorld, difficultyInstance, reason, livingEntityData, compoundNBT);
	}

	@Override
	public boolean removeWhenFarAway(double distance){
		return !this.hasCustomName() && tickCount >= Functions.minutesToTicks(ConfigHandler.COMMON.hunterDespawnDelay.get());
	}

	protected int getExperienceReward(PlayerEntity p_70693_1_){
		return 1 + this.level.random.nextInt(2);
	}
}