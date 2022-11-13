package wisniautilitypack.wisniautilitypack.modules;

public abstract class Module {
    public String name;
    public String description;
    public String mode;
    public boolean enabled;
    public ModuleCategory category;

    public abstract void init();
    public abstract boolean getEnabled();
    public abstract void setEnabled(boolean state);

    public abstract String  getMode();
    public Module(String name, String description, String mode,ModuleCategory category) {
        this.name = name;
        this.description = description;
        this.mode = mode;
        this.category = category;
    }
    public enum ModuleCategory{
        RENDER,AUTO
    }
}
