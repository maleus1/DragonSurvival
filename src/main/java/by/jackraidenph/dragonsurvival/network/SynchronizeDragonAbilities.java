package by.jackraidenph.dragonsurvival.network;

import by.jackraidenph.dragonsurvival.DragonSurvivalMod;
import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import by.jackraidenph.dragonsurvival.capability.DragonStateProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SynchronizeDragonAbilities implements IMessage<SynchronizeDragonAbilities> {

    public NonNullList<AbilityType> abilityTypes;
    int size;

    public SynchronizeDragonAbilities() {

    }

    public SynchronizeDragonAbilities(int size, NonNullList<AbilityType> abilityTypes) {
        this.size = size;
        this.abilityTypes = abilityTypes;
    }

    @Override
    public void encode(SynchronizeDragonAbilities message, PacketBuffer buffer) {
        buffer.writeInt(message.size);
        for (AbilityType abilityType : message.abilityTypes) {
            buffer.writeString(abilityType.getId());
        }
    }

    @Override
    public SynchronizeDragonAbilities decode(PacketBuffer buffer) {
        NonNullList<AbilityType> abilities = NonNullList.create();
        int size = buffer.readInt();
        for (int i = 0; i < size; i++)
            abilities.add(i, DragonSurvivalMod.ABILITIES_MAP.get(buffer.readString()));
        return new SynchronizeDragonAbilities(size, abilities);
    }

    @Override
    public void handle(SynchronizeDragonAbilities message, Supplier<NetworkEvent.Context> supplier) {

        if (supplier.get().getDirection().getReceptionSide().isClient())
            DragonStateProvider.getCap(Minecraft.getInstance().player).ifPresent(cap -> {
                cap.setMaxActiveAbilitySlots(message.size);
                cap.setAbilitySlotList(AbilityType.toAbilityList(Minecraft.getInstance().player, message.abilityTypes));
            });

        ServerPlayerEntity playerEntity = supplier.get().getSender();
        if (playerEntity != null)
            DragonStateProvider.getCap(playerEntity).ifPresent(cap -> {
                cap.setMaxActiveAbilitySlots(message.size);
                cap.setAbilitySlotList(AbilityType.toAbilityList(supplier.get().getSender(), message.abilityTypes));
            });

    }
}
