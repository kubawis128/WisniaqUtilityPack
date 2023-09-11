package wisniautilitypack.wisniautilitypack.modules.AUTO.AutoVillagerChecking;

import java.util.ArrayList;

public class EnchantClassifier {
    public record Enchant(String enchantName, int level) {

    }
    public static ArrayList<Enchant> goodEnchantsList;
    public static ArrayList<Enchant> badEnchantsList;

    public static String oldGoodEnchantsString;

    public static EnchantClass classifyEnchant(String enchant, int lvl){
        if (badEnchantsList == null || badEnchantsList.size() == 0){
            badEnchantsList = new ArrayList<Enchant>();
            badEnchantsList.add(new Enchant("binding_curse",1));
            badEnchantsList.add(new Enchant("vanishing_curse",1));
        }
        if (goodEnchantsList == null || goodEnchantsList.size() == 0){
            goodEnchantsList = new ArrayList<Enchant>();
            goodEnchantsList.add(new Enchant("mending",1));
        }
        if(badEnchantsList.stream().anyMatch((e)-> enchant.toLowerCase().equals("enchantment.minecraft." + e.enchantName().toLowerCase()) && lvl >= e.level())){
            return EnchantClass.BAD;
        }else if (goodEnchantsList.stream().anyMatch((e)-> enchant.toLowerCase().equals("enchantment.minecraft." + e.enchantName().toLowerCase()) && lvl >= e.level())){
            return EnchantClass.GOOD;
        }
        return EnchantClass.NEUTRAL;
    }
}
