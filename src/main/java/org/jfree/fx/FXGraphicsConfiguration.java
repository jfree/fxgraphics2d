/* ============
 * FXGraphics2D
 * ============
 * 
 * (C)opyright 2014-present, by David Gilbert.
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

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.VolatileImage;

/**
 * A graphics configuration for the {@link FXGraphics2D} class.
 */
public class FXGraphicsConfiguration extends GraphicsConfiguration {

    private GraphicsDevice device;
    
    private final int width, height;
    
    /**
     * Creates a new instance.
     * 
     * @param width  the width of the bounds.
     * @param height  the height of the bounds.
     */
    public FXGraphicsConfiguration(int width, int height) {
      super(); 
      this.width = width;
      this.height = height;
    }
    
    /**
     * Returns the graphics device that this configuration is associated with.
     * 
     * @return The graphics device (never {@code null}).
     */
    @Override
    public GraphicsDevice getDevice() {
        if (this.device == null) {
            this.device = new FXGraphicsDevice("FXGraphicsDevice", this);
        }
        return this.device;
    }

    /**
     * Returns the color model for this configuration.
     * 
     * @return The color model.
     */
    @Override
    public ColorModel getColorModel() {
        return getColorModel(Transparency.TRANSLUCENT);
    }

    /**
     * Returns the color model for the specified transparency type, or 
     * {@code null}.
     * 
     * @param transparency  the transparency type.
     * 
     * @return A color model (possibly {@code null}).
     */
    @Override
    public ColorModel getColorModel(int transparency) {
        if (transparency == Transparency.TRANSLUCENT) {
            return ColorModel.getRGBdefault();
        } else if (transparency == Transparency.OPAQUE) {
            return new DirectColorModel(32, 0x00ff0000, 0x0000ff00, 0x000000ff);
        } else {
            return null;
        }
    }

    /**
     * Returns the default transform.
     * 
     * @return The default transform. 
     */
    @Override
    public AffineTransform getDefaultTransform() {
        return new AffineTransform();
    }

    /**
     * Returns the normalizing transform.
     * 
     * @return The normalizing transform. 
     */
    @Override
    public AffineTransform getNormalizingTransform() {
        return new AffineTransform();
    }
    
    /**
     * Returns the bounds for this configuration.
     * 
     * @return The bounds. 
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(this.width, this.height);
    }

    private BufferedImage img;
    private GraphicsConfiguration gc;

    /**
     * Returns a volatile image.  This method is a workaround for a
     * ClassCastException that occurs on MacOSX when exporting a Swing UI
     * that uses the Nimbus Look and Feel.
     *
     * @param width  the image width.
     * @param height  the image height.
     * @param caps  the image capabilities.
     * @param transparency  the transparency.
     *
     * @return The volatile image.
     *
     * @throws AWTException if there is a problem creating the image.
     */
    @Override
    public VolatileImage createCompatibleVolatileImage(int width, int height,
                                                       ImageCapabilities caps, int transparency) throws AWTException {
        if (img == null) {
            img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            gc = img.createGraphics().getDeviceConfiguration();
        }
        return gc.createCompatibleVolatileImage(width, height, caps,
                transparency);
    }

}
