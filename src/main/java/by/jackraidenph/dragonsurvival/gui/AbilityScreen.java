package by.jackraidenph.dragonsurvival.gui;


import by.jackraidenph.dragonsurvival.DragonSurvivalMod;
import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityTree;
import by.jackraidenph.dragonsurvival.abilities.common.utils.AbilityType;
import by.jackraidenph.dragonsurvival.capability.DragonStateProvider;
import by.jackraidenph.dragonsurvival.util.DragonType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;

public class AbilityScreen extends Screen {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation(DragonSurvivalMod.MODID, "textures/gui/magic_interface.png");

    private final int xSize = 504 / 2;
    private final int ySize = 585 / 2;
    private int guiLeft;
    private int guiTop;
    private DragonType type;
    private int mouseX = 0;
    private int mouseY = 0;

    public AbilityScreen() {
        super(new StringTextComponent("AbilityScreenTest"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        if (this.minecraft == null)
            return;

        this.mouseX = mouseX;
        this.mouseY = mouseY;

        this.renderBackground();

        int startX = this.guiLeft;
        int startY = this.guiTop;

        super.render(mouseX, mouseY, partialTicks);

        this.minecraft.getTextureManager().bindTexture(BACKGROUND_TEXTURE);

        blit(startX, startY, 1, 0, 504 / 2, 330 / 2, xSize, ySize);

        float xpProgress = MathHelper.clamp((Minecraft.getInstance().player.experienceLevel / 18f), 0, 1);

        blit(startX + 71 / 2, startY + 64 / 2, 0, (330 + 5 * type.ordinal()) / 2f, (int) (410 / 2 * xpProgress), 5 / 2, xSize, ySize);
        blit(startX + 71 / 2, startY + 69 / 2, 0, (479 + 35 * type.ordinal()) / 2f, 410 / 2, 35 / 2, xSize, ySize);

        if (xpProgress * 18f < 3)
            blit(startX + (71 + 56) / 2, startY + 69 / 2, 0, 445 / 2f, 36 / 2, 35 / 2, xSize, ySize);
        if (xpProgress * 18f < 9)
            blit(startX + (71 + 56 + 94 + 36) / 2, startY + 69 / 2, 36 / 2f, 445 / 2f, 36 / 2, 35 / 2, xSize, ySize);
        if (xpProgress * 18f < 15)
            blit(startX + (71 + 56 + 94 + 97 + 36 + 36) / 2, startY + 69 / 2, (36 + 36) / 2f, 445 / 2f, 36 / 2, 35 / 2, xSize, ySize);

        drawAbilityTree(type, 75 + 74 / 2 - 8, 76 + 26 + 8, 8, 8, 96, 2, 4);
    }

    @Override
    protected void init() {
        super.init();

        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize / 2) / 2;

        if (Minecraft.getInstance().player != null)
            DragonStateProvider.getCap(Minecraft.getInstance().player).ifPresent(cap -> type = cap.getType());
    }

    private void drawAbilityTree(DragonType dragonType, int xRelative, int yRelative, int xStep, int yStep, int levelStep, int xCount, int yCount) {
        for (AbilityTree.AbilityTreeLevel level : DragonSurvivalMod.ABILITY_TREE_MAP.get(dragonType).getLevelsList()) {
            drawAbilityLevel((int) (xRelative + (level.getIndex() * levelStep * 1.37)), yRelative, xStep, yStep, xCount, yCount, level);
        }
    }

    private void drawAbilityLevel(int xRelative, int yRelative, int xStep, int yStep, int xCount, int yCount, AbilityTree.AbilityTreeLevel level) {
        for (int x = 0; x < Math.min(Math.ceil((float) level.getAbilityTypesList().size() / yCount), xCount); x++) {
            for (int y = 0; y < Math.min(level.getAbilityTypesList().size(), yCount); y++)
                drawAbilityIcon(level.getAbilityTypesList().get(x * yCount + y),
                        xRelative + (16 + xStep * 4) * x,
                        yRelative + (16 + yStep * 4) * y,
                        this.getStage(level.getIndex(), (x * yCount + y)));
        }
    }

    //TODO Hovered
    private void drawAbilityIcon(AbilityType ability, int xRelative, int yRelative, int stage) {
        ResourceLocation resourceLocation = new ResourceLocation(DragonSurvivalMod.MODID, "textures/ability/" + stage + "_" + ability.getId() + ".png");
        this.minecraft.getTextureManager().bindTexture(resourceLocation);
        resourceLocation = null;
        blit(this.guiLeft + xRelative / 2, this.guiTop + yRelative / 2, 0, 0, 16, 16, 16, 16);
    }

    private int getStage(int level, int index) {
        return DragonStateProvider.getCap(Minecraft.getInstance().player).map(cap -> cap.getUnlockedAbilityStage(level, index)).orElseGet(() -> 0);
    }
}
