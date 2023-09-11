package wisniautilitypack.wisniautilitypack.mixin;

import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wisniautilitypack.wisniautilitypack.gui.UtilityScreen;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen{

    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "initWidgets()V")
    private void initWidgets(CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(net.minecraft.text.Text.of("Wisniaq menu"), (button) -> MinecraftClient.getInstance().setScreen(new CottonClientScreen(new UtilityScreen()))).position(this.width / 2 + 102 - 285, this.height / 4 + 24 + -16).size(75,20).build());
    }
}