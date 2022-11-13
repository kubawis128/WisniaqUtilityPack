package wisniautilitypack.wisniautilitypack.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import wisniautilitypack.wisniautilitypack.modules.Module;
import wisniautilitypack.wisniautilitypack.utils.Colors;

import java.util.ArrayList;
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
            System.out.println("MouseClicked: " + mouseButton);
            if(mouseButton == 0){
                drag = true;
            }
            return super.mouseClicked(mouseX, mouseY, mouseButton);
        }

        @Override
        public boolean mouseReleased(double mouseX, double mouseY, int mouseButton)
        {
            System.out.println("MouseReleased: " + mouseButton);
            if(mouseButton == 0){
                drag = false;
                waitForRelease = false;
            }
            return super.mouseReleased(mouseX, mouseY, mouseButton);
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double delta)
        {
            System.out.println("Mouse scroll: \nX: " + mouseX + "\nY: " + mouseY + "\ndelta: " + delta);
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
    public void render(MatrixStack matrixStack, int mouseX, int mouseY,
                       float partialTicks)
    {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        for (Window window : windowsList) {
            if(drag && mouseX >= window.coord.x1 && mouseX <= window.coord.x1 + window.coord.width && mouseY >= window.coord.y1-5 && mouseY <= window.coord.y1 + window.titleBarHeight+5){
                if(Objects.equals(draggingWindowTitle, window.Title) || Objects.equals(draggingWindowTitle, "")){
                    System.out.println("Dragging window: " + window.Title);
                    window.coord.setX1(mouseX-(window.coord.width/2));
                    window.coord.setY1(mouseY-(window.titleBarHeight/2));
                    draggingWindowTitle = window.Title;
                }
            }
            if(!drag) {
                draggingWindowTitle = "";
            }
            drawWindow(matrixStack, window, mouseX, mouseY);
        }

    }
    public static void drawWindow(MatrixStack matrices,Window window, int mouseX, int mouseY){
        // Window Body
        drawSquare(window.coord.x1, window.coord.y1, window.coord.width+window.coord.x1, window.coord.height+window.coord.y1, window.bodyColor);

        // Top bar
        drawSquare(window.coord.x1, window.coord.y1, window.coord.width+window.coord.x1,window.coord.y1 + window.titleBarHeight, window.titleBarColor);

        // Title
        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        matrices.scale(0.75f,0.75f,0.75f);
        float backToOne = 1f/0.75f;
        renderer.drawWithShadow(matrices,window.Title,window.coord.x1*backToOne+(2*backToOne),window.coord.y1*backToOne+(2*backToOne), RGBtoInt(window.textColor));
        matrices.scale(backToOne,backToOne,backToOne);
        int index = 0;
        for (GUIElement element: window.elements) {
            if(element.type == GUIElement.ElementType.TEXT){
                if(element.getWidth() > window.coord.width){
                    window.coord.setWidth(element.getWidth());
                }
                drawSquare(window.coord.x1, window.coord.y1 + window.titleBarHeight + (10 * index), window.coord.width+window.coord.x1,window.coord.y1 + window.titleBarHeight + (10 * index) + 10, new Colors.ColorRGBA(0,0,0,.5f));
                renderer.drawWithShadow(matrices,element.getText(),window.coord.x1 + 2,window.coord.y1 + window.titleBarHeight + (10 * index) + 2, RGBtoInt(element.colorRGB));
                index++;
            }
            if(element.type == GUIElement.ElementType.MODULETOGGLE){
                if(element.getWidth() > window.coord.width){
                    window.coord.setWidth(element.getWidth());
                }
                if(!waitForRelease && drag && mouseX >= window.coord.x1 && mouseX <= window.coord.x1 + window.coord.width && mouseY >= window.coord.y1 + window.titleBarHeight + (10 * index) && mouseY <= window.coord.y1 + window.titleBarHeight + (10 * index) + 10){
                    if(Objects.equals(draggingWindowTitle,"")) {
                        System.out.println("Clicked: " + element.getText());
                        element.getModule().setEnabled(!element.getModule().getEnabled());
                        waitForRelease = true;
                    }
                }
                drawSquare(window.coord.x1, window.coord.y1 + window.titleBarHeight + (10 * index), window.coord.width+window.coord.x1,window.coord.y1 + window.titleBarHeight + (10 * index) + 10, (element.getModule().getEnabled()) ? new Colors.ColorRGBA(0,1,0,.5f):new Colors.ColorRGBA(0,0,0,.5f));
                renderer.drawWithShadow(matrices,element.getText(),window.coord.x1 + 2,window.coord.y1 + window.titleBarHeight + (10 * index) + 2, RGBtoInt(element.colorRGB));
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
                         ArrayList<GUIElement> elements
    ){};
    public static class Coordinates {
        private int width;
        private int height;

        private int x1;
        private int y1;


        public Coordinates(int x1, int y1, int width, int height) {
            this.x1 = x1;
            this.y1 = y1;
            this.width = width;
            this.height = height;

        }

        public void setX1(int x1) {
            this.x1 = x1;
        }
        public void setY1(int y1) {
            this.y1 = y1;
        }
        public void setWidth(int width) {
            this.width = width;
        }
        public void setHeight(int height) {
            this.height = height;
        }

    }
}
