package wisniautilitypack.wisniautilitypack.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wisniautilitypack.wisniautilitypack.gui.WindowingSystem;
import wisniautilitypack.wisniautilitypack.modules.HUD.CheatList;

@Mixin(InGameHud.class)
public class OnHudUpdateMixin {
    @Inject(method = "render"  ,at=@At("RETURN"))
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        CheatList.draw(matrices);
        //WindowingSystem.draw(matrices);
        /*RenderSystem.disableTexture();
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

        bufferBuilder.vertex(50, 50, 0).color(0f,1f,1f,1f).next();
        bufferBuilder.vertex(50, 20, 0).color(0f,1f,0f,1f).next();
        bufferBuilder.vertex(20, 20, 0).color(1f,0f,0f,1f).next();
        bufferBuilder.vertex(20, 50, 0).color(1f,0f,1f,1f).next();

        tessellator.draw();

        RenderSystem.setShaderColor(1f,1f,1f, 1f);
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableTexture();*/

    }
}
