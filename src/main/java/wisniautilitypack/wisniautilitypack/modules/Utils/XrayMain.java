package wisniautilitypack.wisniautilitypack.modules.Utils;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wisniautilitypack.wisniautilitypack.modules.Module;

import static wisniautilitypack.wisniautilitypack.client.WisniaUtilityPackClient.inWorld;

public class XrayMain extends Module {
    private static KeyBinding keyBinding;
    private static int keybindCounter;
    public static boolean enabled;

    public XrayMain(String name, String desc, String mode,ModuleCategory category) {
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

    public void changeEnable(){
        MinecraftClient.getInstance().player.sendMessage(Text.of("X-Ray is now " + ((enabled) ? "enabled" : "disabled")), true);
        MinecraftClient.getInstance().worldRenderer.reload();
    }
    public void init(){
        ClientTickEvents.END_CLIENT_TICK.register((e) -> {
            if (inWorld) {
                if (keybindCounter >= 5) {
                    if (keyBinding.isPressed()) {
                        enabled = !enabled;
                        changeEnable();
                        keybindCounter = 0;
                    }
                }
                keybindCounter++;
            }
        });

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "X-Ray Toggle", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_X, // The keycode of the key
                "Wisniaq Utility Pack" // The translation key of the keybinding's category.
        ));
    }
    public static void shouldDrawSide(BlockState state, CallbackInfoReturnable<Boolean> ci) {
        if (!enabled) {
            return;
        }
        if (state.getBlock().getTranslationKey().contains("ore")) { // TODO: Add interesting block list
            ci.setReturnValue(true);
        } else {
            ci.setReturnValue(false);
        }
    }
    public static void isTranslucent(BlockState state, CallbackInfoReturnable<Boolean> ci){
        if (!enabled) {
            return;
        }
        if (state.getBlock().getTranslationKey().contains("ore")) { // TODO: Add interesting block list
            ci.setReturnValue(false);
        } else {
            ci.setReturnValue(true);
        }
    }
}
