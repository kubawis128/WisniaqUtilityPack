package wisniautilitypack.wisniautilitypack.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wisniautilitypack.wisniautilitypack.modules.Utils.XrayMain;

@Mixin(Block.class)
public class ShouldRenderSideMixin {

    /*@Inject(at = @At("HEAD"),method = "isSideInvisible",cancellable = true)
    public void isSideInvisible(BlockState state, Direction direction, CallbackInfoReturnable<Boolean> ci) {
        if(state.getBlock().getTranslationKey().equals("block.minecraft.stone")){
            ci.setReturnValue(false);
            return;
        }else{
            ci.setReturnValue(true);
        }
    }*/

    @Inject(at = @At("HEAD"),method = "shouldDrawSide",cancellable = true)
    private static void shouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos otherPos, CallbackInfoReturnable<Boolean> ci) {
        XrayMain.shouldDrawSide(state,ci);
    }
    @Inject(at = @At("HEAD"),method = "isTranslucent",cancellable = true)
    public void isTranslucent(BlockState state, BlockView world, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        XrayMain.isTranslucent(state, ci);
        return;
    }

    /*@Inject(at = @At("HEAD"),method = "getAmbientOcclusionLightLevel",cancellable = true)
    public void getAmbientOcclusionLightLevel(BlockView world, BlockPos pos,CallbackInfoReturnable<Float> ci) {
        if(world.getBlockState(pos).getBlock().getTranslationKey().equals("block.minecraft.stone")){
            ci.setReturnValue(1.0f);
            return;
        }
    }*/
    /*@Inject(at = @At("HEAD"),method = "getCullingFace",cancellable = true)
    public void getCullingFace(BlockView world, BlockPos pos, Direction direction,CallbackInfoReturnable<VoxelShape> ci) {
        if(world.getBlockState(pos).getBlock().getTranslationKey().equals("block.minecraft.stone")){
            ci.setReturnValue(VoxelShapes.fullCube());
            return;
        }
    }*/
}
