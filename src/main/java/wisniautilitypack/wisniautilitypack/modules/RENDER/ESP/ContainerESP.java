package wisniautilitypack.wisniautilitypack.modules.RENDER.ESP;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;
import wisniautilitypack.wisniautilitypack.client.WisniaUtilityPackClient;
import wisniautilitypack.wisniautilitypack.modules.Module;
import wisniautilitypack.wisniautilitypack.utils.Colors;
import wisniautilitypack.wisniautilitypack.utils.Renderer;
import wisniautilitypack.wisniautilitypack.utils.events.EventListener;
import wisniautilitypack.wisniautilitypack.utils.events.EventName;

import java.util.ArrayList;

import static wisniautilitypack.wisniautilitypack.client.WisniaUtilityPackClient.inWorld;

public class ContainerESP extends Module implements EventListener {
    private static KeyBinding keyBinding;
    private static int keybindCounter;
    public static boolean enabled;

    public ContainerESP(String name, String desc, String mode,ModuleCategory category) {
        super(name,desc,mode,category);
    }
    public boolean getEnabled(){
        return enabled;
    }
    public void setEnabled(boolean state){
        enabled = state;
        changeEnable();
    }

    @Override
    public String getMode() {
        return "";
    }

    public record Result(BlockState blockState,BlockPos blockPos){}
    public static ArrayList<Result> results = new ArrayList<>();

    public void changeEnable(){
        if(enabled){
            int y;
            int x;
            int z;

            for (y = -64; y <= 256; y++) {
                for (x = -64; x <= 64; x ++) {
                    for (z = -64; z <= 64; z ++) {
                        Vec3d PlayerPos = MinecraftClient.getInstance().player.getPos();
                        BlockPos pos = new BlockPos((int) (y-PlayerPos.getY()), (int) (x-PlayerPos.getX()), (int) (z-PlayerPos.getZ()));
                        BlockState blockState = MinecraftClient.getInstance().world.getBlockState(pos);
                        if(blockState.getBlock().getTranslationKey().contains("result")){ // TODO: Add interesting blocks list
                            results.add(new Result(blockState,pos));
                        }
                    }
                }
            }
        }else{
            results.clear();
        }
        MinecraftClient.getInstance().player.sendMessage(Text.of("Container ESP is now " + ((enabled) ? "enabled" : "disabled")), true);
    }
    public void init() {
        WisniaUtilityPackClient.initiater.addListener(this);
        ClientTickEvents.END_CLIENT_TICK.register((e) -> {
            if (inWorld) {
                if (keybindCounter >= 5) {
                    if (keyBinding.isPressed()) {
                        enabled = !enabled;
                        keybindCounter = 0;
                        changeEnable();
                    }
                }
                keybindCounter++;
            }
        });
        ClientChunkEvents.CHUNK_LOAD.register((clientWorld, worldChunk) -> {
            if (!inWorld || !enabled) {
                return;
            }
            for (int y = -64; y <= 256; y++) {
                for (int x = 0; x <= 16; x++) {
                    for (int z = 0; z <= 16; z++) {
                        BlockPos pos = new BlockPos(x+worldChunk.getPos().getStartPos().getX(),y+worldChunk.getPos().getStartPos().getY(),z+worldChunk.getPos().getStartPos().getZ());
                        BlockState blockState = MinecraftClient.getInstance().world.getBlockState(pos);
                        if(blockState.getBlock().getTranslationKey().contains("chest")){ // TODO: add interesting blocks
                            results.add(new Result(blockState,pos));
                        }
                    }
                }
            }
        });
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Container ESP toggle", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_H, // The keycode of the key
                "Wisniaq Utility Pack" // The translation key of the keybinding's category.
        ));
    }

    @Override
    public void onEvent(EventName eventName){
        if(eventName == EventName.WORLD_UNLOAD){
            results.clear();
        }
    }
    public static void draw(MatrixStack matrices, Camera camera){
        if (!enabled) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        if(client.player != null && results.size() != 0) {
            for (Result resoult: results) {
                Renderer.drawBox(matrices,camera,resoult.blockPos.getX(),resoult.blockPos.getY(),resoult.blockPos.getZ(),resoult.blockPos.getX()+1,resoult.blockPos.getY()+1,resoult.blockPos.getZ()+1,new Colors.ColorRGBA(1.0f,1.0f,0.0f,0.4f),false);
                Renderer.drawBox(matrices,camera,resoult.blockPos.getX(),resoult.blockPos.getY(),resoult.blockPos.getZ(),resoult.blockPos.getX()+1,resoult.blockPos.getY()+1,resoult.blockPos.getZ()+1,new Colors.ColorRGBA(1.0f,1.0f,0.0f,0.75f),true);
            }
        }
    }
    public static void render_return(MatrixStack matrices, Camera camera){
        if (inWorld) {
            draw(matrices,camera);
        }
    }
}
