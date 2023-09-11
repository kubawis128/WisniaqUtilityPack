package wisniautilitypack.wisniautilitypack.modules.AUTO.AutoFarming;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import wisniautilitypack.wisniautilitypack.modules.Module;

import java.util.ArrayList;
import java.util.List;

import static wisniautilitypack.wisniautilitypack.client.WisniaUtilityPackClient.inWorld;


public class AutoFarmingMain extends Module {

    public static boolean enabled;
    private static int counter;
    private List<BlockPos> blocksToCrop = new ArrayList<>();
    private List<BlockPos> blocksToPlant = new ArrayList<>();

    public AutoFarmingMain(String name, String desc, String mode,ModuleCategory category) {
        super(name,desc,mode,category);
    }
    public boolean getEnabled(){
        return enabled;
    }
    public void setEnabled(boolean state){
        enabled = state;
    }

    @Override
    public String getMode() {
        return "";
    }

    public void init(){
        ClientTickEvents.END_CLIENT_TICK.register((clientWorld) -> {
            if (inWorld) {
                if(MinecraftClient.getInstance().player == null){
                    return;
                }
                if(!enabled){
                    return;
                }
                if(counter >= 10){
                    BlockPos pos = MinecraftClient.getInstance().player.getBlockPos();
                    for(int y = -1; y <= 1; y++){
                        for(int x = -3; x <= 3; x++){
                            for(int z = -3; z <= 3; z++){
                                BlockPos blockPos = new BlockPos(pos).add(x,y,z);
                                BlockState blockState = clientWorld.world.getBlockState(blockPos);
                                if (blockState.getBlock() instanceof CropBlock) {
                                    if (((CropBlock) blockState.getBlock()).isMature(blockState)) {
                                        if(!blocksToCrop.contains(blockPos)){
                                            blocksToCrop.add(blockPos);
                                        }
                                    }
                                }
                                if(blockState.getBlock() instanceof FarmlandBlock){
                                    BlockState blockState2 = clientWorld.world.getBlockState(blockPos.add(0,1,0));
                                    if(!blockState2.isIn(BlockTags.MAINTAINS_FARMLAND)){
                                        if(!blocksToPlant.contains(blockPos)){
                                            blocksToPlant.add(blockPos);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    counter = 0;
                }
                if(counter % 2 == 0){
                    if(!blocksToPlant.isEmpty()){
                        BlockPos blockPos = blocksToPlant.remove(0);
                        if(blockPos.isWithinDistance(MinecraftClient.getInstance().player.getPos(), 3)){
                            MinecraftClient.getInstance().interactionManager.interactBlock(MinecraftClient.getInstance().player, Hand.OFF_HAND, BlockHitResult.createMissed(blockPos.toCenterPos(), Direction.UP, blockPos));
                        }
                    }
                }else{
                    if(!blocksToCrop.isEmpty()){
                        BlockPos blockPos = blocksToCrop.remove(0);
                        if(blockPos.isWithinDistance(MinecraftClient.getInstance().player.getPos(), 3)){
                            MinecraftClient.getInstance().interactionManager.attackBlock(blockPos, Direction.UP);
                        }
                    }
                }
                counter++;
            }
        });
    }
}