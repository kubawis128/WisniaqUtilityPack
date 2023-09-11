package wisniautilitypack.wisniautilitypack.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wisniautilitypack.wisniautilitypack.modules.RENDER.HUD.CheatList;

@Mixin(InGameHud.class)
public class OnHudUpdateMixin {
    @Inject(method = "render"  ,at=@At("RETURN"))
    public void render(DrawContext context, float tickDelta, CallbackInfo ci) {
        CheatList.draw(context);
    }
}
