package wisniautilitypack.wisniautilitypack.modules.HUD;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;

import wisniautilitypack.wisniautilitypack.client.WisniaUtilityPackClient;
import wisniautilitypack.wisniautilitypack.modules.Module;

public class CheatList {

    public static void draw(MatrixStack matrices){
        int i = 0;
        for (Module mod: WisniaUtilityPackClient.ModuleList) {
             if(mod.getEnabled()){
                 TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
                 renderer.drawWithShadow(matrices,mod.name,10,10+10*i, 0xFFFFFF);
                 matrices.scale(0.5f,0.5f,0.5f);
                 renderer.drawWithShadow(matrices,mod.getMode(),(12+renderer.getWidth(mod.name))*2,(10+10*i)*2, 0xFFFFFF);
                 matrices.scale(2.0f,2.0f,2.0f);
                 i++;
             }
        }
    }
}
