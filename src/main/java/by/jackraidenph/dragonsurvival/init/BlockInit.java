package by.jackraidenph.dragonsurvival.init;

import by.jackraidenph.dragonsurvival.DragonSurvivalMod;
import by.jackraidenph.dragonsurvival.blocks.DragonAltarBlock;
import by.jackraidenph.dragonsurvival.blocks.DragonDoor;
import by.jackraidenph.dragonsurvival.blocks.PredatorStarBlock;
import by.jackraidenph.dragonsurvival.items.DragonDoorItem;
import by.jackraidenph.dragonsurvival.nest.NestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(DragonSurvivalMod.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockInit {
    public static ItemGroup blocks = new ItemGroup("dragon.survival.blocks") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemsInit.heartElement);
        }
    };

    public static Block predatorStarBlock;
    public static Block dragonAltar;
    public static NestBlock nestBlock;
    public static DragonDoor dragonDoor;

    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
        //WARNING: do not use final static initialization outside from here, because it breaks hot-swap
        IForgeRegistry<Block> forgeRegistry = event.getRegistry();

        dragonAltar = new DragonAltarBlock(Block.Properties
                .create(Material.ROCK)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(2)
                .hardnessAndResistance(5.0f)
                .lightValue(5)
                .sound(SoundType.STONE));
        forgeRegistry.register(dragonAltar);

        predatorStarBlock = new PredatorStarBlock(Block.Properties
                .create(Material.DRAGON_EGG)
                .doesNotBlockMovement()
                .hardnessAndResistance(9999)
                .tickRandomly()
                .noDrops()
                .sound(SoundType.NETHER_WART));
        forgeRegistry.register(predatorStarBlock.setRegistryName(DragonSurvivalMod.MOD_ID, "predator_star"));

        dragonDoor = new DragonDoor(Block.Properties
                .create(Material.WOOD, MaterialColor.BROWN)
                .hardnessAndResistance(3.0F)
                .sound(SoundType.WOOD)
                .notSolid());
        forgeRegistry.register(dragonDoor.setRegistryName(DragonSurvivalMod.MOD_ID, "dragon_gate"));

        nestBlock = new NestBlock(Block.Properties
                .create(Material.ROCK)
                .hardnessAndResistance(3, 100)
                .notSolid());
        forgeRegistry.register(nestBlock.setRegistryName(DragonSurvivalMod.MOD_ID, "dragon_nest"));
    }

    @SubscribeEvent
    public static void registerBlockItems(final RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> forgeRegistry = event.getRegistry();

        forgeRegistry.register(new BlockItem(dragonAltar, new Item.Properties().group(blocks)).setRegistryName(dragonAltar.getRegistryName()));
        forgeRegistry.register(new DragonDoorItem(dragonDoor, new Item.Properties().group(blocks)).setRegistryName(dragonDoor.getRegistryName()));
        forgeRegistry.register(new BlockItem(nestBlock, new Item.Properties().group(blocks)).setRegistryName(nestBlock.getRegistryName()));
        forgeRegistry.register(new BlockItem(predatorStarBlock, new Item.Properties().group(blocks)).setRegistryName(predatorStarBlock.getRegistryName()));
    }
}