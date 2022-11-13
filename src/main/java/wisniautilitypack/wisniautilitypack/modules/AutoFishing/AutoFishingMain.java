package wisniautilitypack.wisniautilitypack.modules.AutoFishing;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;
import wisniautilitypack.wisniautilitypack.modules.Module;

import static wisniautilitypack.wisniautilitypack.client.WisniaUtilityPackClient.inWorld;

public class AutoFishingMain extends Module {

    private static KeyBinding keyBinding;
    private static int keybindCounter;
    private static int counter;

    public static boolean enabled;

    public AutoFishingMain(String name, String desc, String mode,ModuleCategory category) {
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
                                enabled = !enabled;
                                MinecraftClient.getInstance().player.sendMessage(Text.of("Auto Fishing is now " + ((enabled) ? "enabled" : "disabled")), true);
                                keybindCounter = 0;
                            }
                        }
                        MinecraftClient client = MinecraftClient.getInstance();
                        if(enabled) {
                            if (counter == 9) {
                                ActionResult actionResult = client.interactionManager.interactItem(client.player, client.player.getActiveHand());
                                if (actionResult.isAccepted()) {
                                    if (actionResult.shouldSwingHand()) {
                                        client.player.swingHand(client.player.getActiveHand());
                                    }
                                    client.gameRenderer.firstPersonRenderer.resetEquipProgress(client.player.getActiveHand());
                                }
                            }
                        }
                        keybindCounter++;
                        counter ++;
                    }
                });

        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Auto Fishing toggle", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_V, // The keycode of the key
                "Wisniaq Utility Pack" // The translation key of the keybinding's category.
        ));
    }

    public static void tickFishingLogic(){
        if(!enabled){
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        ActionResult actionResult = client.interactionManager.interactItem(client.player, client.player.getActiveHand());
        if (actionResult.isAccepted()) {
            if (actionResult.shouldSwingHand()) {
                client.player.swingHand(client.player.getActiveHand());
            }
            client.gameRenderer.firstPersonRenderer.resetEquipProgress(client.player.getActiveHand());
        }
        counter = 0;
    }
}
