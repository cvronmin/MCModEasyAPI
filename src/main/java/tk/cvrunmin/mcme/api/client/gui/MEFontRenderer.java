package tk.cvrunmin.mcme.api.client.gui;

import org.lwjgl.opengl.GL11;

import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/**
 * This Font Renderer is not ready to use
 * @author cvrunmin
 */
@Deprecated
@SideOnly(Side.CLIENT)
public class MEFontRenderer extends FontRenderer{
    private static final ResourceLocation[] unicodePageLocations = new ResourceLocation[256];
    /** Array of width of all the characters in default.png */
    private int[] charWidth = new int[256];
    /** Array of the start/end column (in upper/lower nibble) for every glyph in the /font directory. */
    private byte[] glyphWidth = new byte[65536];
    /**
     * Array of RGB triplets defining the 16 standard chat colors followed by 16 darker version of the same colors for
     * drop shadows.
     */
    private int[] colorCode = new int[32];
    private final ResourceLocation locationFontTexture;
    /** The RenderEngine used to load and setup glyph textures. */
    private final TextureManager renderEngine;
    /** Current X coordinate at which to draw the next character. */
    private float posX;
    /** Current Y coordinate at which to draw the next character. */
    private float posY;
    /** Used to specify new red value for the current color. */
    private float red;
    /** Used to specify new blue value for the current color. */
    private float blue;
    /** Used to specify new green value for the current color. */
    private float green;
    /** Used to speify new alpha value for the current color. */
    private float alpha;
    /** Text color of the currently rendering string. */
    @SuppressWarnings("unused")
	private int textColor;
    /** Set if the "k" style (random) is active in currently rendering string */
    private boolean randomStyle;
    /** Set if the "l" style (bold) is active in currently rendering string */
    private boolean boldStyle;
    /** Set if the "o" style (italic) is active in currently rendering string */
    private boolean italicStyle;
    /** Set if the "n" style (underlined) is active in currently rendering string */
    private boolean underlineStyle;
    /** Set if the "m" style (strikethrough) is active in currently rendering string */
    private boolean strikethroughStyle;
    /** If true, the Unicode Bidirectional Algorithm should be run before rendering any string. */
    private boolean bidiFlag;
    /** If true, strings should be rendered with Unicode fonts instead of the default.png font */
    private boolean unicodeFlag;
	public MEFontRenderer(GameSettings gameSettings, ResourceLocation resourceLoc,
			TextureManager TextureMana, boolean par4) {
		super(gameSettings, resourceLoc, TextureMana, par4);
        this.locationFontTexture = resourceLoc;
        this.renderEngine = TextureMana;
        this.unicodeFlag = par4;
        TextureMana.bindTexture(this.locationFontTexture);
	}
    /**
     * Reset all style flag fields in the class to false; called at the start of string rendering
     */
    private void resetStyles()
    {
        this.randomStyle = false;
        this.boldStyle = false;
        this.italicStyle = false;
        this.underlineStyle = false;
        this.strikethroughStyle = false;
    }
    /**
     * Draws the specified string with a shadow.
     */
    public int drawStringWithShadow(String text, float x, float y, int color)
    {
        return this.drawString(text, x, y, color, true);
    }

    /**
     * Draws the specified string.
     */
    public int drawString(String text, int x, int y, int color)
    {
        return this.drawString(text, (float)x, (float)y, color, false);
    }

    /**
     * Draws the specified string. Args: string, x, y, color, dropShadow
     */
    public int drawString(String text, float x, float y, int color, boolean withShadow)
    {
        GlStateManager.enableAlpha();
        this.resetStyles();
        int j;

        if (withShadow)
        {
            j = this.renderString(text, x + 1.0F, y + 1.0F, color, true);
            j = Math.max(j, this.renderString(text, x, y, color, false));
        }
        else
        {
            j = this.renderString(text, x, y, color, false);
        }

        return j;
    }
    /**
     * Render single line string by setting GL color, current (posX,posY), and calling renderStringAtPos()
     */
    private int renderString(String text, float x, float y, int color, boolean p_180455_5_)
    {
        if (text == null)
        {
            return 0;
        }
        else
        {
            if (this.bidiFlag)
            {
                text = this.bidiReorder(text);
            }

            if ((color & -67108864) == 0)
            {
                color |= -16777216;
            }

            if (p_180455_5_)
            {
                color = (color & 16579836) >> 2 | color & -16777216;
            }

            this.red = (float)(color >> 16 & 255) / 255.0F;
            this.blue = (float)(color >> 8 & 255) / 255.0F;
            this.green = (float)(color & 255) / 255.0F;
            this.alpha = (float)(color >> 24 & 255) / 255.0F;
            GlStateManager.color(this.red, this.blue, this.green, this.alpha);
            this.posX = x;
            this.posY = y;
            this.renderStringAtPos(text, p_180455_5_);
            return (int)this.posX;
        }
    }
    /**
     * Draws the specified string with a shadow.
     * 絵画出有阴影的字串。
     */
    public int drawStringWithShadow(String text, float x, float y, int R, int G, int B, int Alpha)
    {
        return this.drawString(text, x, y, R, G, B, Alpha, true);
    }

    /**
     * Draws the specified string.
     * 絵画出字串。
     */
    public int drawString(String text, int x, int y, int R, int G, int B, int Alpha)
    {
        return this.drawString(text, (float)x, (float)y, R, G, B, Alpha, false);
    }
    /**
     * Draws the specified string. Args: string, x, y, Red, Green, Blue, Alpha, dropShadow
     * 絵画出字串。参数：字串、x坐标、y坐标、红色、緵色、蓝色、透明度、是否阴影
     */
    public int drawString(String text, float x, float y, int R, int G, int B, int Alpha, boolean withShadow)
    {
        GlStateManager.enableAlpha();
        this.resetStyles();
        int j;

        if (withShadow)
        {
            j = this.renderString(text, x + 1.0F, y + 1.0F, R < 10 ? 0 : R - 10, G < 10 ? 0 : G - 10, B < 10 ? 0 : B - 10, Alpha, true);
            j = Math.max(j, this.renderString(text, x, y, R, G, B, Alpha, false));
        }
        else
        {
            j = this.renderString(text, x, y, R, G, B, Alpha, false);
        }

        return j;
    }
    /**
     * Render single line string by setting RGB color, current (posX,posY), and calling renderStringAtPos()
     * 用RGB颜色，坐标(x坐标、y坐标)，以及呼叫renderStringAtPos去渲染单行字串
     */
    private int renderString(String text, float x, float y, int R, int G, int B, int Alpha, boolean p_180455_5_)
    {
        if (text == null)
        {
            return 0;
        }
        else
        {
            if (this.bidiFlag)
            {
                text = this.bidiReorder(text);
            }
            
            this.red = R / 255.0F;
            this.blue = B / 255.0F;
            this.green = G / 255.0F;
            this.alpha = Alpha / 255.0F;
            GlStateManager.color(this.red, this.blue, this.green, this.alpha);
            this.posX = x;
            this.posY = y;
            this.renderStringAtPos(text, p_180455_5_);
            return (int)this.posX;
        }
    }
    /**
     * Apply Unicode Bidirectional Algorithm to string and return a new possibly reordered string for visual rendering.
     */
    private String bidiReorder(String p_147647_1_)
    {
        try
        {
            Bidi bidi = new Bidi((new ArabicShaping(8)).shape(p_147647_1_), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        }
        catch (ArabicShapingException arabicshapingexception)
        {
            return p_147647_1_;
        }
    }
    /**
     * Render a single line string at the current (posX,posY) and update posX
     */
    private void renderStringAtPos(String p_78255_1_, boolean p_78255_2_)
    {
        for (int i = 0; i < p_78255_1_.length(); ++i)
        {
            char c0 = p_78255_1_.charAt(i);
            int j;
            int k;

            if (c0 == 167 && i + 1 < p_78255_1_.length())
            {
                j = "0123456789abcdefklmnor".indexOf(p_78255_1_.toLowerCase().charAt(i + 1));

                if (j < 16)
                {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;

                    if (j < 0 || j > 15)
                    {
                        j = 15;
                    }

                    if (p_78255_2_)
                    {
                        j += 16;
                    }

                    k = this.colorCode[j];
                    this.textColor = k;
                    GlStateManager.color((float)(k >> 16) / 255.0F, (float)(k >> 8 & 255) / 255.0F, (float)(k & 255) / 255.0F, this.alpha);
                }
                else if (j == 16)
                {
                    this.randomStyle = true;
                }
                else if (j == 17)
                {
                    this.boldStyle = true;
                }
                else if (j == 18)
                {
                    this.strikethroughStyle = true;
                }
                else if (j == 19)
                {
                    this.underlineStyle = true;
                }
                else if (j == 20)
                {
                    this.italicStyle = true;
                }
                else if (j == 21)
                {
                    this.randomStyle = false;
                    this.boldStyle = false;
                    this.strikethroughStyle = false;
                    this.underlineStyle = false;
                    this.italicStyle = false;
                    GlStateManager.color(this.red, this.blue, this.green, this.alpha);
                }

                ++i;
            }
            else
            {
                j = "\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(c0);

                if (this.randomStyle && j != -1)
                {
                    do
                    {
                        k = this.fontRandom.nextInt(this.charWidth.length);
                    }
                    while (this.charWidth[j] != this.charWidth[k]);

                    j = k;
                }

                float f1 = this.unicodeFlag ? 0.5F : 1.0F;
                boolean flag1 = (c0 == 0 || j == -1 || this.unicodeFlag) && p_78255_2_;

                if (flag1)
                {
                    this.posX -= f1;
                    this.posY -= f1;
                }

                float f = this.renderCharAtPos(j, c0, this.italicStyle);

                if (flag1)
                {
                    this.posX += f1;
                    this.posY += f1;
                }

                if (this.boldStyle)
                {
                    this.posX += f1;

                    if (flag1)
                    {
                        this.posX -= f1;
                        this.posY -= f1;
                    }

                    this.renderCharAtPos(j, c0, this.italicStyle);
                    this.posX -= f1;

                    if (flag1)
                    {
                        this.posX += f1;
                        this.posY += f1;
                    }

                    ++f;
                }

                Tessellator tessellator;
                WorldRenderer worldrenderer;

                if (this.strikethroughStyle)
                {
                    tessellator = Tessellator.getInstance();
                    worldrenderer = tessellator.getWorldRenderer();
                    GlStateManager.disableTexture2D();
                    worldrenderer.startDrawingQuads();
                    worldrenderer.addVertex((double)this.posX, (double)(this.posY + (float)(this.FONT_HEIGHT / 2)), 0.0D);
                    worldrenderer.addVertex((double)(this.posX + f), (double)(this.posY + (float)(this.FONT_HEIGHT / 2)), 0.0D);
                    worldrenderer.addVertex((double)(this.posX + f), (double)(this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0F), 0.0D);
                    worldrenderer.addVertex((double)this.posX, (double)(this.posY + (float)(this.FONT_HEIGHT / 2) - 1.0F), 0.0D);
                    tessellator.draw();
                    GlStateManager.enableTexture2D();
                }

                if (this.underlineStyle)
                {
                    tessellator = Tessellator.getInstance();
                    worldrenderer = tessellator.getWorldRenderer();
                    GlStateManager.disableTexture2D();
                    worldrenderer.startDrawingQuads();
                    int l = this.underlineStyle ? -1 : 0;
                    worldrenderer.addVertex((double)(this.posX + (float)l), (double)(this.posY + (float)this.FONT_HEIGHT), 0.0D);
                    worldrenderer.addVertex((double)(this.posX + f), (double)(this.posY + (float)this.FONT_HEIGHT), 0.0D);
                    worldrenderer.addVertex((double)(this.posX + f), (double)(this.posY + (float)this.FONT_HEIGHT - 1.0F), 0.0D);
                    worldrenderer.addVertex((double)(this.posX + (float)l), (double)(this.posY + (float)this.FONT_HEIGHT - 1.0F), 0.0D);
                    tessellator.draw();
                    GlStateManager.enableTexture2D();
                }

                this.posX += (float)((int)f);
            }
        }
    }
    /**
     * Pick how to render a single character and return the width used.
     */
    private float renderCharAtPos(int p_78278_1_, char p_78278_2_, boolean p_78278_3_)
    {
        return p_78278_2_ == 32 ? 4.0F : ("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000".indexOf(p_78278_2_) != -1 && !this.unicodeFlag ? this.renderDefaultChar(p_78278_1_, p_78278_3_) : this.renderUnicodeChar(p_78278_2_, p_78278_3_));
    }
    /**
     * Render a single character with the default.png font at current (posX,posY) location...
     */
    protected float renderDefaultChar(int p_78266_1_, boolean p_78266_2_)
    {
        float f = (float)(p_78266_1_ % 16 * 8);
        float f1 = (float)(p_78266_1_ / 16 * 8);
        float f2 = p_78266_2_ ? 1.0F : 0.0F;
        this.renderEngine.bindTexture(this.locationFontTexture);
        float f3 = (float)this.charWidth[p_78266_1_] - 0.01F;
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        GL11.glTexCoord2f(f / 128.0F, f1 / 128.0F);
        GL11.glVertex3f(this.posX + f2, this.posY, 0.0F);
        GL11.glTexCoord2f(f / 128.0F, (f1 + 7.99F) / 128.0F);
        GL11.glVertex3f(this.posX - f2, this.posY + 7.99F, 0.0F);
        GL11.glTexCoord2f((f + f3 - 1.0F) / 128.0F, f1 / 128.0F);
        GL11.glVertex3f(this.posX + f3 - 1.0F + f2, this.posY, 0.0F);
        GL11.glTexCoord2f((f + f3 - 1.0F) / 128.0F, (f1 + 7.99F) / 128.0F);
        GL11.glVertex3f(this.posX + f3 - 1.0F - f2, this.posY + 7.99F, 0.0F);
        GL11.glEnd();
        return (float)this.charWidth[p_78266_1_];
    }
    /**
     * Render a single Unicode character at current (posX,posY) location using one of the /font/glyph_XX.png files...
     */
    protected float renderUnicodeChar(char p_78277_1_, boolean p_78277_2_)
    {
        if (this.glyphWidth[p_78277_1_] == 0)
        {
            return 0.0F;
        }
        else
        {
            int i = p_78277_1_ / 256;
            this.loadGlyphTexture(i);
            int j = this.glyphWidth[p_78277_1_] >>> 4;
            int k = this.glyphWidth[p_78277_1_] & 15;
            float f = (float)j;
            float f1 = (float)(k + 1);
            float f2 = (float)(p_78277_1_ % 16 * 16) + f;
            float f3 = (float)((p_78277_1_ & 255) / 16 * 16);
            float f4 = f1 - f - 0.02F;
            float f5 = p_78277_2_ ? 1.0F : 0.0F;
            GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
            GL11.glTexCoord2f(f2 / 256.0F, f3 / 256.0F);
            GL11.glVertex3f(this.posX + f5, this.posY, 0.0F);
            GL11.glTexCoord2f(f2 / 256.0F, (f3 + 15.98F) / 256.0F);
            GL11.glVertex3f(this.posX - f5, this.posY + 7.99F, 0.0F);
            GL11.glTexCoord2f((f2 + f4) / 256.0F, f3 / 256.0F);
            GL11.glVertex3f(this.posX + f4 / 2.0F + f5, this.posY, 0.0F);
            GL11.glTexCoord2f((f2 + f4) / 256.0F, (f3 + 15.98F) / 256.0F);
            GL11.glVertex3f(this.posX + f4 / 2.0F - f5, this.posY + 7.99F, 0.0F);
            GL11.glEnd();
            return (f1 - f) / 2.0F + 1.0F;
        }
    }
    /**
     * Load one of the /font/glyph_XX.png into a new GL texture and store the texture ID in glyphTextureName array.
     */
    private void loadGlyphTexture(int p_78257_1_)
    {
        this.renderEngine.bindTexture(this.getUnicodePageLocation(p_78257_1_));
    }
    private ResourceLocation getUnicodePageLocation(int p_111271_1_)
    {
        if (unicodePageLocations[p_111271_1_] == null)
        {
            unicodePageLocations[p_111271_1_] = new ResourceLocation(String.format("textures/font/unicode_page_%02x.png", new Object[] {Integer.valueOf(p_111271_1_)}));
        }

        return unicodePageLocations[p_111271_1_];
    }
}
