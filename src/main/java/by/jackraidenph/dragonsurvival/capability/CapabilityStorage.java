package by.jackraidenph.dragonsurvival.capability;

import by.jackraidenph.dragonsurvival.util.DragonLevel;
import by.jackraidenph.dragonsurvival.util.DragonType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;

public class CapabilityStorage implements Capability.IStorage<DragonStateHandler> {
    private static CompoundNBT writeVec3d(Vector3d vec) {
        CompoundNBT comp = new CompoundNBT();
        comp.putDouble("X", vec.x);
        comp.putDouble("Y", vec.y);
        comp.putDouble("Z", vec.z);
        return comp;
    }

    private static Vector3d getVec3d(INBT nbt) {
        CompoundNBT tag = (CompoundNBT) nbt;
        Vector3d vec = Vector3d.ZERO;
        if (tag != null && tag.contains("X"))
            vec = new Vector3d(tag.getDouble("X"), tag.getDouble("Y"), tag.getDouble("Z"));
        return vec;
    }

    @Override
    public INBT writeNBT(Capability<DragonStateHandler> capability, DragonStateHandler instance, Direction side) {
        CompoundNBT tag = new CompoundNBT();
        tag.putBoolean("isDragon", instance.isDragon());
        if (instance.isDragon()) {
            DragonStateHandler.DragonMovementData movementData = instance.getMovementData();

            tag.putDouble("bodyYaw", movementData.bodyYaw);
            tag.putDouble("headYaw", movementData.headYaw);
            tag.putDouble("headPitch", movementData.headPitch);
            tag.putBoolean("isHiding", instance.isHiding());
            tag.putString("type", instance.getType().toString());
            tag.putFloat("size", instance.getSize());
            tag.putBoolean("hasWings", instance.hasWings());
        }
        return tag;
    }

    @Override
    public void readNBT(Capability<DragonStateHandler> capability, DragonStateHandler instance, Direction side, INBT base) {
        CompoundNBT tag = (CompoundNBT) base;
        instance.setIsDragon(tag.getBoolean("isDragon"));
        if (tag.getBoolean("isDragon")) {
            instance.setMovementData(tag.getDouble("bodyYaw"), tag.getDouble("headYaw"),
                    tag.getDouble("headPitch"));
            instance.setIsHiding(tag.getBoolean("isHiding"));
            instance.setType(DragonType.valueOf(tag.getString("type")));
            instance.setSize(tag.getFloat("size"));
            if (instance.getSize() == 0)
                instance.setSize(DragonLevel.BABY.initialHealth);
            instance.setHasWings(tag.getBoolean("hasWings"));
        }
    }
}
