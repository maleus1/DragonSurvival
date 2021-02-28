package by.jackraidenph.dragonsurvival.network;

import by.jackraidenph.dragonsurvival.abilities.common.ChargeableDragonAbility;
import by.jackraidenph.dragonsurvival.abilities.common.IDragonAbility;
import by.jackraidenph.dragonsurvival.capability.DragonStateProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.lwjgl.glfw.GLFW;

import java.util.function.Supplier;

public class ActivateAbilityInSlot implements IMessage<ActivateAbilityInSlot> {

    private int slot;
    private byte glfwMode;

    public ActivateAbilityInSlot(int slot, byte glfwMode) {
        this.slot = slot;
        this.glfwMode = glfwMode;
    }

    public ActivateAbilityInSlot() {
    }

    @Override
    public void encode(ActivateAbilityInSlot message, PacketBuffer buffer) {
        buffer.writeInt(message.slot);
        buffer.writeByte(message.glfwMode);
    }

    @Override
    public ActivateAbilityInSlot decode(PacketBuffer buffer) {
        int slot = buffer.readInt();
        byte glfwMode = buffer.readByte();
        return new ActivateAbilityInSlot(slot, glfwMode);
    }

    @Override
    public void handle(ActivateAbilityInSlot message, Supplier<NetworkEvent.Context> supplier) {
        ServerPlayerEntity playerEntity = supplier.get().getSender();

        if(playerEntity == null)
            return;

        DragonStateProvider.getCap(playerEntity).ifPresent(dragonStateHandler -> {
            IDragonAbility ability = dragonStateHandler.getAbilityFromSlot(message.slot);
            if (!(ability instanceof ChargeableDragonAbility) && (message.glfwMode == GLFW.GLFW_PRESS))
                ability.onKeyPressed();
            else if (ability instanceof ChargeableDragonAbility) {
                ChargeableDragonAbility chargeableDragonAbility = (ChargeableDragonAbility) ability;
                if (message.glfwMode == GLFW.GLFW_RELEASE)
                    chargeableDragonAbility.stopCharge();
                else if (message.glfwMode == GLFW.GLFW_REPEAT) {
                    chargeableDragonAbility.charge();
                    chargeableDragonAbility.onKeyPressed();
                }

            }
        });
    }
}
