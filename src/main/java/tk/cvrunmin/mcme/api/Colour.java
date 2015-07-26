package tk.cvrunmin.mcme.api;


/**
 * @author cvrunmin
 *
 */
public class Colour {

	public static int[] toARGB(int mixed){
		int a = (mixed >> 24) & 255;
    	int r = (mixed >> 16) & 255;
    	int g = (mixed >> 8) & 255;
    	int b = (mixed) & 255;
    	int[] argb = new int[]{a, r, g, b};
    	return argb;
	}
	
	public static int fromARGB(int a, int r, int g, int b){
		int a1 = (a & 255) << 24;
    	int r1 = (r & 255) << 16;
    	int g1 = (g & 255) << 8;
    	int b1 = (b & 255);
    	return a1 + r1 + g1 + b1;
	}
	
	public static int fromARGB(int[] argb){
		int a1 = (argb[0] & 255) << 24;
    	int r1 = (argb[1] & 255) << 16;
    	int g1 = (argb[2] & 255) << 8;
    	int b1 = (argb[3] & 255);
    	return a1 + r1 + g1 + b1;
	}
	
	public static int[] toRGB(int mixed){
    	int r = (mixed >> 16) & 255;
    	int g = (mixed >> 8) & 255;
    	int b = (mixed) & 255;
    	int[] rgb = new int[]{r, g, b};
    	return rgb;
	}
	
	public static int fromRGB(int r, int g, int b){
    	int r1 = (r & 255) << 16;
    	int g1 = (g & 255) << 8;
    	int b1 = (b & 255);
    	return r1 + g1 + b1;
	}
	
	public static int fromRGB(int[] argb){
    	int r1 = (argb[0] & 255) << 16;
    	int g1 = (argb[1] & 255) << 8;
    	int b1 = (argb[2] & 255);
    	return r1 + g1 + b1;
	}
	
	public static float colorPercentage(int color){
		return color / 255.0F;
	}
	public static float colorHeld(float percentage){
		return percentage * 255.0F;
	}
	
	public static int getColorFromBits(int src, int pos, int count){
		int tmp1 = src >> pos;
    	int tmp2 = Integer.valueOf(Integer.toBinaryString(Integer.MAX_VALUE).substring(0, count), 2);
    	int tmp3 = tmp1 & tmp2;
    	return tmp3;
	}
}
