package wisniautilitypack.wisniautilitypack.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.HoveredTooltipPositioner;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import wisniautilitypack.wisniautilitypack.modules.RENDER.HUD.CheatsMenuHUD;
import wisniautilitypack.wisniautilitypack.modules.Module;
import wisniautilitypack.wisniautilitypack.utils.Colors;
import wisniautilitypack.wisniautilitypack.utils.RendererHUD;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static wisniautilitypack.wisniautilitypack.utils.Colors.RGBtoInt;
import static wisniautilitypack.wisniautilitypack.utils.RendererHUD.drawSquare;

public class WindowingSystem extends Screen {
    public static ArrayList<Window> windowsList = new ArrayList<>();
    public static boolean drag = false;
    public static String draggingWindowTitle = "";
    public static boolean waitForRelease = false;

    public WindowingSystem(Text title) {
        super(title);
    }

        @Override
        public boolean shouldPause()
        {
            return false;
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int mouseButton)
        {
            if(mouseButton == 0){
                drag = true;
            }
            return super.mouseClicked(mouseX, mouseY, mouseButton);
        }


        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int mouseButton)
        {
            if(mouseButton == 0){
                drag = false;
                waitForRelease = false;
            }
            return super.mouseReleased(mouseX, mouseY, mouseButton);
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double delta)
        {
            return super.mouseScrolled(mouseX, mouseY, delta);
        }

    public static void addWindow(Window window){
        windowsList.add(window);
    }
    public static void addContent(Module.ModuleCategory category, GUIElement element){
        for (Window window: windowsList) {
            if(window.Title.equals(category.name())){
                window.elements.add(element);
            }
        }
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks)
    {
        super.render(context, mouseX, mouseY, partialTicks);
        for (Window window : windowsList) {
            if(drag && mouseX >= window.coord.pos.x && mouseX <= window.coord.pos.x + window.coord.width && mouseY >= window.coord.pos.y-5 && mouseY <= window.coord.pos.y + window.titleBarHeight+5){
                if(Objects.equals(draggingWindowTitle, "")){
                    draggingWindowTitle = window.Title;
                    window.holding.x = mouseX;
                    window.holding.y = mouseY;
                }
            }
            if(Objects.equals(draggingWindowTitle, window.Title) ){
                window.coord.pos.x -= window.holding.x - mouseX;
                window.coord.pos.y -= window.holding.y - mouseY;
                window.holding.x = mouseX;
                window.holding.y = mouseY;
            }

            if(!drag) {
                draggingWindowTitle = "";
            }
            drawWindow(context, window, mouseX, mouseY);
        }

    }
    public void drawWindow(DrawContext context, Window window, int mouseX, int mouseY){
        // Window Body
        drawSquare(window.coord.pos.x, window.coord.pos.y, window.coord.width+window.coord.pos.x, window.coord.height+window.coord.pos.y, window.bodyColor);

        // Top bar
        drawSquare(window.coord.pos.x, window.coord.pos.y, window.coord.width+window.coord.pos.x,window.coord.pos.y + window.titleBarHeight, window.titleBarColor);

        // Title
        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        RendererHUD.drawText(context, window.Title, window.coord.pos.x, window.coord.pos.y, RGBtoInt(window.textColor), RGBtoInt(new Colors.ColorRGB(0,0,0)),.75f,true);

        int index = 0;
        for (GUIElement element: window.elements) {
            if(element.type == GUIElement.ElementType.TEXT){
                if(element.getWidth() > window.coord.width){
                    window.coord.width = element.getWidth();
                }
                drawSquare(window.coord.pos.x, window.coord.pos.y + window.titleBarHeight + (10 * index), window.coord.width+window.coord.pos.x,window.coord.pos.y + window.titleBarHeight + (10 * index) + 10, new Colors.ColorRGBA(0,0,0,.5f));
                context.drawText(renderer,element.getText(),window.coord.pos.x + 2,window.coord.pos.y + window.titleBarHeight + (10 * index) + 2, RGBtoInt(element.colorRGB), true);
                index++;
            }
            if(element.type == GUIElement.ElementType.MODULETOGGLE){
                if(element.getModule() instanceof CheatsMenuHUD){
                    return;
                }
                if(element.getWidth() > window.coord.width){
                    window.coord.width = element.getWidth();
                }
                if(mouseX >= window.coord.pos.x && mouseX <= window.coord.pos.x + window.coord.width && mouseY >= window.coord.pos.y + window.titleBarHeight + (10 * index) && mouseY <= window.coord.pos.y + window.titleBarHeight + (10 * index) + 10){
                    if(hasAltDown()){
                        Text tooltip = Text.of(element.getModule().description);
                        PositionedTooltip tool = new PositionedTooltip(Tooltip.wrapLines(MinecraftClient.getInstance(), tooltip), HoveredTooltipPositioner.INSTANCE);
                        context.drawTooltip(this.textRenderer, tool.tooltip(), tool.positioner(), mouseX, mouseY);
                    }

                    if(!waitForRelease && drag){
                        if(Objects.equals(draggingWindowTitle,"")) {
                            System.out.println("Clicked: " + element.getText());
                            element.getModule().setEnabled(!element.getModule().getEnabled());
                            waitForRelease = true;
                        }
                    }
                }
                drawSquare(window.coord.pos.x, window.coord.pos.y + window.titleBarHeight + (10 * index), window.coord.width+window.coord.pos.x,window.coord.pos.y + window.titleBarHeight + (10 * index) + 10, (element.getModule().getEnabled()) ? new Colors.ColorRGBA(0,1,0,.5f):new Colors.ColorRGBA(0,0,0,.5f));
                context.drawText(renderer,element.getText(),window.coord.pos.x + 2,window.coord.pos.y + window.titleBarHeight + (10 * index) + 2, RGBtoInt(element.colorRGB), true);
                index++;
            }
        }


    }
    public record Window(Coordinates coord,
                         int titleBarHeight,
                         String Title,
                         Colors.ColorRGBA bodyColor,
                         Colors.ColorRGBA titleBarColor,
                         Colors.ColorRGB textColor,
                         ArrayList<GUIElement> elements,
                         Pos2D holding 
    ){}
    public static class Coordinates {
        public int width;
        public int height;

        public Pos2D pos;

        public Coordinates(Pos2D pos, int width, int height) {
            this.pos = pos;
            this.width = width;
            this.height = height;
        }
    }
    public static class Pos2D {
        public int x;
        public int y;

        public Pos2D(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    @Environment(EnvType.CLIENT)
    private record PositionedTooltip(List<OrderedText> tooltip, TooltipPositioner positioner) {
        public List<OrderedText> tooltip() {
            return this.tooltip;
        }

        public TooltipPositioner positioner() {
            return this.positioner;
        }
    }
}
