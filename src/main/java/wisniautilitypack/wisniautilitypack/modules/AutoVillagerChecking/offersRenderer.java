package wisniautilitypack.wisniautilitypack.modules.AutoVillagerChecking;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

public class offersRenderer {
    public static String toShow = "";
    public static int toShowColor = 0xffffff;
    public static EnchantClass enchantClass;
    public static String enchantName;
    public static int enchantLevel;
    public void renderNewOffers(TradeOfferList listOfOffers) {
        toShowColor = 0xff3d3d;
        toShow = "Trade: Nothing";
        AutoVillagerMain.tickCounterGuiClear = 0;
        for (TradeOffer off : listOfOffers) { // for each trade offer

            Item sellItem = off.getSellItem().getItem();
            ItemStack sellItemStack = off.getSellItem();

            if (sellItem.getName().equals(Text.translatable("item.minecraft.enchanted_book"))) {

                JsonObject parsedNBT = JsonParser.parseString(sellItemStack.getNbt().get("StoredEnchantments").toString()).getAsJsonArray().get(0).getAsJsonObject();
                String enchant = parsedNBT.get("id").toString().replace("minecraft:", "enchantment.minecraft.").replace("\"", "");
                Text  enchantTranslated = Text.translatable(enchant);
                String lvl = parsedNBT.get("lvl").toString().substring(1,2).replace("1","I").replace("2","II").replace("3","III").replace("4","IV").replace("5","V"); // This is really stupid conversion from Arabic to Roman numerals but it works
                toShow = "Trade: " + enchantTranslated.getString() +  " " + lvl;
                enchantName = enchantTranslated.getString();
                enchantLevel = Integer.parseInt(parsedNBT.get("lvl").toString().substring(1,2));
                enchantClass = EnchantClassifier.classifyEnchant(enchant,Integer.parseInt(parsedNBT.get("lvl").toString().substring(1,2)));
                switch (enchantClass){
                    case GOOD -> {
                        toShowColor = 0x61ff33;
                        break;
                    }
                    case BAD -> {
                        toShowColor = 0xff3d3d;
                        break;
                    }
                    case NEUTRAL -> {
                        toShowColor = 0xffffff;
                        break;
                    }
                }
            }
        }
    }
}
