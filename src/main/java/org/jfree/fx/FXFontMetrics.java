/* ============
 * FXGraphics2D
 * ============
 * 
 * (C)opyright 2014-2022, by David Gilbert.
 * 
 * https://github.com/jfree/fxgraphics2d
 *
 * The FXGraphics2D class has been developed by David Gilbert for
 * use in Orson Charts (https://github.com/jfree/orson-charts) and
 * JFreeChart (http://www.jfree.org/jfreechart).  It may be useful for other
 * code that uses the Graphics2D API provided by Java2D.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of JFree.org nor the names of its contributors may
 *     be used to endorse or promote products derived from this software
 *     without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */

package org.jfree.fx;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.text.CharacterIterator;
import javafx.geometry.Bounds;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * A font metrics implementation for JavaFX.  This uses the JavaFX APIs to
 * get string bounds, which is more exact than relying on the Java2D/AWT
 * measurements.  The remaining font metrics, however, still come from 
 * Java2D/AWT.
 * 
 * @since 1.5
 */
public class FXFontMetrics extends FontMetrics {

    /** The graphics target. */
    private final Graphics2D g2;
    
    /**
     * Creates a new instance.
     * 
     * @param font  the font ({@code null} not permitted).
     * @param g2  the graphics target ({@code null} not permitted).
     */
    public FXFontMetrics(Font font, Graphics2D g2) {
        super(font);
        this.g2 = g2;
    }

    @Override
    public Rectangle2D getStringBounds(CharacterIterator ci, int beginIndex, 
            int limit, Graphics context) {
        char[]  arr = new char[limit - beginIndex];
        ci.setIndex(beginIndex);
        for(int idx = 0; idx < arr.length; idx++) {
            arr[idx] = ci.current();
            ci.next();
        }
        return getStringBounds(arr, beginIndex, limit, context);
    }

    @Override
    public Rectangle2D getStringBounds(char[] chars, int beginIndex, int limit, 
            Graphics context) {
        String str = new String(chars, beginIndex, limit - beginIndex);
        return getStringBounds(str, context);
    }

    @Override
    public Rectangle2D getStringBounds(String str, int beginIndex, int limit, 
            Graphics context) {
        String substr = str.substring(beginIndex, limit);
        return super.getStringBounds(substr, context);
    }

    @Override
    public Rectangle2D getStringBounds(String str, Graphics context) {
        Text text = new Text(str);
        FontWeight weight = font.isBold() ? FontWeight.BOLD : FontWeight.NORMAL;
        FontPosture posture = font.isItalic() 
                ? FontPosture.ITALIC : FontPosture.REGULAR;
        javafx.scene.text.Font jfxfont = javafx.scene.text.Font.font(
                font.getFamily(), weight, posture, font.getSize());
        text.setFont(jfxfont);
        Bounds b = text.getLayoutBounds();
        return new Rectangle2D.Double(b.getMinX(), b.getMinY(), b.getWidth(), 
                b.getHeight());
    }

    @Override
    public int stringWidth(String str) {
        return (int) getStringBounds(str, this.g2).getWidth();
    }
    
}
