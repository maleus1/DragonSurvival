package by.jackraidenph.dragonsurvival;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class Functions {
    public static float getDefaultXRightLimbRotation(float limbSwing, float swingAmount) {
        return MathHelper.cos((float) (limbSwing + Math.PI)) * swingAmount;
    }

    public static float getDefaultXLeftLimbRotation(float limbSwing, float swingAmount) {
        return MathHelper.cos(limbSwing) * swingAmount;
    }

    /**
     * Angle Y or Z
     */
    public static float getDefaultHeadYaw(float netYaw) {
        return netYaw * 0.017453292F;
    }

    /**
     * Angle X
     */
    public static float getDefaultHeadPitch(float pitch) {
        return pitch * 0.017453292F;
    }

    public static float degreesToRadians(float degrees) {
        return (float) (degrees * Math.PI / 180);
    }

    public static void blit(int startX, int startY, float textureX, float textureY, int width, int height, int sizeX, int sizeY) {
        AbstractGui.blit(startX, startY, textureX, textureY, width, height, sizeX, sizeY);
    }

    public static byte getKeyMode(KeyBinding keyBinding) {
        if (keyBinding.isKeyDown())
            return GLFW.GLFW_REPEAT;
        else if (keyBinding.isPressed())
            return GLFW.GLFW_PRESS;
        else
            return GLFW.GLFW_RELEASE;
    }

}
