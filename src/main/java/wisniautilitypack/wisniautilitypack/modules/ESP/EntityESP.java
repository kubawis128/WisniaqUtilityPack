package wisniautilitypack.wisniautilitypack.modules.ESP;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.*;
import org.lwjgl.glfw.GLFW;
import wisniautilitypack.wisniautilitypack.modules.Module;
import wisniautilitypack.wisniautilitypack.utils.Colors;

import java.util.HashMap;

import static net.minecraft.util.math.MathHelper.clamp;
import static wisniautilitypack.wisniautilitypack.client.WisniaUtilityPackClient.inWorld;
import static wisniautilitypack.wisniautilitypack.modules.ESP.EntityTypes.getEntityType;
import static wisniautilitypack.wisniautilitypack.utils.Renderer.*;

public class EntityESP extends Module {
    private static KeyBinding keyBinding;
    private static int keybindCounter;

    public static boolean enabled;
    public static HashMap<EntityTypes.EntityType, Colors.ColorRGBA> colorRGBAMap = new HashMap<>();

    public EntityESP(String name, String desc, String mode,ModuleCategory category) {
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

    public void init() {

        ClientTickEvents.END_CLIENT_TICK.register((e) -> {
            if (inWorld) {
                if (keybindCounter >= 5) {
                    if (keyBinding.isPressed()) {
                        enabled = !enabled;
                        MinecraftClient.getInstance().player.sendMessage(Text.of("Entity ESP is now " + ((enabled) ? "enabled" : "disabled")), true);
                        keybindCounter = 0;
                    }
                }
                keybindCounter++;
            }
        });
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Entity ESP toggle", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_J, // The keycode of the key
                "Wisniaq Utility Pack" // The translation key of the keybinding's category.
        ));

        colorRGBAMap.put(EntityTypes.EntityType.OTHER, new Colors.ColorRGBA(0,0,0,0.5f));
        colorRGBAMap.put(EntityTypes.EntityType.BOSS, new Colors.ColorRGBA(1,0,0,0.9f));
        colorRGBAMap.put(EntityTypes.EntityType.ARMOR_STAND, new Colors.ColorRGBA(1,1,0,0.75f));
        colorRGBAMap.put(EntityTypes.EntityType.VILLAGER, new Colors.ColorRGBA(1,0,1,0.75f));
        colorRGBAMap.put(EntityTypes.EntityType.ANIMAL_HOSTILE, new Colors.ColorRGBA(1,0,0,0.5f));
        colorRGBAMap.put(EntityTypes.EntityType.ANIMAL_NEUTRAL, new Colors.ColorRGBA(1,1,0,0.5f));
        colorRGBAMap.put(EntityTypes.EntityType.ANIMAL_PASSIVE, new Colors.ColorRGBA(1,0,0,0.5f));

    }

    public static void draw(MatrixStack matrices,Camera cam){
        if (!enabled) {
            return;
        }
        MinecraftClient client = MinecraftClient.getInstance();
        drawText(matrices,cam,0,0,0, new Colors.ColorRGBA(1.0f,1.0f,1.0f,1.0f));
        if(client.player != null) {
            for (Entity ent: client.world.getEntities()) {
                if (ent instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) ent;

                    int health = (int)livingEntity.getHealth();

                    MutableText formattedHealth = Text.literal(ent.getType().getName().getString() + " ")
                            .append(Integer.toString(health)).formatted(getColor(health));
                    ent.setCustomName(formattedHealth);
                }else{
                    ent.setCustomName(Text.of(ent.getType().getName().getString()));

                }
                ent.setCustomNameVisible(true);
                Box box = ent.getBoundingBox();
                if(ent instanceof PlayerEntity){
                    if(!ent.getName().equals(MinecraftClient.getInstance().player.getName())){
                        drawBox(matrices, cam, box, new Colors.ColorRGBA(1.0f, 0.0f, 0.0f, 1.0f),true); // TODO: Add friends list
                        float camRotxy = MinecraftClient.getInstance().cameraEntity.getHeadYaw();
                        Vec2f camRot = MinecraftClient.getInstance().player.getRotationClient();
                        Vec3d camPos = MinecraftClient.getInstance().player.getPos();
                        double camRotRad = Math.toRadians(camRot.x);
                        double multiplier = Math.abs((Math.abs(Math.sin(camRotRad))/2)*2-1);

                        camPos = new Vec3d((Math.sin(Math.toRadians(camRotxy))*-2)*multiplier + camPos.x, camPos.y + (Math.abs(Math.sin(camRotRad)-1)*2), (Math.cos(Math.toRadians(camRotxy))*2)*multiplier + camPos.z);
                        Vec3d playerPos = ent.getPos();

                        float f = MinecraftClient.getInstance().player.distanceTo(ent) / 20F;
                        Colors.ColorRGB colorByDistance = new Colors.ColorRGB(clamp(2 - f, 0, 1),clamp(f, 0, 1),0);
                        drawLine2(matrices, cam,(float) camPos.x,(float) camPos.y,(float) camPos.z,(float) playerPos.x,(float) playerPos.y + 1.0f,(float) playerPos.z, Colors.RGBtoRGBA(colorByDistance,0.75f));
                    }
                }else{
                    drawBox(matrices, cam, box, colorRGBAMap.get(getEntityType(ent)),true);
                }
            }
        }
    }

    public static void render_return(MatrixStack matrices ,Camera cam){
        if (inWorld) {
            draw(matrices, cam);
        }
    }
    private static Formatting getColor(int health)
    {
        if(health <= 5)
            return Formatting.DARK_RED;

        if(health <= 10)
            return Formatting.GOLD;

        if(health <= 15)
            return Formatting.YELLOW;

        return Formatting.GREEN;
    }

}