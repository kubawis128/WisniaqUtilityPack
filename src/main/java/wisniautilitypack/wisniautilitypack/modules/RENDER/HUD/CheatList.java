package wisniautilitypack.wisniautilitypack.modules.RENDER.HUD;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;

import wisniautilitypack.wisniautilitypack.client.WisniaUtilityPackClient;
import wisniautilitypack.wisniautilitypack.modules.Module;

public class CheatList extends Module {
    private static boolean enabled;

    public CheatList(String name, String description, String mode, ModuleCategory category) {
        super(name, description, mode, category);
    }

    public static void draw(DrawContext context){
        int i = 0;
        if(enabled){
            for (Module mod: WisniaUtilityPackClient.ModuleList) {
                if(mod.getEnabled()){
                    TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
                    context.drawText(renderer,mod.name,10,10+10*i,0xFFFFFF,true);
                    i++;
                }
            }
        }
    }

    @Override
    public void init() {
        // Nothing to do
    }

    @Override
    public boolean getEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean state) {
        enabled = state;
    }

    @Override
    public String getMode() {
        return "";
    }
}
