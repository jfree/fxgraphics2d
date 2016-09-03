/* ============
 * FXGraphics2D
 * ============
 * 
 * (C)opyright 2014-2016, by Object Refinery Limited.
 * 
 * http://www.jfree.org/fxgraphics2d/index.html
 *
 * The FXGraphics2D class has been developed by Object Refinery Limited for 
 * use in Orson Charts (http://www.object-refinery.com/orsoncharts) and
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
 *   - Neither the name of the Object Refinery Limited nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL OBJECT REFINERY LIMITED BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 */

package org.jfree.fx;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.tk.Toolkit;
import com.sun.javafx.scene.text.TextLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.text.CharacterIterator;
import javafx.scene.canvas.GraphicsContext;

/**
 * A font metrics implementation for JavaFX.  This uses the JavaFX APIs to
 * get font measurements, which is more exact than relying on the equivalent
 * Java2D APIs...the only issue is that we need to call non-public API to 
 * get these measurements.
 * 
 * @since 1.5
 */
public class FXFontMetrics extends FontMetrics {
    
    private Graphics2D g2;
    
    private GraphicsContext ctx;
    
    private TextLayout layout = Toolkit.getToolkit().getTextLayoutFactory().createLayout();
    
    /**
     * Creates a new instance.
     * 
     * @param font  the font ({@code null} not permitted).
     * @param g2  the graphics target ({@code null} not permitted).
     * @param ctx  the graphics context ({@code null} not permitted).
     */
    public FXFontMetrics(Font font, Graphics2D g2, GraphicsContext ctx) {
        super(font);
        this.g2 = g2;
        this.ctx = ctx;
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
        this.layout.setContent(str, this.ctx.getFont().impl_getNativeFont());
        this.layout.setBoundsType(TextLayout.TYPE_BASELINE);
        BaseBounds bb = this.layout.getBounds();
        return new Rectangle2D.Double(bb.getMinX(), bb.getMinY(), bb.getWidth(), bb.getHeight());
    }

    @Override
    public int stringWidth(String str) {
        return (int) getStringBounds(str, this.g2).getWidth();
    }
    
}
