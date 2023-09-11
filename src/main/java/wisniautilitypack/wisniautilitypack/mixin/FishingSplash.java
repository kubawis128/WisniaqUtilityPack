package wisniautilitypack.wisniautilitypack.mixin;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wisniautilitypack.wisniautilitypack.modules.AUTO.AutoFishing.AutoFishingMain;

@Mixin(FishingBobberEntity.class)
public class FishingSplash {
    @Inject(method = "tickFishingLogic", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/FishingBobberEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
    private void tickFishingLogic(BlockPos pos, CallbackInfo ci) {
        AutoFishingMain.tickFishingLogic();
    }
}
