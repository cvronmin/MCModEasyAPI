package tk.cvrunmin.mcme.api;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

public class MEHelper {
	public static ArmorMaterial newMaterial(String name, String textureName, int durability, int helmetReduction, int chestplateReduction, int leggingsReduction, int bootsReduction, int enchantability){
		int[] reductionAmounts = new int[]{helmetReduction, chestplateReduction, leggingsReduction, bootsReduction};
		ArmorMaterial armor = EnumHelper.addArmorMaterial(name, textureName, durability, reductionAmounts, enchantability);
		return armor;
	}
	public static CreativeTabs newTab(String label, final ItemStack iconItem){
		CreativeTabs tab = new CreativeTabs(label) {	
			@Override
			public Item getTabIconItem() {
				return iconItem.getItem();
			}
		};
		return tab;
	}
}
