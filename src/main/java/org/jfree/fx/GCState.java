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

import javafx.scene.canvas.GraphicsContext;

/**
 * Clip / save / restore is global to a graphic context
 */
final class GCState {

    private final static boolean TRACE = false;

    /** The graphics context for the JavaFX canvas. */
    final GraphicsContext gc;

    private int restorePoint = 0;

    GCState(final GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public String toString() {
        return "GCState{" + "gc=" + gc + ", stackPos= " + restorePoint + '}';
    }

    void restore() {
        if (TRACE) {
            System.out.println("gc.restore:  in: " + this);
        }
        this.gc.restore(); // get back original clip
        this.restorePoint--;
        if (TRACE) {
            System.out.println("gc.restore: out: " + this);
        }
    }

    void save() {
        if (TRACE) {
            System.out.println("gc.save:  in: " + this);
        }
        this.gc.save(); // save current clip
        this.restorePoint++;
        if (TRACE) {
            System.out.println("gc.save: out: " + this);
        }
    }
}
