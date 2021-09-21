/* ============
 * FXGraphics2D
 * ============
 * 
 * (C)opyright 2014-2021, by David Gilbert Limited.
 * 
 * http://www.jfree.org/fxgraphics2d/index.html
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

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;

/**
 * A graphics device for FXGraphics2D.
 */
public class FXGraphicsDevice extends GraphicsDevice {

    private final String id;
    
    GraphicsConfiguration defaultConfig;
    
    /**
     * Creates a new instance.
     * 
     * @param id  the id.
     * @param defaultConfig  the default configuration.
     */
    public FXGraphicsDevice(String id, GraphicsConfiguration defaultConfig) {
        this.id = id;
        this.defaultConfig = defaultConfig;
    }
    
    /**
     * Returns the device type.  
     * 
     * @return The device type.
     */
    @Override
    public int getType() {
        return GraphicsDevice.TYPE_RASTER_SCREEN;
    }

    /**
     * Returns the id string (defined in the constructor).
     * 
     * @return The id string. 
     */
    @Override
    public String getIDstring() {
        return this.id;
    }

    /**
     * Returns all configurations for this device.
     * 
     * @return All configurations for this device.
     */
    @Override
    public GraphicsConfiguration[] getConfigurations() {
        return new GraphicsConfiguration[] { getDefaultConfiguration() };
    }

    /**
     * Returns the default configuration for this device.
     * 
     * @return The default configuration for this device. 
     */
    @Override
    public GraphicsConfiguration getDefaultConfiguration() {
        return this.defaultConfig;
    }
    
}
