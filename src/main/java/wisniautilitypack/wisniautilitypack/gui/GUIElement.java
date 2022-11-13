package wisniautilitypack.wisniautilitypack.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import wisniautilitypack.wisniautilitypack.modules.Module;
import wisniautilitypack.wisniautilitypack.utils.Colors;

public class GUIElement {
    public ElementType type;
    public int width;
    public int height;
    public Module module;
    public Colors.ColorRGB colorRGB = new Colors.ColorRGB(1,1,1);

    public GUIElement(ElementType type) {
        this.type = type;
    }

    public String getText() {
        return "";
    }

    public Colors.ColorRGB getColorRGB(){
        return colorRGB;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public Module getModule() {
        return module;
    }
    public void onClick(){

    }
    public enum ElementType{
        TEXTFIELD,TEXT,MODULETOGGLE,BUTTON
    }
    public static class GUIElementText extends GUIElement {
        public String text_content;

        public GUIElementText(String contents) {
            super(ElementType.TEXT);
            this.text_content = contents;
        }
        public GUIElementText(String contents, Colors.ColorRGB colorRGB) {
            super(ElementType.TEXT);
            this.text_content = contents;
            this.colorRGB = colorRGB;
        }
        @Override
        public String getText() {
            return text_content;
        }

        @Override
        public int getWidth(){
            TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
            return renderer.getWidth(text_content);
        }
    }
    public static class GUIElementModuleToggle extends GUIElement {
        public String text_content;
        public Module module;
        public GUIElementModuleToggle(String contents, Module module) {
            super(ElementType.MODULETOGGLE);
            this.text_content = contents;
            this.module = module;
        }
        public GUIElementModuleToggle(String contents, Colors.ColorRGB colorRGB) {
            super(ElementType.MODULETOGGLE);
            this.text_content = contents;
            this.colorRGB = colorRGB;
        }
        @Override
        public String getText() {
            return text_content;
        }

        @Override
        public int getWidth(){
            TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
            return renderer.getWidth(text_content);
        }

        @Override
        public Module getModule() {
            return module;
        }
    }
}