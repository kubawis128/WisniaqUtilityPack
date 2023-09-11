package wisniautilitypack.wisniautilitypack.modules.COMBAT;

import wisniautilitypack.wisniautilitypack.modules.Module;

public class Criticals extends Module {
    public Criticals(String name, String description, String mode, ModuleCategory category) {
        super(name, description, mode, category);
    }

    @Override
    public void init() {

    }

    @Override
    public boolean getEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean state) {

    }

    @Override
    public String getMode() {
        return null;
    }
}
