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

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;

/**
 * Clip / save / restore is global to a graphic context
 */
final class GCStateHandler {

    private final static boolean TRACE = false;

    /** The graphics context for the JavaFX canvas. */
    final GraphicsContext gc;

    private int saveCount = 0;

    /** stack of saved states */
    private final ArrayList<GCState> states = new ArrayList<GCState>(8);

    GCStateHandler(final GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public String toString() {
        return "GCState{" + "gc=" + gc + ", stackPos= " + saveCount + '}';
    }

    boolean isStateSavedSince(final int saveCount) {
        return (getSaveCount() > saveCount);
    }

    int getSaveCount() {
        return this.saveCount;
    }

    int save(final GCState state) {
        if (TRACE) {
            System.out.println("gc.save:  in: " + this);
        }

        // save current clip:
        this.gc.save();

        // save state in stack at current restorePoint:
        states.add(state);

        final int restore = this.saveCount++;

        if (TRACE) {
            System.out.println("gc.save: out: " + this);
        }
        return restore;
    }

    private GCState restore() {
        if (TRACE) {
            System.out.println("gc.restore:  in: " + this);
        }
        GCState state = null;

        if (isStateSavedSince(0)) {
            final int pos = --this.saveCount;
            this.gc.restore(); // get back original clip
            if (pos >= 0) {
                state = states.remove(pos);
            }
        }
        if (TRACE) {
            System.out.println("gc.restore: out: " + this);
        }
        return state;
    }

    GCState restoreToCount(final int saveCount) {
        if (TRACE) {
            System.out.println("gc.restoreToCount(" + saveCount + "):  in: " + this);
        }
        GCState state = null;

        while (isStateSavedSince(saveCount)) {
            state = restore();
        }
        if (TRACE) {
            System.out.println("gc.restoreToCount(" + saveCount + "): out: " + this);
        }
        return state;
    }

    public final static class GCState {

        final Color savedColor;
        final Paint savedPaint;
        final Stroke savedStroke;
        final Font savedFont;
        final AffineTransform savedTransform;

        public GCState(Color color, Paint paint, Stroke stroke, Font font, AffineTransform transform) {
            this.savedColor = color;
            this.savedPaint = paint;
            this.savedStroke = stroke;
            this.savedFont = font;
            this.savedTransform = new AffineTransform(transform);
        }

        @Override
        public String toString() {
            return "GCState{"
                    + "savedColor=" + savedColor
                    + ", savedPaint=" + savedPaint
                    + ", savedStroke=" + savedStroke
                    + ", savedFont=" + savedFont
                    + ", savedTransform=" + savedTransform + '}';
        }
    }
}
