package tk.cvrunmin.mcme.api.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
/**
 * 中繼檔
 * @author cvrunmin
 *
 */
public class MEGuiIngame extends GuiIngame{
    public MEGuiIngame(Minecraft mcIn) {
		super(mcIn);
	}
	/**
     * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
     * <br>
     * 绘画一个有特定坐标和顏色的實层长方形。 参数 : 开始x坐标, 开始y坐标, 结束x坐标, 结束y坐标, 頂层顏色(RGBA), 底层顏色(RGBA)
     */
    public static void drawRect(int x1, int y1, int x2, int y2, int R, int G, int B, int A)
    {
        int j1;

        if (x1 < x2)
        {
            j1 = x1;
            x1 = x2;
            x2 = j1;
        }

        if (y1 < y2)
        {
            j1 = y1;
            y1 = y2;
            y2 = j1;
        }
        
        float f3 = A / 255.0F;
        float f = R / 255.0F;
        float f1 = G / 255.0F;
        float f2 = B / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(f, f1, f2, f3);
        worldrenderer.startDrawingQuads();
        worldrenderer.addVertex((double)x1, (double)y2, 0.0D);
        worldrenderer.addVertex((double)x2, (double)y2, 0.0D);
        worldrenderer.addVertex((double)x2, (double)y1, 0.0D);
        worldrenderer.addVertex((double)x1, (double)y1, 0.0D);
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    /**
     * Draws a rectangle with a vertical gradient between the specified colors (RGBA format). Args : x1, y1, x2, y2,
     * topColor, bottomColor
     * <br>
     * 绘画一个有垂直漸层的长方形。 参数 : 开始x坐标, 开始y坐标, 结束x坐标, 结束y坐标, 頂层顏色(RGBA), 底层顏色(RGBA)
     */
    protected void drawGradientRect(int left, int top, int right, int bottom, 
    		int sR, int sG, int sB, int sA,
    		int eR, int eG, int eB, int eA)
    {
        float f = sA / 255.0F;
        float f1 = sR / 255.0F;
        float f2 = sG / 255.0F;
        float f3 = sB / 255.0F;
        float f4 = eA / 255.0F;
        float f5 = eR / 255.0F;
        float f6 = eG / 255.0F;
        float f7 = eB / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.startDrawingQuads();
        worldrenderer.setColorRGBA_F(f1, f2, f3, f);
        worldrenderer.addVertex((double)right, (double)top, (double)this.zLevel);
        worldrenderer.addVertex((double)left, (double)top, (double)this.zLevel);
        worldrenderer.setColorRGBA_F(f5, f6, f7, f4);
        worldrenderer.addVertex((double)left, (double)bottom, (double)this.zLevel);
        worldrenderer.addVertex((double)right, (double)bottom, (double)this.zLevel);
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    /**
     * Renders the specified text to the screen, center-aligned. Args : renderer, string, x, y, color
     * <br>
     * 渲染特定的置中字串到介面。参数：渲染器, 字串, x坐标, y坐标, 颜色(RGBA)
     */
    public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int R, int G, int B, int Alpha)
    {
    	int color;
    	int Red = R << 16;
    	int Blue = B << 8;
    	int Green = G;
    	int Alpha1 = Alpha << 24;
    	color = Alpha1 + Red + Blue + Green;
        fontRendererIn.drawStringWithShadow(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color);
    }

    /**
     * Renders the specified text to the screen. Args : renderer, string, x, y, color
     *<br>
     * 渲染特定的字串到介面。参数：渲染器, 字串, x坐标, y坐标, 颜色(RGBA)
     */
    public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int R, int G, int B, int Alpha)
    {
    	int color;
    	int Red = R << 16;
    	int Blue = B << 8;
    	int Green = G;
    	int Alpha1 = Alpha << 24;
    	color = Alpha1 + Red + Blue + Green;
        fontRendererIn.drawStringWithShadow(text, (float)x, (float)y, color);
    }
}
