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

    private NonNullList<AbilityType> abilityTypes;
    private int size;
    private int selectedSlot;
    private int maxMana;
    private int currentMana;

    public SynchronizeDragonAbilities() {

    }

    public SynchronizeDragonAbilities(int selectedSlot, int maxMana, int currentMana, NonNullList<AbilityType>abilityTypes) {
        this.maxMana = maxMana;
        this.currentMana = currentMana;
        this.abilityTypes = abilityTypes;
        this.selectedSlot = selectedSlot;
    }

    @Override
    public void encode(SynchronizeDragonAbilities message, PacketBuffer buffer) {
        buffer.writeInt(message.selectedSlot);
        buffer.writeInt(message.maxMana);
        buffer.writeInt(message.currentMana);
        for (AbilityType abilityType : message.abilityTypes) {
            buffer.writeString(abilityType.getId());
        }
    }

    @Override
    public SynchronizeDragonAbilities decode(PacketBuffer buffer) {
        NonNullList<AbilityType> abilities = NonNullList.create();
        int selectedSlot = buffer.readInt();
        int maxMana = buffer.readInt();
        int currentMana = buffer.readInt();
        for (int i = 0; i < 5; i++)
            abilities.add(i, DragonSurvivalMod.ABILITY_TYPES.get(buffer.readString()));
        return new SynchronizeDragonAbilities(selectedSlot, maxMana, currentMana, abilities);
    }

    @Override
    public void handle(SynchronizeDragonAbilities message, Supplier<NetworkEvent.Context> supplier) {
        if (supplier.get().getDirection().getReceptionSide().isClient() && (Minecraft.getInstance().player != null))
            DragonStateProvider.getCap(Minecraft.getInstance().player).ifPresent(cap -> {
                cap.setMaxMana(message.maxMana);
                cap.setCurrentMana(message.currentMana);
                cap.setSelectedAbilitySlot(message.selectedSlot);
                cap.setAbilitySlotList(AbilityType.toAbilityList(Minecraft.getInstance().player, message.abilityTypes));
            });

        ServerPlayerEntity playerEntity = supplier.get().getSender();
        if (playerEntity != null)
            DragonStateProvider.getCap(playerEntity).ifPresent(cap -> {
                cap.setMaxMana(message.maxMana);
                cap.setCurrentMana(message.currentMana);
                cap.setSelectedAbilitySlot(message.selectedSlot);
                cap.setAbilitySlotList(AbilityType.toAbilityList(supplier.get().getSender(), message.abilityTypes));
            });

    }
}
