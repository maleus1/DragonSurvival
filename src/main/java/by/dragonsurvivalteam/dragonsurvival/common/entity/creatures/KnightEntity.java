package by.dragonsurvivalteam.dragonsurvival.common.entity.creatures;

import by.dragonsurvivalteam.dragonsurvival.client.render.util.AnimationTimer;
import by.dragonsurvivalteam.dragonsurvival.client.render.util.CommonTraits;
import by.dragonsurvivalteam.dragonsurvival.common.DragonEffects;
import by.dragonsurvivalteam.dragonsurvival.config.ConfigHandler;
import by.dragonsurvivalteam.dragonsurvival.util.Functions;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.Hand;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;

public class KnightEntity extends CreatureEntity implements IAnimatable, DragonHunter, CommonTraits{
	AnimationFactory animationFactory = new AnimationFactory(this);
	AnimationTimer animationTimer = new AnimationTimer();

	public KnightEntity(EntityType<? extends CreatureEntity> p_i48576_1_, World world){
		super(p_i48576_1_, world);
	}

	@Override
	public void registerControllers(AnimationData data){
		data.addAnimationController(new AnimationController<>(this, "everything", 3, event -> {
			AnimationBuilder animationBuilder = new AnimationBuilder();

			AnimationController animationController = event.getController();
			double movement = getMovementSpeed(this);
			if(swingTime > 0){
				Animation animation = animationController.getCurrentAnimation();
				if(animation != null){
					String name = animation.animationName;
					switch(name){
						case "attack":
							if(animationTimer.getDuration("attack2") <= 0){
								if(random.nextBoolean()){
									animationTimer.putAnimation("attack", 17d, animationBuilder);
								}else{
									animationTimer.putAnimation("attack2", 17d, animationBuilder);
								}
							}
							break;
						case "attack2":
							if(animationTimer.getDuration("attack") <= 0){
								if(random.nextBoolean()){
									animationTimer.putAnimation("attack", 17d, animationBuilder);
								}else{
									animationTimer.putAnimation("attack2", 17d, animationBuilder);
								}
							}
							break;
						default:
							if(random.nextBoolean()){
								animationTimer.putAnimation("attack", 17d, animationBuilder);
							}else{
								animationTimer.putAnimation("attack2", 17d, animationBuilder);
							}
					}
				}
			}
			if(movement > 0.4){
				animationBuilder.addAnimation("run");
			}else if(movement > 0.1){
				animationBuilder.addAnimation("walk");
			}else{
				Animation animation = animationController.getCurrentAnimation();
				if(animation == null){
					animationTimer.putAnimation("idle", 88d, animationBuilder);
				}else{
					String name = animation.animationName;
					switch(name){
						case "idle":
							if(animationTimer.getDuration("idle") <= 0){
								if(random.nextInt(2000) == 0){
									animationTimer.putAnimation("idle_2", 145d, animationBuilder);
								}
							}
							break;
						case "walk":
						case "run":
							animationTimer.putAnimation("idle", 88d, animationBuilder);
							break;
						case "idle_2":
							if(animationTimer.getDuration("idle_2") <= 0){
								animationTimer.putAnimation("idle", 88d, animationBuilder);
							}
							break;
					}
				}
			}
			animationController.setAnimation(animationBuilder);
			return PlayState.CONTINUE;
		}));
	}

	@Override
	public AnimationFactory getFactory(){
		return animationFactory;
	}

	@Override
	protected void registerGoals(){
		super.registerGoals();
		goalSelector.addGoal(0, new SwimGoal(this));
		goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1));
		goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5, true));
		targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, 1, true, false, livingEntity -> {
			return livingEntity.hasEffect(Effects.BAD_OMEN) || livingEntity.hasEffect(DragonEffects.EVIL_DRAGON);
		}));
		targetSelector.addGoal(6, new HurtByTargetGoal(this, ShooterEntity.class).setAlertOthers());
	}

	protected int getExperienceReward(PlayerEntity p_70693_1_){
		return 5 + this.level.random.nextInt(5);
	}

	@Override
	public void tick(){
		updateSwingTime();
		super.tick();
	}	@Override
	protected void populateDefaultEquipmentSlots(DifficultyInstance difficultyInstance){
		setItemInHand(Hand.MAIN_HAND, new ItemStack(Items.IRON_SWORD));
		if(random.nextDouble() < ConfigHandler.COMMON.knightShieldChance.get()){
			ItemStack itemStack = new ItemStack(Items.SHIELD);
			ListNBT listNBT = Functions.createRandomPattern(new BannerPattern.Builder(), 16);
			CompoundNBT compoundNBT = new CompoundNBT();
			compoundNBT.putInt("Base", DyeColor.values()[this.random.nextInt((DyeColor.values()).length)].getId());
			compoundNBT.put("Patterns", listNBT);
			itemStack.addTagElement("BlockEntityTag", compoundNBT);
			setItemInHand(Hand.OFF_HAND, itemStack);
		}
	}

	@Override
	public boolean removeWhenFarAway(double distance){
		return !this.hasCustomName() && tickCount >= Functions.minutesToTicks(ConfigHandler.COMMON.hunterDespawnDelay.get());
	}	@Nullable
	@Override
	public ILivingEntityData finalizeSpawn(IServerWorld serverWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason,
		@Nullable
			ILivingEntityData entityData,
		@Nullable
			CompoundNBT nbt){
		populateDefaultEquipmentSlots(difficultyInstance);
		return super.finalizeSpawn(serverWorld, difficultyInstance, spawnReason, entityData, nbt);
	}

	@Override
	public boolean isBlocking(){
		if(getOffhandItem().isShield(this)){
			return random.nextBoolean();
		}
		return false;
	}




}