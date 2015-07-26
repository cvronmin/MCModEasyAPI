package tk.cvrunmin.mcme.api.item.crafting;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
public interface IMERecipe {
        boolean matches(IInventory inventory);
        
        int getRecipeSize();
        
        ItemStack getRecipeOutput();
}
