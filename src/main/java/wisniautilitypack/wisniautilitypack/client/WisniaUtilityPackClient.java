package wisniautilitypack.wisniautilitypack.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import wisniautilitypack.wisniautilitypack.gui.GUIElement;
import wisniautilitypack.wisniautilitypack.gui.WindowingSystem;
import wisniautilitypack.wisniautilitypack.modules.AutoFishing.AutoFishingMain;
import wisniautilitypack.wisniautilitypack.modules.AutoVillagerChecking.AutoVillagerMain;
import wisniautilitypack.wisniautilitypack.modules.ESP.ContainerESP;
import wisniautilitypack.wisniautilitypack.modules.ESP.EntityESP;
import wisniautilitypack.wisniautilitypack.modules.HUD.CheatList;
import wisniautilitypack.wisniautilitypack.modules.HUD.CheatsMenuHUD;
import wisniautilitypack.wisniautilitypack.modules.Module;
import wisniautilitypack.wisniautilitypack.modules.Utils.XrayMain;
import wisniautilitypack.wisniautilitypack.modules.Module.ModuleCategory;
import wisniautilitypack.wisniautilitypack.utils.Colors;
import wisniautilitypack.wisniautilitypack.utils.events.Event;
import wisniautilitypack.wisniautilitypack.utils.events.EventListener;
import wisniautilitypack.wisniautilitypack.utils.events.EventName;

import java.util.*;

@Environment(EnvType.CLIENT)
public class WisniaUtilityPackClient implements ClientModInitializer, EventListener {
    public static boolean inWorld;
    public static final List<Module> ModuleList = new ArrayList<>();
    public static Event initiater;
    @Override
    public void onInitializeClient() {
        initiater = new Event();

        ServerWorldEvents.LOAD.register(((server, world) -> inWorld = true));

        ClientLoginConnectionEvents.INIT.register(((server, world) -> inWorld = true));

        ServerWorldEvents.UNLOAD.register(((server, world) -> {
            inWorld = false;
            initiater.call(EventName.WORLD_UNLOAD);
        }));

        ClientLoginConnectionEvents.DISCONNECT.register(((server, world) -> {
            inWorld = false;
            initiater.call(EventName.WORLD_UNLOAD);
        }));

        AutoVillagerMain autoVillagerMain =  new AutoVillagerMain("Auto Villager Trades","Automatically cycles villager trades","", ModuleCategory.AUTO);
        ModuleList.add(autoVillagerMain);
        AutoFishingMain autoFishingMain = new AutoFishingMain("AutoFish","Automatically catches fish","", ModuleCategory.AUTO);
        ModuleList.add(autoFishingMain);
        XrayMain xrayMain = new XrayMain("X-Ray","Just plain old X-Ray","", ModuleCategory.RENDER);
        ModuleList.add(xrayMain);
        EntityESP entityESP = new EntityESP("EntityESP","Draws box over hit-boxes of entities","", ModuleCategory.RENDER);
        ModuleList.add(entityESP);
        ContainerESP containerESP = new ContainerESP("Chest ESP","Draws box over chest","", ModuleCategory.RENDER);
        ModuleList.add(containerESP);
        CheatList cheatList = new CheatList("Cheat List","Shows active modules on screen","", ModuleCategory.RENDER);
        ModuleList.add(cheatList);
        CheatsMenuHUD cheatsMenuHUD = new CheatsMenuHUD("menu","Opens conf menu","", ModuleCategory.RENDER);
        ModuleList.add(cheatsMenuHUD);

        int index = 0;
        for (ModuleCategory category: ModuleCategory.values()) {
            WindowingSystem.addWindow(new WindowingSystem.Window(
                    new WindowingSystem.Coordinates(60*index,0,50,100),10, category.name(),
                    new Colors.ColorRGBA(0,0,0,.4f),    // Body color
                    new Colors.ColorRGBA(0,0,0,.6f),    // Title Bar color
                    new Colors.ColorRGB(1f,1f,1f),    // Text color
                    new ArrayList<>()
            ));
            index++;
        }

        for (Module mod: ModuleList) {
            mod.init();
            WindowingSystem.addContent(mod.category,new GUIElement.GUIElementModuleToggle(mod.name,mod));
        }
    }

    @Override
    public void onEvent(EventName eventName) {
        // Ignore
    }
}
