package pml;

import net.minecraft.client.Minecraft;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.Tessellator;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class PMLLoadingScreen {

    public static void render(String status, int progress) {
        Minecraft mc = Minecraft.instance;
        if (mc == null || mc.gameSettings == null) return;

        ScaledResolution scaledresolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int width = scaledresolution.getScaledWidth();
        int height = scaledresolution.getScaledHeight();

        // 2d view
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, (double)width, (double)height, 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);

        // bg
        drawRect(0, 0, width, height, 0xFF121212);

        int centerX = width / 2;
        int centerY = height / 2;

        if (mc.renderEngine != null) {
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            // render mojang
            int mojangTex = mc.renderEngine.getTexture("/pml/mojang.png");
            mc.renderEngine.bindTexture(mojangTex);

            int mojangW = 128;
            int mojangH = 128;
            int mojangY = centerY - 100;

            drawFullTexture(centerX - (mojangW / 2), mojangY, mojangW, mojangH);

            // render pml
            int pmlTex = mc.renderEngine.getTexture("/pml/logo.png");
            mc.renderEngine.bindTexture(pmlTex);

            int displayScale = 2;
            int pmlW = 48 * displayScale;
            int pmlH = 16 * displayScale;
            int pmlY = centerY + 35;

            drawFullTexture(centerX - (pmlW / 2), pmlY, pmlW, pmlH);
        }

        FontRenderer font = mc.fontRenderer;
        if (font != null) {
            // status text
            if (status != null && !status.isEmpty()) {
                font.drawStringWithShadow(status, centerX - (font.getStringWidth(status) / 2), centerY + 85, 0xAAAAAA);
            }
        }

        // progress bar
        int barWidth = 180;
        int barHeight = 8;
        int barX = centerX - (barWidth / 2);
        int barY = centerY + 100;

        drawRect(barX - 1, barY - 1, barX + barWidth + 1, barY + barHeight + 1, 0xFF555555);
        drawRect(barX, barY, barX + barWidth, barY + barHeight, 0xFF222222);

        if (progress > 0) {
            int currentProgress = Math.min(progress, 100);
            int fillWidth = (barWidth * currentProgress) / 100;
            drawRect(barX, barY, barX + fillWidth, barY + barHeight, 0xFF00AAAA);
        }

        // show latest buffer
        Display.update();
    }

    // render texture
    private static void drawFullTexture(int x, int y, int width, int height) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, 0.0D, 0.0F, 1.0F);
        tessellator.addVertexWithUV(x + width, y + height, 0.0D, 1.0F, 1.0F);
        tessellator.addVertexWithUV(x + width, y, 0.0D, 1.0F, 0.0F);
        tessellator.addVertexWithUV(x, y, 0.0D, 0.0F, 0.0F);
        tessellator.draw();

        GL11.glDisable(GL11.GL_BLEND);
    }

    private static void drawRect(int left, int top, int right, int bottom, int color) {
        float alpha = (float)(color >> 24 & 255) / 255.0F;
        float red   = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue  = (float)(color & 255) / 255.0F;

        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(red, green, blue, alpha);

        tessellator.startDrawingQuads();
        tessellator.addVertex(left, bottom, 0.0D);
        tessellator.addVertex(right, bottom, 0.0D);
        tessellator.addVertex(right, top, 0.0D);
        tessellator.addVertex(left, top, 0.0D);
        tessellator.draw();

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
}