package wisniautilitypack.wisniautilitypack.modules.ESP;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class EntityTypes {
    public static List<String> animal_passive = List.of("axolotl","fox","cat","horse","pig","chicken","cow","parrot");
    public static List<String> animal_hostile = List.of("bee","llama","enderman","panda","goat","wolf","golem");

    public static List<String> animal_neutral = List.of("blaze","creeper","ghast","phantom","silverfish","skeleton","slime","witch","warden","guardian","stray","drowned","ravager","endermite","pillager");


    public enum EntityType {
        PLAYER,ANIMAL_PASSIVE,ANIMAL_NEUTRAL,ANIMAL_HOSTILE,VILLAGER,ARMOR_STAND,BOSS,OTHER
    }

    public static EntityType getEntityType(Entity ent){
        AtomicReference<EntityType> type = new AtomicReference<>();

        if(ent instanceof PlayerEntity){
            return EntityType.PLAYER;
        }

        String key = ent.getType().getTranslationKey();
        animal_passive.forEach((each) -> {
            if(key.contains(each)){
                type.set(EntityType.ANIMAL_PASSIVE);
            }
        });

        animal_neutral.forEach((each) -> {
            if(key.contains(each)){
                type.set(EntityType.ANIMAL_NEUTRAL);
            }
        });

        animal_hostile.forEach((each) -> {
            if(each.contains(key)){
                type.set(EntityType.ANIMAL_HOSTILE);
            }
        });

        if(key.contains("villager")){
            return EntityType.VILLAGER;
        }

        if(key.contains("armor_stand")){
            return EntityType.ARMOR_STAND;
        }
        if(key.contains("dragon") || key.equals("entity.minecraft.wither")){
            return EntityType.BOSS;
        }
        if(type.get() != null){
            return type.get();
        }
        return EntityType.OTHER;
    }
}