package by.dragonsurvivalteam.dragonsurvival.network.container;

import by.dragonsurvivalteam.dragonsurvival.common.util.DragonUtils;
import by.dragonsurvivalteam.dragonsurvival.network.IMessage;
import by.dragonsurvivalteam.dragonsurvival.server.containers.DragonContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class OpenDragonInventory implements IMessage<OpenDragonInventory>{
	@Override
	public void encode(OpenDragonInventory message, PacketBuffer buffer){}

	@Override
	public OpenDragonInventory decode(PacketBuffer buffer){
		return new OpenDragonInventory();
	}

	@Override
	public void handle(OpenDragonInventory message, Supplier<NetworkEvent.Context> supplier){
		ServerPlayerEntity serverPlayerEntity = supplier.get().getSender();
		if(DragonUtils.isDragon(serverPlayerEntity)){

			if(serverPlayerEntity.containerMenu != null){
				serverPlayerEntity.containerMenu.removed(serverPlayerEntity);
			}

			serverPlayerEntity.openMenu(new INamedContainerProvider(){
				@Override
				public ITextComponent getDisplayName(){
					return new StringTextComponent("");
				}

				@Nullable
				@Override
				public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_){
					return new DragonContainer(p_createMenu_1_, p_createMenu_2_, false);
				}
			});
		}
	}
}