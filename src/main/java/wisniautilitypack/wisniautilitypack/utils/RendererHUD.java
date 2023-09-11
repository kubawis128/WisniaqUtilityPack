package wisniautilitypack.wisniautilitypack.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

import static wisniautilitypack.wisniautilitypack.utils.Colors.RGBtoInt;

public class RendererHUD {
    public static void drawSquare(float x1, float y1, float x2, float y2, Colors.ColorRGBA color){
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(515);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
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
    }

    public static void drawText(DrawContext context, String text, float x, float y, int color, int backgroundColor, float scale, boolean shadow){
        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
        VertexConsumerProvider consumers = context.getVertexConsumers();
        matrix.scale(scale);
        float backToOne = 1f/scale;
        renderer.draw(text, x*backToOne+(2*backToOne), y*backToOne+(2*backToOne), color, shadow, matrix, consumers, TextRenderer.TextLayerType.NORMAL, backgroundColor, 255);
        matrix.scale(backToOne);
        //context.drawText(renderer, text, (int) (window.coord.x1), (int) (window.coord.y1),RGBtoInt(window.textColor),true);
    }
}
