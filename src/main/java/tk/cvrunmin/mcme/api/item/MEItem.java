package tk.cvrunmin.mcme.api.item;

import java.util.Random;

import net.minecraft.item.Item;

public class MEItem extends Item {
	private String registeredName;
	private Random rand = new Random();

	public String getRegisteredName() {
		String name = null;
		if(registeredName != null){
		 name = registeredName;
		}
		else{
			name = "item_" + Integer.toString(rand.nextInt(256));
		}
		return name;
	}

	public MEItem setRegisteredName(String registeredName) {
		this.registeredName = registeredName;
		return this;
	}
}
