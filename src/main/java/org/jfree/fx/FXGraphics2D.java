/* ============
 * FXGraphics2D
 * ============
 * 
 * (C)opyright 2014-2018, by Object Refinery Limited.
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

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * A {@link Graphics2D} implementation that writes to a JavaFX {@link Canvas}.
 * This is intended for general purpose usage, but has been created for use in
 * Orson Charts (<a href="http://www.object-refinery.com/orsoncharts/">http://www.object-refinery.com/orsoncharts/</a>) and
 * JFreeChart (<a href="http://www.jfree.org/jfreechart/">http://www.jfree.org/jfreechart/</a>).
 */
public class FXGraphics2D extends Graphics2D {
    
    /** The graphics context for the JavaFX canvas. */
    private final GraphicsContext gc;
    
    /** Rendering hints. */
    private final RenderingHints hints;
    
    private Shape clip;
    
    /** Stores the AWT Paint object for get/setPaint(). */
    private Paint paint = Color.BLACK;
    
    /** Stores the AWT Color object for get/setColor(). */
    private Color color = Color.BLACK;
    
    private Composite composite = AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, 1.0f);

    private Stroke stroke = new BasicStroke(1.0f);
 
    /** 
     * The width of the stroke to use when the user supplies a
     * BasicStroke with a width of 0.0 (in this case the Java specification
     * says "If width is set to 0.0f, the stroke is rendered as the thinnest 
     * possible line for the target device and the antialias hint setting.")
     */
    private double zeroStrokeWidth;
    
    private Font font = new Font("SansSerif", Font.PLAIN, 12);
    
    private final FontRenderContext fontRenderContext = new FontRenderContext(
            null, false, true);

    private AffineTransform transform = new AffineTransform();

    /** The background color, used in the {@code clearRect()} method. */
    private Color background = Color.BLACK;
    
    /** A flag that is set when the JavaFX graphics state has been saved. */
    private boolean stateSaved = false;

    private Stroke savedStroke;
    private Paint savedPaint;
    private Color savedColor;
    private Font savedFont;
    private AffineTransform savedTransform;
    
    /**
     * An instance that is lazily instantiated in drawLine and then 
     * subsequently reused to avoid creating a lot of garbage.
     */
    private Line2D line;
    
    /**
     * An instance that is lazily instantiated in fillRect and then 
     * subsequently reused to avoid creating a lot of garbage.
     */
    Rectangle2D rect;

    /**
     * An instance that is lazily instantiated in draw/fillRoundRect and then
     * subsequently reused to avoid creating a lot of garbage.
     */
    private RoundRectangle2D roundRect;
    
     /**
     * An instance that is lazily instantiated in draw/fillOval and then
     * subsequently reused to avoid creating a lot of garbage.
     */
   private Ellipse2D oval;
    
    /**
     * An instance that is lazily instantiated in draw/fillArc and then
     * subsequently reused to avoid creating a lot of garbage.
     */
    private Arc2D arc;
    
    /** A hidden image used for font metrics. */
    private BufferedImage fmImage;

    /** 
     * A Graphics2D instance for the hidden image that is used for font
     * metrics.  Used in the getFontMetrics(Font f) method.
     */
    private Graphics2D fmImageG2;
    
    /** The FXFontMetrics. */
    private FXFontMetrics fxFontMetrics;

    /** 
     * The device configuration (this is lazily instantiated in the 
     * getDeviceConfiguration() method).
     */
    private GraphicsConfiguration deviceConfiguration;
    
    /**
     * Throws an {@code IllegalArgumentException} if {@code arg} is
     * {@code null}.
     * 
     * @param arg  the argument to check.
     * @param name  the name of the argument (to display in the exception 
     *         message).
     */
    private static void nullNotPermitted(Object arg, String name) {
        if (arg == null) {
            throw new IllegalArgumentException("Null '" + name + "' argument.");
        }    
    }
    
    /**
     * Creates a new instance that will render to the specified JavaFX
     * {@code GraphicsContext}.
     * 
     * @param gc  the graphics context ({@code null} not permitted). 
     */
    public FXGraphics2D(GraphicsContext gc) {
        nullNotPermitted(gc, "gc");
        this.gc = gc;
        this.zeroStrokeWidth = 0.5;
        this.hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_DEFAULT);
        this.hints.put(FXHints.KEY_USE_FX_FONT_METRICS, true);
    }
    
    /**
     * Returns the width to use for the stroke when the AWT stroke
     * specified has a zero width (the default value is {@code 0.5}).  
     * <p>In the Java specification for {@code BasicStroke} it states "If width 
     * is set to 0.0f, the stroke is rendered as the thinnest possible 
     * line for the target device and the antialias hint setting."  We don't 
     * have a means to implement that accurately since we must specify a fixed
     * width to the JavaFX canvas - this attribute is the width that is 
     * used.</p>
     * 
     * @return The width.
     */
    public double getZeroStrokeWidth() {
        return this.zeroStrokeWidth;
    }
    
    /**
     * Sets the width to use for the stroke when setting a new AWT stroke that
     * has a width of {@code 0.0}.
     * 
     * @param width  the new width (must be 0.0 or greater).
     */
    public void setZeroStrokeWidth(double width) {
        if (width < 0.0) {
            throw new IllegalArgumentException("Width cannot be negative.");
        }
        this.zeroStrokeWidth = width;
    }
    
    /**
     * Returns the device configuration associated with this
     * {@code Graphics2D}.
     * 
     * @return The device configuration (never {@code null}).
     */
    @Override
    public GraphicsConfiguration getDeviceConfiguration() {
        if (this.deviceConfiguration == null) {
            int width = (int) this.gc.getCanvas().getWidth();
            int height = (int) this.gc.getCanvas().getHeight();
            this.deviceConfiguration = new FXGraphicsConfiguration(width,
                    height);
        }
        return this.deviceConfiguration;
    }

    /**
     * Creates a new graphics object that is a copy of this graphics object.
     * 
     * @return A new graphics object.
     */
    @Override
    public Graphics create() {
        FXGraphics2D copy = new FXGraphics2D(this.gc);
        copy.setRenderingHints(getRenderingHints());
        copy.setClip(getClip());
        copy.setPaint(getPaint());
        copy.setColor(getColor());
        copy.setComposite(getComposite());
        copy.setStroke(getStroke());
        copy.setFont(getFont());
        copy.setTransform(getTransform());
        copy.setBackground(getBackground());    
        return copy;
    }

    /**
     * Returns the paint used to draw or fill shapes (or text).  The default 
     * value is {@link Color#BLACK}.  This attribute is updated by both the
     * {@link #setPaint(java.awt.Paint)} and {@link #setColor(java.awt.Color)}
     * methods.
     * 
     * @return The paint (never {@code null}). 
     * 
     * @see #setPaint(java.awt.Paint) 
     */
    @Override
    public Paint getPaint() {
        return this.paint;
    }

    /**
     * Sets the paint used to draw or fill shapes (or text).  If 
     * {@code paint} is an instance of {@code Color}, this method will
     * also update the current color attribute (see {@link #getColor()}). If 
     * you pass {@code null} to this method, it does nothing (in 
     * accordance with the JDK specification).
     * <br><br>
     * Note that this implementation will map {@link Color}, 
     * {@link GradientPaint}, {@link LinearGradientPaint} and 
     * {@link RadialGradientPaint} to JavaFX equivalents, other paint 
     * implementations are not handled.
     * 
     * @param paint  the paint ({@code null} is permitted but ignored).
     * 
     * @see #getPaint() 
     */
    @Override
    public void setPaint(Paint paint) {
        if (paint == null) {
            return;
        }
        if (paintsAreEqual(paint, this.paint)) {
            return;
        }
        applyPaint(paint);
    }
    
    private void applyPaint(Paint paint) {
        this.paint = paint;
        if (paint instanceof Color) {
            setColor((Color) paint);
        } else if (paint instanceof GradientPaint) {
            GradientPaint gp = (GradientPaint) paint;
            Stop[] stops = new Stop[] { new Stop(0, 
                    awtColorToJavaFX(gp.getColor1())), 
                    new Stop(1, awtColorToJavaFX(gp.getColor2())) };
            Point2D p1 = gp.getPoint1();
            Point2D p2 = gp.getPoint2();
            LinearGradient lg = new LinearGradient(p1.getX(), p1.getY(), 
                    p2.getX(), p2.getY(), false, CycleMethod.NO_CYCLE, stops);
            this.gc.setStroke(lg);
            this.gc.setFill(lg);
        } else if (paint instanceof MultipleGradientPaint) {
            MultipleGradientPaint mgp = (MultipleGradientPaint) paint;
            Color[] colors = mgp.getColors();
            float[] fractions = mgp.getFractions();
            Stop[] stops = new Stop[colors.length];
            for (int i = 0; i < colors.length; i++) {
                stops[i] = new Stop(fractions[i], awtColorToJavaFX(colors[i]));
            }

            if (paint instanceof RadialGradientPaint) {
                RadialGradientPaint rgp = (RadialGradientPaint) paint;
                Point2D center = rgp.getCenterPoint();
                Point2D focus = rgp.getFocusPoint();           
                double focusDistance = focus.distance(center);
                double focusAngle = 0.0;
                if (!focus.equals(center)) {
                    focusAngle = Math.atan2(focus.getY() - center.getY(), 
                        focus.getX() - center.getX());
                }
                double radius = rgp.getRadius();
                RadialGradient rg = new RadialGradient(
                        Math.toDegrees(focusAngle), focusDistance, 
                        center.getX(), center.getY(), radius, false, 
                        CycleMethod.NO_CYCLE, stops);
                this.gc.setStroke(rg);
                this.gc.setFill(rg);
            } else if (paint instanceof LinearGradientPaint) {
                LinearGradientPaint lgp = (LinearGradientPaint) paint;
                Point2D start = lgp.getStartPoint();
                Point2D end = lgp.getEndPoint();
                LinearGradient lg = new LinearGradient(start.getX(), 
                        start.getY(), end.getX(), end.getY(), false, 
                        CycleMethod.NO_CYCLE, stops);
                this.gc.setStroke(lg);
                this.gc.setFill(lg);
            }
        } else {
            // this is a paint we don't recognise
        }
    }

    /**
     * Returns the foreground color.  This method exists for backwards
     * compatibility in AWT, you should use the {@link #getPaint()} method.
     * This attribute is updated by the {@link #setColor(java.awt.Color)}
     * method, and also by the {@link #setPaint(java.awt.Paint)} method if
     * a {@code Color} instance is passed to the method.
     * 
     * @return The foreground color (never {@code null}).
     * 
     * @see #getPaint() 
     */
    @Override
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the foreground color.  This method exists for backwards 
     * compatibility in AWT, you should use the 
     * {@link #setPaint(java.awt.Paint)} method.
     * 
     * @param c  the color ({@code null} permitted but ignored). 
     * 
     * @see #setPaint(java.awt.Paint) 
     */
    @Override
    public void setColor(Color c) {
        if (c == null || c.equals(this.color)) {
            return;
        }
        applyColor(c);
    }
    
    private void applyColor(Color c) {
        this.color = c;
        this.paint = c;
        javafx.scene.paint.Color fxcolor = awtColorToJavaFX(c);
        this.gc.setFill(fxcolor);
        this.gc.setStroke(fxcolor);
    }

    /**
     * Returns a JavaFX color that is equivalent to the specified AWT color.
     * 
     * @param c  the color ({@code null} not permitted).
     * 
     * @return A JavaFX color. 
     */
    private javafx.scene.paint.Color awtColorToJavaFX(Color c) {
        return javafx.scene.paint.Color.rgb(c.getRed(), c.getGreen(), 
                c.getBlue(), c.getAlpha() / 255.0);
    }
    
    /**
     * Returns the background color (the default value is {@link Color#BLACK}).
     * This attribute is used by the {@link #clearRect(int, int, int, int)} 
     * method.
     * 
     * @return The background color (possibly {@code null}). 
     * 
     * @see #setBackground(java.awt.Color) 
     */
    @Override
    public Color getBackground() {
        return this.background;
    }

    /**
     * Sets the background color.  This attribute is used by the 
     * {@link #clearRect(int, int, int, int)} method.  The reference 
     * implementation allows {@code null} for the background color so
     * we allow that too (but for that case, the {@link #clearRect(int, int, int, int)} 
     * method will do nothing).
     * 
     * @param color  the color ({@code null} permitted).
     * 
     * @see #getBackground() 
     */
    @Override
    public void setBackground(Color color) {
        this.background = color;
    }

    /**
     * Returns the current composite.
     * 
     * @return The current composite (never {@code null}).
     * 
     * @see #setComposite(java.awt.Composite) 
     */
    @Override
    public Composite getComposite() {
        return this.composite;
    }
    
    /**
     * Sets the composite.  There is limited handling for 
     * {@code AlphaComposite}, other composites will have no effect on the 
     * output.
     * 
     * @param comp  the composite ({@code null} not permitted).
     * 
     * @see #getComposite() 
     */
    @Override
    public void setComposite(Composite comp) {
        nullNotPermitted(comp, "comp");
        this.composite = comp;
        if (comp instanceof AlphaComposite) {
            AlphaComposite ac = (AlphaComposite) comp;
            this.gc.setGlobalAlpha(ac.getAlpha());
            this.gc.setGlobalBlendMode(blendMode(ac.getRule()));
        }
    }
    
    /**
     * Returns a JavaFX BlendMode that is the closest match for the Java2D 
     * alpha composite rule.
     * 
     * @param rule  the rule.
     * 
     * @return The blend mode. 
     */
    private BlendMode blendMode(int rule) {
        switch (rule) {
            case AlphaComposite.SRC_ATOP:
                return BlendMode.SRC_ATOP;
            case AlphaComposite.CLEAR:
            case AlphaComposite.DST:
            case AlphaComposite.DST_ATOP:
            case AlphaComposite.DST_IN:
            case AlphaComposite.DST_OUT:
            case AlphaComposite.DST_OVER:
            case AlphaComposite.SRC:
            case AlphaComposite.SRC_IN:
            case AlphaComposite.SRC_OUT:
            case AlphaComposite.SRC_OVER:
            case AlphaComposite.XOR:
                return BlendMode.SRC_OVER;
            default:
                return BlendMode.SRC_OVER;
        }
    }

    /**
     * Returns the current stroke (this attribute is used when drawing shapes). 
     * 
     * @return The current stroke (never {@code null}). 
     * 
     * @see #setStroke(java.awt.Stroke) 
     */
    @Override
    public Stroke getStroke() {
        return this.stroke;
    }

    /**
     * Sets the stroke that will be used to draw shapes.  
     * 
     * @param s  the stroke ({@code null} not permitted).
     * 
     * @see #getStroke() 
     */
    @Override
    public void setStroke(Stroke s) {
        nullNotPermitted(s, "s");
        if (s == this.stroke) { // quick test, full equals test later
            return;
        }
        if (stroke instanceof BasicStroke) {
            BasicStroke bs = (BasicStroke) s;
            if (bs.equals(this.stroke)) {
                return; // no change
            }
        }
        this.stroke = s;
        applyStroke(s);
    }
    
    private void applyStroke(Stroke s) {
        if (s instanceof BasicStroke) {
            applyBasicStroke((BasicStroke) s);
        }
    }
    
    private void applyBasicStroke(BasicStroke bs) {
        double lineWidth = bs.getLineWidth();
        if (lineWidth == 0.0) {
            lineWidth = this.zeroStrokeWidth;
        }
        this.gc.setLineWidth(lineWidth);
        this.gc.setLineCap(awtToJavaFXLineCap(bs.getEndCap()));
        this.gc.setLineJoin(awtToJavaFXLineJoin(bs.getLineJoin()));
        this.gc.setMiterLimit(bs.getMiterLimit());
        this.gc.setLineDashes(floatToDoubleArray(bs.getDashArray()));
        this.gc.setLineDashOffset(bs.getDashPhase());   
    }
    
    /**
     * Maps a line cap code from AWT to the corresponding JavaFX StrokeLineCap
     * enum value.
     * 
     * @param c  the line cap code.
     * 
     * @return A JavaFX line cap value. 
     */
    private StrokeLineCap awtToJavaFXLineCap(int c) {
        if (c == BasicStroke.CAP_BUTT) {
            return StrokeLineCap.BUTT;
        } else if (c == BasicStroke.CAP_ROUND) {
            return StrokeLineCap.ROUND;
        } else if (c == BasicStroke.CAP_SQUARE) {
            return StrokeLineCap.SQUARE;
        } else {
            throw new IllegalArgumentException("Unrecognised cap code: " + c);
        }
    }

    /**
     * Maps a line join code from AWT to the corresponding JavaFX 
     * StrokeLineJoin enum value.
     * 
     * @param j  the line join code.
     * 
     * @return A JavaFX line join value. 
     */
    private StrokeLineJoin awtToJavaFXLineJoin(int j) {
        if (j == BasicStroke.JOIN_BEVEL) {
            return StrokeLineJoin.BEVEL;
        } else if (j == BasicStroke.JOIN_MITER) {
            return StrokeLineJoin.MITER;
        } else if (j == BasicStroke.JOIN_ROUND) {
            return StrokeLineJoin.ROUND;
        } else {
            throw new IllegalArgumentException("Unrecognised join code: " + j);            
        }
    }
    
    private double[] floatToDoubleArray(float[] f) {
        if (f == null) {
            return null;
        }
        double[] d = new double[f.length];
        for (int i = 0; i < f.length; i++) {
            d[i] = (double) f[i];
        }
        return d;
    }

    /**
     * Returns the current value for the specified hint.
     * 
     * @param hintKey  the hint key ({@code null} permitted, but the
     *     result will be {@code null} also in that case).
     * 
     * @return The current value for the specified hint 
     *     (possibly {@code null}).
     * 
     * @see #setRenderingHint(java.awt.RenderingHints.Key, java.lang.Object) 
     */
    @Override
    public Object getRenderingHint(RenderingHints.Key hintKey) {
        return this.hints.get(hintKey);
    }

    /**
     * Sets the value for a hint.  See the {@link FXHints} class for 
     * information about the hints that can be used with this implementation.
     * 
     * @param hintKey  the hint key ({@code null} not permitted).
     * @param hintValue  the hint value.
     * 
     * @see #getRenderingHint(java.awt.RenderingHints.Key) 
     */
    @Override
    public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {
        this.hints.put(hintKey, hintValue);
    }

    /**
     * Returns a copy of the rendering hints.  Modifying the returned copy
     * will have no impact on the state of this {@code Graphics2D} 
     * instance.
     * 
     * @return The rendering hints (never {@code null}). 
     * 
     * @see #setRenderingHints(java.util.Map) 
     */
    @Override
    public RenderingHints getRenderingHints() {
        return (RenderingHints) this.hints.clone();
    }

    /**
     * Sets the rendering hints to the specified collection.
     * 
     * @param hints  the new set of hints ({@code null} not permitted).
     * 
     * @see #getRenderingHints() 
     */
    @Override
    public void setRenderingHints(Map<?, ?> hints) {
        this.hints.clear();
        this.hints.putAll(hints);
    }

    /**
     * Adds all the supplied rendering hints.
     * 
     * @param hints  the hints ({@code null} not permitted).
     */
    @Override
    public void addRenderingHints(Map<?, ?> hints) {
        this.hints.putAll(hints);
    }

    /**
     * Draws the specified shape with the current {@code paint} and 
     * {@code stroke}.  There is direct handling for {@code Line2D}, 
     * {@code Rectangle2D}, {@code Ellipse2D}, {@code Arc2D} and 
     * {@code Path2D}. All other shapes are mapped to a path outline and then
     * drawn.
     * 
     * @param s  the shape ({@code null} not permitted).
     * 
     * @see #fill(java.awt.Shape) 
     */
    @Override
    public void draw(Shape s) {
        // if the current stroke is not a BasicStroke then it is handled as
        // a special case
        if (!(this.stroke instanceof BasicStroke)) {
            fill(this.stroke.createStrokedShape(s));
            return;
        }
        if (s instanceof Line2D) {
            Line2D l = (Line2D) s;
            Object hint = getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
            if (hint != RenderingHints.VALUE_STROKE_PURE) {
                double x1 = Math.rint(l.getX1()) - 0.5;
                double y1 = Math.rint(l.getY1()) - 0.5;
                double x2 = Math.rint(l.getX2()) - 0.5;
                double y2 = Math.rint(l.getY2()) - 0.5;
                l.setLine(x1, y1, x2, y2);
            }
            this.gc.strokeLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
        } else if (s instanceof Rectangle2D) {
            Rectangle2D r = (Rectangle2D) s;
            if (s instanceof Rectangle) {
                r = new Rectangle2D.Double(r.getX(), r.getY(), r.getWidth(), 
                        r.getHeight());
            }
            Object hint = getRenderingHint(RenderingHints.KEY_STROKE_CONTROL);
            if (hint != RenderingHints.VALUE_STROKE_PURE) {
                double x = Math.rint(r.getX()) - 0.5;
                double y = Math.rint(r.getY()) - 0.5;
                double w = Math.floor(r.getWidth());
                double h = Math.floor(r.getHeight());
                r.setRect(x, y, w, h);
            }
            this.gc.strokeRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        } else if (s instanceof RoundRectangle2D) {
            RoundRectangle2D rr = (RoundRectangle2D) s;
            this.gc.strokeRoundRect(rr.getX(), rr.getY(), rr.getWidth(), 
                    rr.getHeight(), rr.getArcWidth(), rr.getArcHeight());
        } else if (s instanceof Ellipse2D) {
            Ellipse2D e = (Ellipse2D) s;
            this.gc.strokeOval(e.getX(), e.getY(), e.getWidth(), e.getHeight());
        } else if (s instanceof Arc2D) {
            Arc2D a = (Arc2D) s;
            this.gc.strokeArc(a.getX(), a.getY(), a.getWidth(), a.getHeight(), 
                    a.getAngleStart(), a.getAngleExtent(), 
                    intToArcType(a.getArcType()));
        } else {
            shapeToPath(s);
            this.gc.stroke();
        }
    }

    private final double[] coords = new double[6];
    
    /**
     * Maps a shape to a path in the graphics context. 
     * 
     * @param s  the shape ({@code null} not permitted).
     */
    private void shapeToPath(Shape s) {
        this.gc.beginPath();
        PathIterator iterator = s.getPathIterator(null);
        while (!iterator.isDone()) {
            int segType = iterator.currentSegment(coords);
            switch (segType) {
                case PathIterator.SEG_MOVETO:
                    this.gc.moveTo(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    this.gc.lineTo(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    this.gc.quadraticCurveTo(coords[0], coords[1], coords[2], 
                            coords[3]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    this.gc.bezierCurveTo(coords[0], coords[1], coords[2], 
                            coords[3], coords[4], coords[5]);
                    break;
                case PathIterator.SEG_CLOSE:
                    this.gc.closePath();
                    break;
                default:
                    throw new RuntimeException("Unrecognised segment type " 
                            + segType);
            }
            iterator.next();
        }
    }
    
    private ArcType intToArcType(int t) {
        if (t == Arc2D.CHORD) {
            return ArcType.CHORD;
        } else if (t == Arc2D.OPEN) {
            return ArcType.OPEN;
        } else if (t == Arc2D.PIE) {
            return ArcType.ROUND;
        }
        throw new IllegalArgumentException("Unrecognised t: " + t);
    }
    
    /**
     * Fills the specified shape with the current {@code paint}.  There is
     * direct handling for {@code RoundRectangle2D}, 
     * {@code Rectangle2D}, {@code Ellipse2D} and {@code Arc2D}.  
     * All other shapes are mapped to a path outline and then filled.
     * 
     * @param s  the shape ({@code null} not permitted). 
     * 
     * @see #draw(java.awt.Shape) 
     */
    @Override
    public void fill(Shape s) {
        if (s instanceof Rectangle2D) {
            Rectangle2D r = (Rectangle2D) s;
            this.gc.fillRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
        } else if (s instanceof RoundRectangle2D) {
            RoundRectangle2D rr = (RoundRectangle2D) s;
            this.gc.fillRoundRect(rr.getX(), rr.getY(), rr.getWidth(), 
                    rr.getHeight(), rr.getArcWidth(), rr.getArcHeight());
        } else if (s instanceof Ellipse2D) {
            Ellipse2D e = (Ellipse2D) s;
            this.gc.fillOval(e.getX(), e.getY(), e.getWidth(), e.getHeight());
        } else if (s instanceof Arc2D) {
            Arc2D a = (Arc2D) s;
            this.gc.fillArc(a.getX(), a.getY(), a.getWidth(), a.getHeight(), 
                    a.getAngleStart(), a.getAngleExtent(), 
                    intToArcType(a.getArcType()));
        } else {
            shapeToPath(s);
            this.gc.fill();
        }
    }

    /**
     * Returns the current font used for drawing text.
     * 
     * @return The current font (never {@code null}).
     * 
     * @see #setFont(java.awt.Font) 
     */
    @Override
    public Font getFont() {
        return this.font;
    }

    /**
     * Sets the font to be used for drawing text.
     * 
     * @param font  the font ({@code null} is permitted but ignored).
     * 
     * @see #getFont() 
     */
    @Override
    public void setFont(Font font) {
        if (font == null || this.font.equals(font)) {
            return;
        }
        applyFont(font);
    }

    private void applyFont(Font font) {
        this.font = font;
        FontWeight weight = font.isBold() ? FontWeight.BOLD : FontWeight.NORMAL;
        FontPosture posture = font.isItalic() 
                ? FontPosture.ITALIC : FontPosture.REGULAR;
        javafx.scene.text.Font jfxfont = javafx.scene.text.Font.font(
                font.getFamily(), weight, posture, font.getSize());
        this.gc.setFont(jfxfont);
    }

    /**
     * Returns the font metrics for the specified font.  The font metrics 
     * returned are from Java2D (via an internal {@code BufferedImage}) which 
     * does not always match exactly the font metrics used by JavaFX.
     * 
     * @param f  the font.
     * 
     * @return The font metrics. 
     */
    @Override
    public FontMetrics getFontMetrics(Font f) {
        if (getRenderingHint(FXHints.KEY_USE_FX_FONT_METRICS) == Boolean.TRUE) {
            if (this.fxFontMetrics == null 
                    || !f.equals(this.fxFontMetrics.getFont())) {
                this.fxFontMetrics = new FXFontMetrics(this.font, this);
            }
            return this.fxFontMetrics;
        } 
        
        // be lazy about creating the underlying objects...
        if (this.fmImage == null) {
            this.fmImage = new BufferedImage(10, 10,
                    BufferedImage.TYPE_INT_RGB);
            this.fmImageG2 = this.fmImage.createGraphics();
            this.fmImageG2.setRenderingHint(
                    RenderingHints.KEY_FRACTIONALMETRICS,
                    RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        }
        return this.fmImageG2.getFontMetrics(f);
    }
    
    /**
     * Returns the font render context.  The implementation here returns the
     * {@code FontRenderContext} for an image that is maintained 
     * internally (as for {@link #getFontMetrics}).
     * 
     * @return The font render context.
     */
    @Override
    public FontRenderContext getFontRenderContext() {
        return this.fontRenderContext;
    }

    /**
     * Draws a string at {@code (x, y)}.  The start of the text at the
     * baseline level will be aligned with the {@code (x, y)} point.
     * 
     * @param str  the string ({@code null} not permitted).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * 
     * @see #drawString(java.lang.String, float, float) 
     */
    @Override
    public void drawString(String str, int x, int y) {
        drawString(str, (float) x, (float) y);
    }

    /**
     * Draws a string at {@code (x, y)}. The start of the text at the
     * baseline level will be aligned with the {@code (x, y)} point.
     * 
     * @param str  the string ({@code null} not permitted).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     */
    @Override
    public void drawString(String str, float x, float y) {
        if (str == null) {
            throw new NullPointerException("Null 'str' argument.");
        }
        this.gc.fillText(str, x, y);
    }

    /**
     * Draws a string of attributed characters at {@code (x, y)}.  The 
     * call is delegated to 
     * {@link #drawString(AttributedCharacterIterator, float, float)}. 
     * 
     * @param iterator  an iterator for the characters.
     * @param x  the x-coordinate.
     * @param y  the x-coordinate.
     */
    @Override
    public void drawString(AttributedCharacterIterator iterator, int x, int y) {
        drawString(iterator, (float) x, (float) y); 
    }

    /**
     * Draws a string of attributed characters at {@code (x, y)}. 
     * 
     * @param iterator  an iterator over the characters ({@code null} not 
     *     permitted).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     */
    @Override
    public void drawString(AttributedCharacterIterator iterator, float x, 
            float y) {
        Set<AttributedCharacterIterator.Attribute> 
                s = iterator.getAllAttributeKeys();
        if (!s.isEmpty()) {
            TextLayout layout = new TextLayout(iterator, 
                    getFontRenderContext());
            layout.draw(this, x, y);
        } else {
            StringBuilder strb = new StringBuilder();
            iterator.first();
            for (int i = iterator.getBeginIndex(); i < iterator.getEndIndex(); 
                    i++) {
                strb.append(iterator.current());
                iterator.next();
            }
            drawString(strb.toString(), x, y);
        }
    }

    /**
     * Draws the specified glyph vector at the location {@code (x, y)}.
     * 
     * @param g  the glyph vector ({@code null} not permitted).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     */
    @Override
    public void drawGlyphVector(GlyphVector g, float x, float y) {
        fill(g.getOutline(x, y));
    }

    /**
     * Applies the translation {@code (tx, ty)}.  This call is delegated 
     * to {@link #translate(double, double)}.
     * 
     * @param tx  the x-translation.
     * @param ty  the y-translation.
     * 
     * @see #translate(double, double) 
     */
    @Override
    public void translate(int tx, int ty) {
        translate((double) tx, (double) ty);
    }

    /**
     * Applies the translation {@code (tx, ty)}.
     * 
     * @param tx  the x-translation.
     * @param ty  the y-translation.
     */
    @Override
    public void translate(double tx, double ty) {
        this.transform.translate(tx, ty);
        this.gc.translate(tx, ty);
    }

    /**
     * Applies a rotation (anti-clockwise) about {@code (0, 0)}.
     * 
     * @param theta  the rotation angle (in radians). 
     */
    @Override
    public void rotate(double theta) {
        this.transform.rotate(theta);
        this.gc.rotate(Math.toDegrees(theta));
    }

    /**
     * Applies a rotation (anti-clockwise) about {@code (x, y)}.
     * 
     * @param theta  the rotation angle (in radians).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     */
    @Override
    public void rotate(double theta, double x, double y) {
        translate(x, y);
        rotate(theta);
        translate(-x, -y);
    }

    /**
     * Applies a scale transformation.
     * 
     * @param sx  the x-scaling factor.
     * @param sy  the y-scaling factor.
     */
    @Override
    public void scale(double sx, double sy) {
        this.transform.scale(sx, sy);
        this.gc.scale(sx, sy);
    }

    /**
     * Applies a shear transformation. This is equivalent to the following 
     * call to the {@code transform} method:
     * <br><br>
     * <ul><li>
     * {@code transform(AffineTransform.getShearInstance(shx, shy));}
     * </ul>
     * 
     * @param shx  the x-shear factor.
     * @param shy  the y-shear factor.
     */
    @Override
    public void shear(double shx, double shy) {
        transform(AffineTransform.getShearInstance(shx, shy));
    }

    /**
     * Applies this transform to the existing transform by concatenating it.
     * 
     * @param t  the transform ({@code null} not permitted). 
     */
    @Override
    public void transform(AffineTransform t) {
        AffineTransform tx = getTransform();
        tx.concatenate(t);
        setTransform(tx);
    }

    /**
     * Returns a copy of the current transform.
     * 
     * @return A copy of the current transform (never {@code null}).
     * 
     * @see #setTransform(java.awt.geom.AffineTransform) 
     */
    @Override
    public AffineTransform getTransform() {
        return (AffineTransform) this.transform.clone();
    }

    /**
     * Sets the transform.
     * 
     * @param t  the new transform ({@code null} permitted, resets to the
     *     identity transform).
     * 
     * @see #getTransform() 
     */
    @Override
    public void setTransform(AffineTransform t) {
        if (t == null) {
            this.transform = new AffineTransform();
            t = this.transform;
        } else {
            this.transform = new AffineTransform(t);
        }
        this.gc.setTransform(t.getScaleX(), t.getShearY(), t.getShearX(), 
                t.getScaleY(), t.getTranslateX(), t.getTranslateY());
    }

    /**
     * Returns {@code true} if the rectangle (in device space) intersects
     * with the shape (the interior, if {@code onStroke} is {@code false}, 
     * otherwise the stroked outline of the shape).
     * 
     * @param rect  a rectangle (in device space).
     * @param s the shape.
     * @param onStroke  test the stroked outline only?
     * 
     * @return A boolean. 
     */
    @Override
    public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
        Shape ts;
        if (onStroke) {
            ts = this.transform.createTransformedShape(
                    this.stroke.createStrokedShape(s));
        } else {
            ts = this.transform.createTransformedShape(s);
        }
        if (!rect.getBounds2D().intersects(ts.getBounds2D())) {
            return false;
        }
        Area a1 = new Area(rect);
        Area a2 = new Area(ts);
        a1.intersect(a2);
        return !a1.isEmpty();
    }

    /**
     * Not implemented - the method does nothing.
     */
    @Override
    public void setPaintMode() {
        // not implemented
    }

    /**
     * Not implemented - the method does nothing.
     */
    @Override
    public void setXORMode(Color c1) {
        // not implemented
    }

    /**
     * Returns the bounds of the user clipping region.
     * 
     * @return The clip bounds (possibly {@code null}). 
     * 
     * @see #getClip() 
     */
    @Override
    public Rectangle getClipBounds() {
        if (this.clip == null) {
            return null;
        }
        return getClip().getBounds();
    }

    /**
     * Returns the user clipping region.  The initial default value is 
     * {@code null}.
     * 
     * @return The user clipping region (possibly {@code null}).
     * 
     * @see #setClip(java.awt.Shape)
     */
    @Override
    public Shape getClip() {
        if (this.clip == null) {
            return null;
        }
        AffineTransform inv;
        try {
            inv = this.transform.createInverse();
            return inv.createTransformedShape(this.clip);
        } catch (NoninvertibleTransformException ex) {
            return null;
        }
    }

    /**
     * Sets the user clipping region.
     * 
     * @param shape  the new user clipping region ({@code null} permitted).
     * 
     * @see #getClip()
     */
    @Override
    public void setClip(Shape shape) {
        if (this.stateSaved) {
            this.gc.restore(); // get back original clip
            reapplyAttributes(); // but keep other attributes
            this.stateSaved = false;
        }
        // null is handled fine here...
        this.clip = this.transform.createTransformedShape(shape);
        if (clip != null) {
            this.gc.save(); 
            rememberSavedAttributes();
            shapeToPath(shape);
            this.gc.clip();
        }
    }
    
    /** 
     * Remember the Graphics2D attributes in force at the point of pushing
     * the JavaFX context.
     */
    private void rememberSavedAttributes() {
        this.stateSaved = true;
        this.savedColor = this.color;
        this.savedFont = this.font;
        this.savedPaint = this.paint;
        this.savedStroke = this.stroke;
        this.savedTransform = new AffineTransform(this.transform);
    }
    
    private void reapplyAttributes() {
        if (!paintsAreEqual(this.paint, this.savedPaint)) {
            applyPaint(this.paint);
        }
        if (!this.color.equals(this.savedColor)) {
            applyColor(this.color);
        }
        if (!this.stroke.equals(this.savedStroke)) {
            applyStroke(this.stroke);
        }
        if (!this.font.equals(this.savedFont)) {
            applyFont(this.font);
        }
        if (!this.transform.equals(this.savedTransform)) {
            setTransform(this.transform);
        }
        this.savedColor = null;
        this.savedFont = null;
        this.savedPaint = null;
        this.savedStroke = null;
        this.savedTransform = null;
    }
    
    /**
     * Clips to the intersection of the current clipping region and the
     * specified shape. 
     * 
     * According to the Oracle API specification, this method will accept a 
     * {@code null} argument, but there is an open bug report (since 2004) 
     * that suggests this is wrong:
     * <p>
     * <a href="http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6206189">
     * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6206189</a>
     * 
     * In this implementation, a {@code null} argument is not permitted.
     * 
     * @param s  the clip shape ({@code null} not permitted). 
     */
    @Override
    public void clip(Shape s) {
        if (s instanceof Line2D) {
            s = s.getBounds2D();
        }
        if (this.clip == null) {
            setClip(s);
            return;
        }
        Shape ts = this.transform.createTransformedShape(s);
        Shape clipNew;
        if (!ts.intersects(this.clip.getBounds2D())) {
            clipNew = new Rectangle2D.Double();
        } else {
            Area a1 = new Area(ts);
            Area a2 = new Area(this.clip);
            a1.intersect(a2);
            clipNew = new Path2D.Double(a1);
        }
        this.clip = clipNew;
        if (!this.stateSaved) {
            this.gc.save();
            rememberSavedAttributes();
        }
        shapeToPath(this.clip);
        this.gc.clip();
    }

    /**
     * Clips to the intersection of the current clipping region and the 
     * specified rectangle.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     */
    @Override
    public void clipRect(int x, int y, int width, int height) {
        clip(rect(x, y, width, height));
    }

    /**
     * Sets the user clipping region to the specified rectangle.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     * 
     * @see #getClip() 
     */
    @Override
    public void setClip(int x, int y, int width, int height) {
        setClip(rect(x, y, width, height));
    }

    /**
     * Draws a line from {@code (x1, y1)} to {@code (x2, y2)} using 
     * the current {@code paint} and {@code stroke}.
     * 
     * @param x1  the x-coordinate of the start point.
     * @param y1  the y-coordinate of the start point.
     * @param x2  the x-coordinate of the end point.
     * @param y2  the x-coordinate of the end point.
     */
    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {
        if (this.line == null) {
            this.line = new Line2D.Double(x1, y1, x2, y2);
        } else {
            this.line.setLine(x1, y1, x2, y2);
        }
        draw(this.line);
    }

    /**
     * Fills the specified rectangle with the current {@code paint}.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the rectangle width.
     * @param height  the rectangle height.
     */
    @Override
    public void fillRect(int x, int y, int width, int height) {
        fill(rect(x, y, width, height));
    }

    /**
     * Clears the specified rectangle by filling it with the current 
     * background color.  If the background color is {@code null}, this
     * method will do nothing.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     * 
     * @see #getBackground() 
     */
    @Override
    public void clearRect(int x, int y, int width, int height) {
        if (getBackground() == null) {
            return;  // we can't do anything
        }
        Paint saved = getPaint();
        setPaint(getBackground());
        fillRect(x, y, width, height);
        setPaint(saved);
    }
    
    /**
     * Draws a rectangle with rounded corners using the current 
     * {@code paint} and {@code stroke}.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     * @param arcWidth  the arc-width.
     * @param arcHeight  the arc-height.
     * 
     * @see #fillRoundRect(int, int, int, int, int, int) 
     */
    @Override
    public void drawRoundRect(int x, int y, int width, int height, 
            int arcWidth, int arcHeight) {
        draw(roundRect(x, y, width, height, arcWidth, arcHeight));
    }

    /**
     * Fills a rectangle with rounded corners using the current {@code paint}.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     * @param arcWidth  the arc-width.
     * @param arcHeight  the arc-height.
     * 
     * @see #drawRoundRect(int, int, int, int, int, int) 
     */
    @Override
    public void fillRoundRect(int x, int y, int width, int height, 
            int arcWidth, int arcHeight) {
        fill(roundRect(x, y, width, height, arcWidth, arcHeight));
    }
    
    /**
     * Draws an oval framed by the rectangle {@code (x, y, width, height)}
     * using the current {@code paint} and {@code stroke}.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     * 
     * @see #fillOval(int, int, int, int) 
     */
    @Override
    public void drawOval(int x, int y, int width, int height) {
        draw(oval(x, y, width, height));
    }

    /**
     * Fills an oval framed by the rectangle {@code (x, y, width, height)}.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     * 
     * @see #drawOval(int, int, int, int) 
     */
    @Override
    public void fillOval(int x, int y, int width, int height) {
        fill(oval(x, y, width, height));
    }

    /**
     * Draws an arc contained within the rectangle 
     * {@code (x, y, width, height)}, starting at {@code startAngle}
     * and continuing through {@code arcAngle} degrees using 
     * the current {@code paint} and {@code stroke}.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     * @param startAngle  the start angle in degrees, 0 = 3 o'clock.
     * @param arcAngle  the angle (anticlockwise) in degrees.
     * 
     * @see #fillArc(int, int, int, int, int, int) 
     */
    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, 
            int arcAngle) {
        draw(arc(x, y, width, height, startAngle, arcAngle));
    }

    /**
     * Fills an arc contained within the rectangle 
     * {@code (x, y, width, height)}, starting at {@code startAngle}
     * and continuing through {@code arcAngle} degrees, using 
     * the current {@code paint}.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     * @param startAngle  the start angle in degrees, 0 = 3 o'clock.
     * @param arcAngle  the angle (anticlockwise) in degrees.
     * 
     * @see #drawArc(int, int, int, int, int, int) 
     */
    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, 
            int arcAngle) {
        fill(arc(x, y, width, height, startAngle, arcAngle));
    }

    /**
     * Draws the specified multi-segment line using the current 
     * {@code paint} and {@code stroke}.
     * 
     * @param xPoints  the x-points.
     * @param yPoints  the y-points.
     * @param nPoints  the number of points to use for the polyline.
     */
    @Override
    public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
        GeneralPath p = createPolygon(xPoints, yPoints, nPoints, false);
        draw(p);
    }

    /**
     * Draws the specified polygon using the current {@code paint} and 
     * {@code stroke}.
     * 
     * @param xPoints  the x-points.
     * @param yPoints  the y-points.
     * @param nPoints  the number of points to use for the polygon.
     * 
     * @see #fillPolygon(int[], int[], int)      */
    @Override
    public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        GeneralPath p = createPolygon(xPoints, yPoints, nPoints, true);
        draw(p);
    }

    /**
     * Fills the specified polygon using the current {@code paint}.
     * 
     * @param xPoints  the x-points.
     * @param yPoints  the y-points.
     * @param nPoints  the number of points to use for the polygon.
     * 
     * @see #drawPolygon(int[], int[], int) 
     */
    @Override
    public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
        GeneralPath p = createPolygon(xPoints, yPoints, nPoints, true);
        fill(p);
    }

    /**
     * Creates a polygon from the specified {@code x} and 
     * {@code y} coordinate arrays.
     * 
     * @param xPoints  the x-points.
     * @param yPoints  the y-points.
     * @param nPoints  the number of points to use for the polyline.
     * @param close  closed?
     * 
     * @return A polygon.
     */
    public GeneralPath createPolygon(int[] xPoints, int[] yPoints, 
            int nPoints, boolean close) {
        GeneralPath p = new GeneralPath();
        p.moveTo(xPoints[0], yPoints[0]);
        for (int i = 1; i < nPoints; i++) {
            p.lineTo(xPoints[i], yPoints[i]);
        }
        if (close) {
            p.closePath();
        }
        return p;
    }
    
    /**
     * Draws an image at the location {@code (x, y)}.  Note that the 
     * {@code observer} is ignored.
     * 
     * @param img  the image ({@code null} permitted...method will do nothing).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param observer  ignored.
     * 
     * @return {@code true} if there is no more drawing to be done. 
     */
    @Override
    public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
        if (img == null) {
            return true;
        }
        int w = img.getWidth(observer);
        if (w < 0) {
            return false;
        }
        int h = img.getHeight(observer);
        if (h < 0) {
            return false;
        }
        return drawImage(img, x, y, w, h, observer);
    }

    /**
     * Draws the image into the rectangle defined by {@code (x, y, w, h)}.  
     * Note that the {@code observer} is ignored (it is not useful in this
     * context).
     * 
     * @param img  the image ({@code null} permitted...draws nothing).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param w  the width.
     * @param h  the height.
     * @param observer  ignored.
     * 
     * @return {@code true} if there is no more drawing to be done. 
     */
    @Override
    public boolean drawImage(final Image img, int x, int y, 
            int w, int h, ImageObserver observer) {
        final BufferedImage buffered;
        if (img instanceof BufferedImage) {
            buffered = (BufferedImage) img;
        } else {
            buffered = new BufferedImage(w, h, 
                    BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g2 = buffered.createGraphics();
            g2.drawImage(img, 0, 0, w, h, null);
            g2.dispose();
        }
        javafx.scene.image.WritableImage fxImage = SwingFXUtils.toFXImage(
                buffered, null);
        this.gc.drawImage(fxImage, x, y, w, h);
        return true;
    }

    /**
     * Draws an image at the location {@code (x, y)}.  Note that the 
     * {@code observer} is ignored.
     * 
     * @param img  the image ({@code null} permitted...draws nothing).
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param bgcolor  the background color ({@code null} permitted).
     * @param observer  ignored.
     * 
     * @return {@code true} if there is no more drawing to be done. 
     */
    @Override
    public boolean drawImage(Image img, int x, int y, Color bgcolor, 
            ImageObserver observer) {
        if (img == null) {
            return true;
        }
        int w = img.getWidth(null);
        if (w < 0) {
            return false;
        }
        int h = img.getHeight(null);
        if (h < 0) {
            return false;
        }
        return drawImage(img, x, y, w, h, bgcolor, observer);
    }

    /**
     * Draws an image to the rectangle {@code (x, y, w, h)} (scaling it if
     * required), first filling the background with the specified color.  Note 
     * that the {@code observer} is ignored.
     * 
     * @param img  the image.
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param w  the width.
     * @param h  the height.
     * @param bgcolor  the background color ({@code null} permitted).
     * @param observer  ignored.
     * 
     * @return {@code true} if the image is drawn.      
     */
    @Override
    public boolean drawImage(Image img, int x, int y, int w, int h, 
            Color bgcolor, ImageObserver observer) {
        Paint saved = getPaint();
        setPaint(bgcolor);
        fillRect(x, y, w, h);
        setPaint(saved);
        return drawImage(img, x, y, w, h, observer);
    }

    /**
     * Draws part of an image (defined by the source rectangle 
     * {@code (sx1, sy1, sx2, sy2)}) into the destination rectangle
     * {@code (dx1, dy1, dx2, dy2)}.  Note that the {@code observer} 
     * is ignored in this implementation.
     * 
     * @param img  the image.
     * @param dx1  the x-coordinate for the top left of the destination.
     * @param dy1  the y-coordinate for the top left of the destination.
     * @param dx2  the x-coordinate for the bottom right of the destination.
     * @param dy2  the y-coordinate for the bottom right of the destination.
     * @param sx1  the x-coordinate for the top left of the source.
     * @param sy1  the y-coordinate for the top left of the source.
     * @param sx2  the x-coordinate for the bottom right of the source.
     * @param sy2  the y-coordinate for the bottom right of the source.
     * 
     * @return {@code true} if the image is drawn. 
     */
    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, 
            int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
        int w = dx2 - dx1;
        int h = dy2 - dy1;
        BufferedImage img2 = new BufferedImage(w, h, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img2.createGraphics();
        g2.drawImage(img, 0, 0, w, h, sx1, sy1, sx2, sy2, null);
        return drawImage(img2, dx1, dy1, null);
    }

    /**
     * Draws part of an image (defined by the source rectangle 
     * {@code (sx1, sy1, sx2, sy2)}) into the destination rectangle
     * {@code (dx1, dy1, dx2, dy2)}.  The destination rectangle is first
     * cleared by filling it with the specified {@code bgcolor}. Note that
     * the {@code observer} is ignored. 
     * 
     * @param img  the image.
     * @param dx1  the x-coordinate for the top left of the destination.
     * @param dy1  the y-coordinate for the top left of the destination.
     * @param dx2  the x-coordinate for the bottom right of the destination.
     * @param dy2  the y-coordinate for the bottom right of the destination.
     * @param sx1 the x-coordinate for the top left of the source.
     * @param sy1 the y-coordinate for the top left of the source.
     * @param sx2 the x-coordinate for the bottom right of the source.
     * @param sy2 the y-coordinate for the bottom right of the source.
     * @param bgcolor  the background color ({@code null} permitted).
     * @param observer  ignored.
     * 
     * @return {@code true} if the image is drawn. 
     */
    @Override
    public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, 
            int sx1, int sy1, int sx2, int sy2, Color bgcolor, 
            ImageObserver observer) {
        Paint saved = getPaint();
        setPaint(bgcolor);
        fillRect(dx1, dy1, dx2 - dx1, dy2 - dy1);
        setPaint(saved);
        return drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer);
    }

    /**
     * Draws a rendered image.
     * 
     * @param img  the rendered image.
     * @param xform  the transform.
     */
    @Override
    public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
        BufferedImage bi = convertRenderedImage(img);
        drawImage(bi, xform, null);
    }

    /**
     * Converts a rendered image to a {@code BufferedImage}.  This utility
     * method has come from a forum post by Jim Moore at:
     * <p>
     * <a href="http://www.jguru.com/faq/view.jsp?EID=114602">
     * http://www.jguru.com/faq/view.jsp?EID=114602</a>
     * 
     * @param img  the rendered image.
     * 
     * @return A buffered image. 
     */
    private static BufferedImage convertRenderedImage(RenderedImage img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;	
        }
        ColorModel cm = img.getColorModel();
        int width = img.getWidth();
        int height = img.getHeight();
        WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        Hashtable properties = new Hashtable();
        String[] keys = img.getPropertyNames();
        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                properties.put(keys[i], img.getProperty(keys[i]));
            }
        }
        BufferedImage result = new BufferedImage(cm, raster, 
                isAlphaPremultiplied, properties);
        img.copyData(raster);
        return result;
    }

    /**
     * Draws the renderable image.
     * 
     * @param img  the renderable image.
     * @param xform  the transform.
     */
    @Override
    public void drawRenderableImage(RenderableImage img, 
            AffineTransform xform) {
        RenderedImage ri = img.createDefaultRendering();
        drawRenderedImage(ri, xform);
    }

    /**
     * Draws an image with the specified transform. Note that the 
     * {@code observer} is ignored in this implementation.     
     * 
     * @param img  the image.
     * @param xform  the transform ({@code null} permitted).
     * @param obs  the image observer (ignored).
     * 
     * @return {@code true} if the image is drawn. 
     */
    @Override
    public boolean drawImage(Image img, AffineTransform xform, 
            ImageObserver obs) {
        AffineTransform savedTransform = getTransform();
        if (xform != null) {
            transform(xform);
        }
        boolean result = drawImage(img, 0, 0, obs);
        if (xform != null) {
            setTransform(savedTransform);
        }
        return result;
    }

    /**
     * Draws the image resulting from applying the {@code BufferedImageOp}
     * to the specified image at the location {@code (x, y)}.
     * 
     * @param img  the image.
     * @param op  the operation.
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     */
    @Override
    public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
        BufferedImage imageToDraw = op.filter(img, null);
        drawImage(imageToDraw, new AffineTransform(1f, 0f, 0f, 1f, x, y), null);
    }

    /**
     * Not yet implemented.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width of the area.
     * @param height  the height of the area.
     * @param dx  the delta x.
     * @param dy  the delta y.
     */
    @Override
    public void copyArea(int x, int y, int width, int height, int dx, int dy) {
        // FIXME: implement this, low priority
    }

    /**
     * This method does nothing.
     */
    @Override
    public void dispose() {
        // nothing to do
    }
 
    /**
     * Sets the attributes of the reusable {@link Rectangle2D} object that is
     * used by the {@link FXGraphics2D#drawRect(int, int, int, int)} and 
     * {@link FXGraphics2D#fillRect(int, int, int, int)} methods.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     * 
     * @return A rectangle (never {@code null}).
     */
    private Rectangle2D rect(int x, int y, int width, int height) {
        if (this.rect == null) {
            this.rect = new Rectangle2D.Double(x, y, width, height);
        } else {
            this.rect.setRect(x, y, width, height);
        }
        return this.rect;
    }

    /**
     * Sets the attributes of the reusable {@link RoundRectangle2D} object that
     * is used by the {@link #drawRoundRect(int, int, int, int, int, int)} and
     * {@link #fillRoundRect(int, int, int, int, int, int)} methods.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     * @param arcWidth  the arc width.
     * @param arcHeight  the arc height.
     * 
     * @return A round rectangle (never {@code null}).
     */
    private RoundRectangle2D roundRect(int x, int y, int width, int height, 
            int arcWidth, int arcHeight) {
        if (this.roundRect == null) {
            this.roundRect = new RoundRectangle2D.Double(x, y, width, height, 
                    arcWidth, arcHeight);
        } else {
            this.roundRect.setRoundRect(x, y, width, height, 
                    arcWidth, arcHeight);
        }
        return this.roundRect;
    }

    /**
     * Sets the attributes of the reusable {@link Arc2D} object that is used by
     * {@link #drawArc(int, int, int, int, int, int)} and 
     * {@link #fillArc(int, int, int, int, int, int)} methods.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     * @param startAngle  the start angle in degrees, 0 = 3 o'clock.
     * @param arcAngle  the angle (anticlockwise) in degrees.
     * 
     * @return An arc (never {@code null}).
     */
    private Arc2D arc(int x, int y, int width, int height, int startAngle, 
            int arcAngle) {
        if (this.arc == null) {
            this.arc = new Arc2D.Double(x, y, width, height, startAngle, 
                    arcAngle, Arc2D.OPEN);
        } else {
            this.arc.setArc(x, y, width, height, startAngle, arcAngle, 
                    Arc2D.OPEN);
        }
        return this.arc;
    }
            
    /**
     * Returns an {@link Ellipse2D} object that may be reused (so this instance
     * should be used for short term operations only). See the 
     * {@link #drawOval(int, int, int, int)} and 
     * {@link #fillOval(int, int, int, int)} methods for usage.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param width  the width.
     * @param height  the height.
     * 
     * @return An oval shape (never {@code null}).
     */
    private Ellipse2D oval(int x, int y, int width, int height) {
        if (this.oval == null) {
            this.oval = new Ellipse2D.Double(x, y, width, height);
        } else {
            this.oval.setFrame(x, y, width, height);
        }
        return this.oval;
    }
    
    /**
     * Returns {@code true} if the two {@code Paint} objects are equal 
     * OR both {@code null}.  This method handles
     * {@code GradientPaint}, {@code LinearGradientPaint} 
     * and {@code RadialGradientPaint} as special cases, since those classes do
     * not override the {@code equals()} method.
     *
     * @param p1  paint 1 ({@code null} permitted).
     * @param p2  paint 2 ({@code null} permitted).
     *
     * @return A boolean.
     */
    private static boolean paintsAreEqual(Paint p1, Paint p2) {
        if (p1 == p2) {
            return true;
        }
            
        // handle cases where either or both arguments are null
        if (p1 == null) {
            return (p2 == null);   
        }
        if (p2 == null) {
            return false;   
        }

        // handle cases...
        if (p1 instanceof Color && p2 instanceof Color) {
            return p1.equals(p2);
        }
        if (p1 instanceof GradientPaint && p2 instanceof GradientPaint) {
            GradientPaint gp1 = (GradientPaint) p1;
            GradientPaint gp2 = (GradientPaint) p2;
            return gp1.getColor1().equals(gp2.getColor1()) 
                    && gp1.getColor2().equals(gp2.getColor2())
                    && gp1.getPoint1().equals(gp2.getPoint1())    
                    && gp1.getPoint2().equals(gp2.getPoint2())
                    && gp1.isCyclic() == gp2.isCyclic()
                    && gp1.getTransparency() == gp1.getTransparency(); 
        } 
        if (p1 instanceof LinearGradientPaint 
                && p2 instanceof LinearGradientPaint) {
            LinearGradientPaint lgp1 = (LinearGradientPaint) p1;
            LinearGradientPaint lgp2 = (LinearGradientPaint) p2;
            return lgp1.getStartPoint().equals(lgp2.getStartPoint())
                    && lgp1.getEndPoint().equals(lgp2.getEndPoint()) 
                    && Arrays.equals(lgp1.getFractions(), lgp2.getFractions())
                    && Arrays.equals(lgp1.getColors(), lgp2.getColors())
                    && lgp1.getCycleMethod() == lgp2.getCycleMethod()
                    && lgp1.getColorSpace() == lgp2.getColorSpace()
                    && lgp1.getTransform().equals(lgp2.getTransform());
        } 
        if (p1 instanceof RadialGradientPaint 
                && p2 instanceof RadialGradientPaint) {
            RadialGradientPaint rgp1 = (RadialGradientPaint) p1;
            RadialGradientPaint rgp2 = (RadialGradientPaint) p2;
            return rgp1.getCenterPoint().equals(rgp2.getCenterPoint())
                    && rgp1.getRadius() == rgp2.getRadius() 
                    && rgp1.getFocusPoint().equals(rgp2.getFocusPoint())
                    && Arrays.equals(rgp1.getFractions(), rgp2.getFractions())
                    && Arrays.equals(rgp1.getColors(), rgp2.getColors())
                    && rgp1.getCycleMethod() == rgp2.getCycleMethod()
                    && rgp1.getColorSpace() == rgp2.getColorSpace()
                    && rgp1.getTransform().equals(rgp2.getTransform());
        }
        return p1.equals(p2);
    }

}
