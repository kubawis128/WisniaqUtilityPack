package wisniautilitypack.wisniautilitypack.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wisniautilitypack.wisniautilitypack.modules.HUD.CheatsMenuHUD;

@Mixin(Mouse.class)
public class MouseClickMixin {
    @Inject(at = @At(value = "INVOKE",target = "Lnet/minecraft/client/Mouse;lockCursor()V"), method = "onMouseButton", cancellable = true)
    private void onMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if(CheatsMenuHUD.enabled){
            ci.cancel();
        }
    }
}
