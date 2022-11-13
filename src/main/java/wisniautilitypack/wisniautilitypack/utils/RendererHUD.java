package wisniautilitypack.wisniautilitypack.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;

import static net.minecraft.client.gui.DrawableHelper.drawWithShadow;
import static wisniautilitypack.wisniautilitypack.utils.Colors.RGBtoInt;

public class RendererHUD {
    public static void drawSquare(float x1, float y1, float x2, float y2, Colors.ColorRGBA color){
        RenderSystem.disableTexture();
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(515);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1f,1f,1f, 1f);

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS,
                VertexFormats.POSITION_COLOR);

        bufferBuilder.vertex(x2, y2, 0).color(color.getR(),color.getG(),color.getB(), color.getA()).next();
        bufferBuilder.vertex(x2, y1, 0).color(color.getR(),color.getG(),color.getB(), color.getA()).next();
        bufferBuilder.vertex(x1, y1, 0).color(color.getR(),color.getG(),color.getB(), color.getA()).next();
        bufferBuilder.vertex(x1, y2, 0).color(color.getR(),color.getG(),color.getB(), color.getA()).next();

        tessellator.draw();

        RenderSystem.setShaderColor(1f,1f,1f, 1f);
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableTexture();
    }
    public static void drawText(MatrixStack matrices, String text, int x, int y, Colors.ColorRGB color){
        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        renderer.drawWithShadow(matrices,text,x,y, RGBtoInt(color));
    }
}
