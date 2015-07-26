package tk.cvrunmin.mcme.api.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MEBlock extends Block {
	private String registeredName;
	protected boolean anti_entity;
	protected Random rand = new Random();
	protected MEBlock(Material materialIn) {
		super(materialIn);
	}
	public String getRegisteredName() {
		String name = null;
		if(registeredName != null){
		 name = registeredName;
		}
		else{
			name = "block_" + Integer.toString(rand.nextInt(256));
		}
		return name;
	}
	public MEBlock setRegisteredName(String registerName) {
		this.registeredName = registerName;
		return this;
	}
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
    	if(anti_entity){
    	    entityIn.attackEntityFrom(DamageSource.generic, rand.nextInt());
    	}
    }
    protected void openGui(Object mod,int guiid, World worldIn, BlockPos pos, EntityPlayer playerIn){
        playerIn.openGui(mod, guiid, worldIn, pos.getX(), pos.getY(), pos.getZ());
    }
}
