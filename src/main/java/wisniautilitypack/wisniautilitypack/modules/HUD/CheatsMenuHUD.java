package wisniautilitypack.wisniautilitypack.modules.HUD;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wisniautilitypack.wisniautilitypack.gui.WindowingSystem;
import wisniautilitypack.wisniautilitypack.modules.Module;

import static wisniautilitypack.wisniautilitypack.client.WisniaUtilityPackClient.inWorld;

public class CheatsMenuHUD extends Module {
    private static KeyBinding keyBinding;
    private static int keybindCounter;
    public static boolean enabled;

    public CheatsMenuHUD(String name, String desc, String mode,ModuleCategory category) {
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
        ClientTickEvents.END_CLIENT_TICK.register((e) -> {
            if (inWorld) {
                if (keybindCounter >= 5) {
                    if (keyBinding.isPressed()) {
                        MinecraftClient.getInstance().mouse.unlockCursor();
                        MinecraftClient.getInstance().setScreen(new WindowingSystem(Text.of("Windows")));
                        keybindCounter = 0;
                    }
                }
                keybindCounter++;
            }
        });

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Menu", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_U, // The keycode of the key
                "Wisniaq Utility Pack" // The translation key of the keybinding's category.
        ));
    }
}
