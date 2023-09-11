package wisniautilitypack.wisniautilitypack.modules.AUTO.AutoVillagerChecking;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.LecternBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.network.listener.PacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.SetTradeOffersS2CPacket;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.VillagerProfession;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wisniautilitypack.wisniautilitypack.modules.Module;

import static wisniautilitypack.wisniautilitypack.client.WisniaUtilityPackClient.inWorld;

public class AutoVillagerMain extends Module {
    public static int tickCounterGuiClear;
    private static int keybindCounter;
    private static KeyBinding keyBinding;
    public static boolean enabled;
    private static MinecraftClient client;
    public static VillagerCheckingState current_state;
    private static VillagerEntity librarian;
    private static BlockPos lecternPos;
    public static boolean autoSearch = false;

    public AutoVillagerMain(String name, String desc, String mode,ModuleCategory category) {
        super(name,desc,mode,category);
    }
    public boolean getEnabled(){
        return enabled;
    }
    public void setEnabled(boolean state){
        enabled = state;
        changeEnable();
    }

    public String getMode(){
        return (autoSearch) ? "AUTO" : "";
    }
    public static <T extends PacketListener> void handlePacket(Packet<T> packet, PacketListener listener, CallbackInfo ci){
        if(current_state == VillagerCheckingState.WAIT_FOR_TRADE_OFFER && enabled){
            if (packet instanceof SetTradeOffersS2CPacket){
                SetTradeOffersS2CPacket pkt = (SetTradeOffersS2CPacket) packet;

                TradeOfferList listOfOffers =  pkt.getOffers();

                new offersRenderer().renderNewOffers(listOfOffers);
                current_state = VillagerCheckingState.GOT_OFFER;
                ci.cancel();
                return;
            }

            if (packet instanceof OpenScreenS2CPacket){
                OpenScreenS2CPacket pkt = (OpenScreenS2CPacket) packet;
                if(pkt.getScreenHandlerType().equals(ScreenHandlerType.MERCHANT)){
                    ci.cancel();
                    return;
                }
            }
        }
    }
    public void changeEnable(){
        current_state = (enabled) ? VillagerCheckingState.INIT:VillagerCheckingState.NONE;
        MinecraftClient.getInstance().player.sendMessage(Text.of("Villager Switching is now " + ((enabled) ? "enabled":"disabled") ), true);
    }
    public void init(){
        client = MinecraftClient.getInstance();
        ServerWorldEvents.LOAD.register(((server, world) -> HudRenderCallback.EVENT.register((context, tickDelta) -> {
            TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
            int h = MinecraftClient.getInstance().getWindow().getScaledHeight();
            int w = MinecraftClient.getInstance().getWindow().getScaledWidth();
            context.drawText(renderer, offersRenderer.toShow, w - renderer.getWidth(offersRenderer.toShow) - 25, h - 25, offersRenderer.toShowColor,true);
            context.drawText(renderer, offersRenderer.price, w - renderer.getWidth(offersRenderer.price) - 25, h - 10, offersRenderer.toShowColor,true);
        })));

        ClientTickEvents.END_CLIENT_TICK.register((e) -> {
            if (inWorld) {
                if (tickCounterGuiClear >= 200){
                    offersRenderer.toShow = ""; // Clear trades text after 10 seconds (200 ticks) // TODO: config 200 ticks or 0 to never
                    offersRenderer.price = "";
                    tickCounterGuiClear = 0;
                }
                if (keybindCounter >= 5){
                    if(keyBinding.isPressed()){
                        enabled = !enabled;
                        changeEnable();
                        keybindCounter = 0;
                    }
                }
                tickCounterGuiClear++;
                keybindCounter++;

                if(current_state == VillagerCheckingState.INIT){
                    ClientPlayerEntity player = client.player;
                    Vec3d playerPos = player.getPos();
                    double closestDistance = Double.POSITIVE_INFINITY;
                    Entity closestEntity = null;

                    for (Entity entity : client.world.getEntities()){
                        Vec3d entityPos = entity.getPos();
                        if (entity instanceof VillagerEntity && entityPos.distanceTo(playerPos) < closestDistance){
                            closestDistance = entityPos.distanceTo(playerPos);
                            closestEntity = entity;
                        }
                    }

                    VillagerEntity villager = (VillagerEntity) closestEntity;
                    if(villager != null ){
                        if(villager.getVillagerData().getProfession() == VillagerProfession.LIBRARIAN){
                            librarian = villager;
                            current_state = VillagerCheckingState.WAIT_FOR_TRADE_OFFER;
                            if(client.interactionManager != null){
                                client.interactionManager.interactEntity(player,closestEntity, Hand.MAIN_HAND);
                            }
                        }
                    }
                }else if (current_state == VillagerCheckingState.GOT_OFFER){
                    lecternPos = null;
                    if(client.player != null){
                        double closestLibraryDistance = Double.POSITIVE_INFINITY;
                        for (int y = -1; y <= 0; y++){
                            for (int x = -3; x <= 3; x++){
                                for (int z = -3; z <= 3; z++){
                                    BlockPos pos = client.player.getBlockPos().add(x,y,z);
                                    BlockState blockState = client.world.getBlockState(pos);

                                    if (blockState.getBlock() instanceof LecternBlock){
                                        Vec3d playerPos = client.player.getPos();
                                        if(playerPos.distanceTo(new Vec3d(pos.getX(),pos.getY(),pos.getZ())) < closestLibraryDistance) {
                                            System.out.println("Found new closest library Z: " + pos.getZ());
                                            closestLibraryDistance = playerPos.distanceTo(new Vec3d(pos.getX(),pos.getY(),pos.getZ()));
                                            lecternPos = pos;
                                            if (autoSearch) {
                                                if (offersRenderer.enchantClass == EnchantClass.GOOD) {
                                                    offersRenderer.toShow = "Got a good offer: " + offersRenderer.enchantName + " " + offersRenderer.enchantLevel;
                                                    offersRenderer.toShowColor = 0x61ff33;
                                                    current_state = VillagerCheckingState.NONE;
                                                    this.setEnabled(false);
                                                    MinecraftClient.getInstance().player.sendMessage(Text.of("Villager Switching is now " + ((enabled) ? "enabled":"disabled") ), true);
                                                } else {
                                                    current_state = VillagerCheckingState.AUTOLOOP;
                                                    client.interactionManager.attackBlock(lecternPos,Direction.UP);
                                                }
                                            } else {
                                                current_state = VillagerCheckingState.WAIT_FOR_BLOCK_BREAK;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (closestLibraryDistance == Double.POSITIVE_INFINITY) {
                            current_state = VillagerCheckingState.INIT;
                        }
                    }
                }else if(current_state == VillagerCheckingState.WAIT_FOR_BLOCK_BREAK){
                    if(librarian != null && librarian.getVillagerData().getProfession() != VillagerProfession.LIBRARIAN){
                        offersRenderer.toShow = "Waiting for offers";
                        offersRenderer.price = "Price: None";
                        offersRenderer.toShowColor =  0xfffc3d;

                        BlockHitResult hitResult = new BlockHitResult(new Vec3d(lecternPos.getX(),lecternPos.getY(),lecternPos.getZ()), Direction.UP,lecternPos,false);
                        client.interactionManager.interactBlock(client.player,Hand.OFF_HAND,hitResult);
                        current_state = VillagerCheckingState.INIT;
                    }
                    if (librarian == null){
                        current_state = VillagerCheckingState.INIT;
                    }
                }else if (current_state == VillagerCheckingState.AUTOLOOP){
                    BlockState blockState = client.world.getBlockState(lecternPos);
                    if (blockState.getBlock() instanceof LecternBlock) {
                        client.interactionManager.updateBlockBreakingProgress(lecternPos,Direction.UP);
                    }else{
                        //client.options.attackKey.setPressed(false);
                        current_state = VillagerCheckingState.WAIT_FOR_BLOCK_BREAK;
                    }
                }
            }
        });
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Auto Villager Checking toggle", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_R, // The keycode of the key
                "Wisniaq Utility Pack" // The translation key of the keybinding's category.
        ));
    }
}
