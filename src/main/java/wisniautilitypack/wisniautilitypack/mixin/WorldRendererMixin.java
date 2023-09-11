package wisniautilitypack.wisniautilitypack.mixin;

import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wisniautilitypack.wisniautilitypack.modules.RENDER.ESP.ContainerESP;
import wisniautilitypack.wisniautilitypack.modules.RENDER.ESP.EntityESP;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "render", at = @At("RETURN"))
    private void render_return(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo callback) {
        ContainerESP.render_return(matrices,camera);
        EntityESP.render_return(matrices,camera);
    }
}
