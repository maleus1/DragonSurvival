package by.jackraidenph.dragonsurvival.tiles;

import by.jackraidenph.dragonsurvival.init.TileEntityTypesInit;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class PredatorStarTileEntity extends BaseBlockEntity implements ITickableTileEntity {
    private int ticksExisted;
    private float activeRotation;

    public PredatorStarTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public PredatorStarTileEntity() {
        super(TileEntityTypesInit.PREDATOR_STAR_TILE_ENTITY_TYPE);

        ticksExisted = 0;
        activeRotation = 0;
    }

    public int getTicksExisted() {
        return ticksExisted;
    }

    public float getActiveRotation(float partialTicks) {
        return (this.activeRotation + partialTicks) * -0.0375F;
    }

    @Override
    public void tick() {
        ++this.ticksExisted;

        // :(
        /*List<Entity> l = this.world.getEntitiesWithinAABB(CreatureEntity.class, new AxisAlignedBB(pos, pos.add(1, 1, 1)).grow(16.0F));
        for (Entity e : l) {
            if (!(e instanceof MagicalPredatorEntity))
                if (((CreatureEntity) e).goalSelector.getRunningGoals().noneMatch(prioritizedGoal -> (prioritizedGoal.getGoal() instanceof PredatorStarBlock.CallEntity))) {
                    ((CreatureEntity) e).goalSelector.addGoal(-1, new PredatorStarBlock.CallEntity((CreatureEntity) e, pos));
                }
        }*/

        if (this.world.isRemote) {
            ++this.activeRotation;
        }
    }
}
