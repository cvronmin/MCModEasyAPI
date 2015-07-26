package tk.cvrunmin.mcme.api.init;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import tk.cvrunmin.mcme.api.block.MEBlock;
import tk.cvrunmin.mcme.api.item.MEItem;

public class Init {
	protected static void registerBlock(MEBlock block)
	{
		GameRegistry.registerBlock(block, block.getRegisteredName());
	}
	protected static void registerBlock(Block block, String registerName)
	{
		GameRegistry.registerBlock(block, registerName);
	}
	protected static void registerItem(MEItem item)
	{
		GameRegistry.registerItem(item, item.getRegisteredName());
	}
	protected static void registerItem(Item item, String registerName)
	{
		GameRegistry.registerItem(item, registerName);
	}
}
