package wisniautilitypack.wisniautilitypack.gui;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import net.minecraft.text.Text;
import wisniautilitypack.wisniautilitypack.modules.AUTO.AutoVillagerChecking.AutoVillagerMain;
import wisniautilitypack.wisniautilitypack.modules.AUTO.AutoVillagerChecking.EnchantClassifier.Enchant;
import wisniautilitypack.wisniautilitypack.modules.AUTO.AutoVillagerChecking.EnchantClassifier;

import java.util.ArrayList;

public class UtilityScreen extends LightweightGuiDescription {

    public UtilityScreen() {
        WGridPanel root = new WGridPanel();
        setRootPanel(root);
        root.setSize(256, 240);
        root.setInsets(Insets.ROOT_PANEL);

        WToggleButton toggleButton = new WToggleButton(Text.of("AutoSearch"));
        toggleButton.setToggle(AutoVillagerMain.autoSearch);
        toggleButton.setOnToggle(on -> AutoVillagerMain.autoSearch = !AutoVillagerMain.autoSearch);
        root.add(toggleButton, 0, 0, 2, 2);

        WTextField goodEnchants = new WTextField(Text.of("Good enchants"));
        goodEnchants.setMaxLength(512);
        System.out.println("oldGoodEnchantsString: " + EnchantClassifier.oldGoodEnchantsString);
        if (EnchantClassifier.oldGoodEnchantsString != null){
            goodEnchants.setText(EnchantClassifier.oldGoodEnchantsString);
            System.out.println("oldGoodEnchantsString: " + EnchantClassifier.oldGoodEnchantsString);
        }
        goodEnchants.setChangedListener((e) -> {
            EnchantClassifier.oldGoodEnchantsString = goodEnchants.getText();
            EnchantClassifier.goodEnchantsList = convertToEnchant(goodEnchants.getText().split(","));
        });
        root.add(goodEnchants, 0, 1, 20, 1);

        //WButton button = new WButton(new TranslatableText("Lorem Ipsum"));
        //root.add(button, 0, 4, 8, 2);

        //WLabel label = new WLabel(new LiteralText("Lorem Ipsum"), 0xFFFFFF);
        //root.add(label, 0, 8, 2, 2);

        root.validate(this);
    }
    public ArrayList<Enchant> convertToEnchant(String[] listOfEnchants){
        ArrayList<Enchant> convertedList = new ArrayList<Enchant>();
        for (String substring: listOfEnchants) {
            if(substring.split(";").length == 1){
                convertedList.add(new Enchant(substring,1));
            }else {
                if(substring.split(";")[1].matches("[0-9]")){
                    convertedList.add(new Enchant(substring.split(";")[0],Integer.parseInt(substring.split(";")[1])));
                }
            }
        }
        return convertedList;
    }
}
