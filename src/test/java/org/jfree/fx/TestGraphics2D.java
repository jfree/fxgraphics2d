/* ============
 * FXGraphics2D
 * ============
 * 
 * (C)opyright 2014-present, by David Gilbert.
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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some tests for a Graphics2D implementation.  All tests should pass with the
 * Graphics2D instance from a BufferedImage (which we can treat as a reference
 * implementation).
 */
public class TestGraphics2D {

    /**
     * Change this to true to test against a reference Graphics2D
     * implementation from the JDK.  This is useful to verify that the tests
     * are correct.
     */
    private static final boolean TEST_REFERENCE_IMPLEMENTATION = false;

    private Graphics2D g2;
    
    @BeforeEach
    public void setUp() {
        if (TEST_REFERENCE_IMPLEMENTATION) {
            // to test a reference implementation, use this Graphics2D from a
            // BufferedImage in the JDK
            BufferedImage img = new BufferedImage(10, 20, BufferedImage.TYPE_INT_ARGB);
            this.g2 = img.createGraphics();
        } else {
            Canvas canvas = new Canvas(640, 480);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            this.g2 = new FXGraphics2D(gc);
        }
    }
    
    /**
     * Checks that the default transform is an identity transform.
     */
    @Test
    public void checkDefaultTransform() {
        assertEquals(new AffineTransform(), g2.getTransform());
    }
    
    /**
     * Modifying the transform returned by the Graphics2D should not affect
     * the state of the Graphics2D.  In order for that to happen, the method
     * should be returning a copy of the actual transform object.
     */
    @Test
    public void checkGetTransformSafety() {
        AffineTransform t = g2.getTransform();
        t.rotate(Math.PI);
        assertNotEquals(t, g2.getTransform());
        assertEquals(new AffineTransform(), g2.getTransform());
    }
    
    /**
     * A basic check that setTransform() does indeed update the transform.
     */
    @Test
    public void setTransform() {
        AffineTransform t = new AffineTransform(1, 2, 3, 4, 5, 6);
        g2.setTransform(t);
        assertEquals(t, g2.getTransform());
  
        t.setTransform(6, 5, 4, 3, 2, 1);
        g2.setTransform(t);
        assertEquals(t, g2.getTransform());
        
        // in spite of the docs saying that null is accepted this gives
        // a NullPointerException with SunGraphics2D.
        //g2.setTransform(null);
        //assertEquals(new AffineTransform(), g2.getTransform());
    }
    
    /**
     * When calling setTransform() the caller passes in an AffineTransform 
     * instance.  If the caller retains a reference to the AffineTransform 
     * and subsequently modifies it, we don't want the Graphics2D object to
     * be affected...so it should be making an internal copy of the 
     * AffineTransform.
     */
    @Test
    public void checkSetTransformSafety() {
        AffineTransform t = AffineTransform.getTranslateInstance(1.0, 2.0);
        g2.setTransform(t);
        assertEquals(t, g2.getTransform());
        t.setToRotation(Math.PI);
        assertNotEquals(t, g2.getTransform());
    }
    
    @Test
    public void checkSetNonInvertibleTransform() {
        AffineTransform t = AffineTransform.getScaleInstance(0.0, 0.0);
        g2.setTransform(t);
        assertEquals(t, g2.getTransform());
        
        // after setting the clip, we cannot retrieve it while the transform
        // is non-invertible...
        Rectangle2D clip = new Rectangle2D.Double(1, 2, 3, 4);
        g2.setClip(clip);
        assertNull(g2.getClip());
        
        g2.setTransform(new AffineTransform());
        assertEquals(new Rectangle2D.Double(0, 0, 0, 0), 
                g2.getClip().getBounds2D());
    }

    /**
     * A check for a call to transform() with a rotation, that follows a
     * translation.
     */
    @Test
    public void checkTransform() {
        AffineTransform t = new AffineTransform();
        this.g2.setTransform(t);
        this.g2.translate(30, 30);
        AffineTransform rt = AffineTransform.getRotateInstance(Math.PI / 2.0, 
                300, 200);
        this.g2.transform(rt);
        t = this.g2.getTransform();
        assertEquals(0, t.getScaleX(), EPSILON);
        assertEquals(0, t.getScaleY(), EPSILON);
        assertEquals(-1.0, t.getShearX(), EPSILON);
        assertEquals(1.0, t.getShearY(), EPSILON);
        assertEquals(530.0, t.getTranslateX(), EPSILON);
        assertEquals(-70, t.getTranslateY(), EPSILON);
    }
    
    @Test
    public void checkTransformNull() {
        try {
            this.g2.transform(null);
            fail("Expected a NullPointerException.");
        } catch (NullPointerException e) {
            // this exception is expected
        }
    }
    
    /**
     * Basic checks for the scale(x, y) method.
     */
    @Test
    public void scale() {
        g2.scale(0.5, 2.0);
        assertEquals(AffineTransform.getScaleInstance(0.5, 2.0), 
                g2.getTransform());
        g2.scale(2.0, -1.0);
        assertEquals(AffineTransform.getScaleInstance(1.0, -2.0), 
                g2.getTransform());    
    }
    
    /**
     * Checks that a call to scale(x, y) on top of an existing translation
     * gives the correct values.
     */
    @Test
    public void translateFollowedByScale() {
        g2.translate(2, 3);
        assertEquals(AffineTransform.getTranslateInstance(2.0, 3.0), 
                g2.getTransform());
        g2.scale(10, 20);
        assertEquals(new AffineTransform(10.0, 0.0, 0.0, 20.0, 2.0, 3.0),
                g2.getTransform());
    }
    
    /**
     * Checks that a call to translate(x, y) on top of an existing scale
     * gives the correct values.
     */    
    @Test
    public void scaleFollowedByTranslate() {
        g2.scale(2, 2);
        assertEquals(AffineTransform.getScaleInstance(2.0, 2.0), 
                g2.getTransform());
        g2.translate(10, 20);
        assertEquals(new AffineTransform(2.0, 0.0, 0.0, 2.0, 20.0, 40.0),
                g2.getTransform());
    }
    
    private static final double EPSILON = 0.000000001;
    
    @Test
    public void scaleFollowedByRotate() {
        g2.scale(2, 2);
        assertEquals(AffineTransform.getScaleInstance(2.0, 2.0), 
                g2.getTransform());
        g2.rotate(Math.PI / 3);
        AffineTransform t = g2.getTransform();
        assertEquals(1.0, t.getScaleX(), EPSILON);
        assertEquals(1.0, t.getScaleY(), EPSILON);
        assertEquals(-1.7320508075688772, t.getShearX(), EPSILON);
        assertEquals(1.7320508075688772, t.getShearY(), EPSILON);
        assertEquals(0.0, t.getTranslateX(), EPSILON);
        assertEquals(0.0, t.getTranslateY(), EPSILON);
    }
    
    @Test
    public void rotateFollowedByScale() {
        g2.rotate(Math.PI);
        assertEquals(AffineTransform.getRotateInstance(Math.PI), 
                g2.getTransform());
        g2.scale(2.0, 2.0);
        assertEquals(new AffineTransform(-2.0, 0.0, 0.0, -2.0, 0.0, 0.0),
                g2.getTransform());
    }
    
    /**
     * Checks that the getClip() method returns a different object than what
     * was passed to setClip(), and that multiple calls to getClip() return
     * a new object each time.
     */
    @Test
    public void checkGetClipSafety() {
        Rectangle2D r = new Rectangle2D.Double(0, 0, 1, 1);
        this.g2.setClip(r);
        Shape s = this.g2.getClip();
        assertNotSame(r, s);
        Shape s2 = this.g2.getClip();
        assertNotSame(s, s2);
    }
    
    /**
     * The default user clip should be {@code null}.
     */
    @Test
    public void checkDefaultClip() {
        assertNull(g2.getClip(), "Default user clip should be null.");
    }
    
    /**
     * Checks that getClipBounds() is returning an integer approximation of
     * the bounds.
     */
    @Test
    public void checkGetClipBounds() {
        Rectangle2D r = new Rectangle2D.Double(0.25, 0.25, 0.5, 0.5);
        this.g2.setClip(r);
        assertEquals(new Rectangle(0, 0, 1, 1), this.g2.getClipBounds());       
    }

    /**
     * Checks that getClipBounds() returns {@code null} when the clip is
     * {@code null}.
     */
    @Test
    public void checkGetClipBoundsWhenClipIsNull() {
        this.g2.setClip(null);
        assertNull(this.g2.getClipBounds());
    }

    /**
     * Simple check that the clip() methods creates an intersection with the
     * existing clip region.
     */
    @Test
    public void checkClip() {
        Rectangle2D r = new Rectangle2D.Double(1.0, 1.0, 3.0, 3.0);
        this.g2.setClip(r);
        this.g2.clip(new Rectangle2D.Double(0.0, 0.0, 2.0, 2.0));
        assertEquals(new Rectangle2D.Double(1.0, 1.0, 1.0, 1.0), 
                this.g2.getClip().getBounds2D());
    }

    /**
     * Check that if the user clip is non-intersecting with the existing clip, then
     * the clip is empty.
     */
    @Test
    public void checkNonIntersectingClip() {
        Rectangle2D r = new Rectangle2D.Double(1.0, 1.0, 3.0, 3.0);
        this.g2.setClip(r);
        this.g2.clip(new Rectangle2D.Double(5.0, 5.0, 1.0, 1.0));
        assertTrue(this.g2.getClip().getBounds2D().isEmpty());
    }

    /**
     * After applying a scale transformation, getClip() will return a
     * modified clip.
     */
    @Test
    public void checkClipAfterScaling() {
        Rectangle2D r = new Rectangle2D.Double(1, 2, 3, 0.5);
        this.g2.setClip(r);
        assertEquals(r, this.g2.getClip().getBounds2D());
        this.g2.scale(0.5, 2.0);
        assertEquals(new Rectangle2D.Double(2, 1, 6, 0.25), 
                this.g2.getClip().getBounds2D());

        // check that we get a good intersection when clipping after the
        // scaling has been done...
        r = new Rectangle2D.Double(3, 0, 2, 2);
        this.g2.clip(r);
        assertEquals(new Rectangle2D.Double(3, 1, 2, 0.25), 
                this.g2.getClip().getBounds2D());
    }
    
    /** 
     * Translating will change the existing clip.
     */
    @Test
    public void checkClipAfterTranslate() {
        Rectangle2D clip = new Rectangle2D.Double(0.0, 0.0, 1.0, 1.0);
        this.g2.setClip(clip);
        assertEquals(clip, this.g2.getClip().getBounds2D());
        this.g2.translate(1.0, 2.0);
        assertEquals(new Rectangle(-1, -2, 1 ,1), 
                this.g2.getClip().getBounds2D());
    }
    
    @Test
    public void checkSetClipAfterTranslate() {
        this.g2.translate(1.0, 2.0);
        this.g2.setClip(0, 0, 1, 1);
        assertEquals(new Rectangle(0, 0, 1, 1), this.g2.getClip().getBounds());
        this.g2.translate(1.0, 2.0);
        assertEquals(new Rectangle(-1, -2, 1, 1), this.g2.getClip().getBounds());
    }
    
    /**
     * Transforming will change the reported clipping shape.
     */
    @Test
    public void checkClipAfterTransform() {
        Rectangle2D clip = new Rectangle2D.Double(0, 0, 1, 1);
        this.g2.setClip(clip);
        assertEquals(clip, this.g2.getClip().getBounds2D());
        
        this.g2.transform(AffineTransform.getRotateInstance(Math.PI));
        assertEquals(new Rectangle(-1, -1, 1 ,1), 
                this.g2.getClip().getBounds2D());
        
        this.g2.setTransform(new AffineTransform());
        assertEquals(clip, this.g2.getClip().getBounds2D());     
    }
    
    /**
     * Clipping with a line makes no sense, but the API allows it so we should
     * not fail.  In fact, running with a JDK Graphics2D (from a BufferedImage)
     * it seems that the bounding rectangle of the line is used for clipping...
     * does that make sense?  Switching off the test for now.
     */
    @Test
    @Disabled
    public void checkClipWithLine2D() {
        Rectangle2D r = new Rectangle2D.Double(1.0, 1.0, 3.0, 3.0);
        this.g2.setClip(r);
        this.g2.clip(new Line2D.Double(1.0, 2.0, 3.0, 4.0));
        //assertEquals(new Rectangle2D.Double(1.0, 2.0, 2.0, 2.0), 
        //        this.g2.getClip().getBounds2D());
        //assertTrue(this.g2.getClip().getBounds2D().isEmpty());        
    }
    
    /**
     * Clipping with a null argument is "not recommended" according to the
     * latest API docs (https://bugs.java.com/bugdatabase/view_bug.do?bug_id=6206189).
     */
    @Test
    public void checkClipWithNullArgument() {

        // when there is a current clip set, a null pointer exception is expected
        this.g2.setClip(new Rectangle2D.Double(1.0, 2.0, 3.0, 4.0));
        Exception exception = assertThrows(NullPointerException.class, () -> this.g2.clip(null));

        this.g2.setClip(null);
        try {
            this.g2.clip(null);
        } catch (Exception e) {
            fail("No exception expected.");
        }
    }

    /**
     * A simple check for a call to clipRect().
     */
    @Test
    public void checkClipRect() {
        Rectangle2D clip = new Rectangle2D.Double(0, 0, 5, 5);
        this.g2.setClip(clip);
        
        this.g2.clipRect(2, 1, 4, 2);
        assertEquals(new Rectangle(2, 1, 3, 2), 
                g2.getClip().getBounds2D());
    }
    
    @Test
    public void checkClipRectParams() {
        Rectangle2D clip = new Rectangle2D.Double(0, 0, 5, 5);
        this.g2.setClip(clip);
        
        // negative width
        this.g2.clipRect(2, 1, -4, 2);
        assertTrue(this.g2.getClip().getBounds2D().isEmpty());
        
        // negative height
        this.g2.setClip(clip);
        this.g2.clipRect(2, 1, 4, -2);
        assertTrue(this.g2.getClip().getBounds2D().isEmpty());    
    }

    @Test
    public void checkDrawStringWithNullString() {
        try {
            g2.drawString((String) null, 1, 2);
            fail("There should be a NullPointerException.");
        } catch (NullPointerException e) {
            // this exception is expected
        }
        try {
            g2.drawString((String) null, 1.0f, 2.0f);
            fail("There should be a NullPointerException.");
        } catch (NullPointerException e) {
            // this exception is expected
        }
    }
    
    @Test
    public void checkDrawStringWithEmptyString() {
        // this should not cause any exception 
        g2.drawString("", 1, 2);
    }

    /**
     * Some checks for the create() method.
     */
    @Test 
    public void checkCreate() {
        this.g2.setClip(new Rectangle(1, 2, 3, 4));
        Graphics2D copy = (Graphics2D) g2.create();
        assertEquals(copy.getBackground(), g2.getBackground());
        assertEquals(copy.getClip().getBounds2D(), 
                g2.getClip().getBounds2D());
        assertEquals(copy.getColor(), g2.getColor());
        assertEquals(copy.getComposite(), g2.getComposite());
        assertEquals(copy.getFont(), g2.getFont());
        assertEquals(copy.getRenderingHints(), g2.getRenderingHints());        
        assertEquals(copy.getStroke(), g2.getStroke()); 
        assertEquals(copy.getTransform(), g2.getTransform()); 
    }
    
    /**
     * The setPaint() method allows a very minor state leakage in the sense 
     * that it is possible to modify a GradientPaint externally after a call
     * to the setPaint() method and have it impact the state of the 
     * Graphics2D implementation.  Avoiding this would require cloning the
     * Paint object, but there is no good way to do that for an arbitrary
     * Paint instance.  
     */
    @Test
    public void checkSetPaintSafety() {
        Point2D pt1 = new Point2D.Double(1.0, 2.0);
        Point2D pt2 = new Point2D.Double(3.0, 4.0);
        GradientPaint gp = new GradientPaint(pt1, Color.RED, pt2, Color.BLUE);
        this.g2.setPaint(gp);
        assertEquals(gp, this.g2.getPaint());
        assertSame(gp, this.g2.getPaint());
        pt1.setLocation(7.0, 7.0);
        assertEquals(gp, this.g2.getPaint());
    }
    
    /**
     * According to the Javadocs, setting the paint to null should have no 
     * impact on the current paint (that is, the call is silently ignored).
     */
    @Test
    public void checkSetPaintNull() {
        this.g2.setPaint(Color.RED);
        // this next call should have no impact
        this.g2.setPaint(null);
        assertEquals(Color.RED, this.g2.getPaint());
    }
    
    /**
     * Passing a Color to setPaint() also updates the color, but not the
     * background color.
     */
    @Test
    public void checkSetPaintAlsoUpdatesColorButNotBackground() {
        Color existingBackground = this.g2.getBackground();
        this.g2.setPaint(Color.MAGENTA);
        assertEquals(Color.MAGENTA, this.g2.getPaint());
        assertEquals(Color.MAGENTA, this.g2.getColor());
        assertEquals(existingBackground, this.g2.getBackground());
    }
    
    /**
     * If setPaint() is called with an argument that is not an instance of
     * Color, then the existing color remains unchanged.
     */
    @Test
    public void checkSetPaintDoesNotUpdateColor() {
        GradientPaint gp = new GradientPaint(1.0f, 2.0f, Color.RED,
                3.0f, 4.0f, Color.BLUE);
        this.g2.setColor(Color.MAGENTA);
        this.g2.setPaint(gp);
        assertEquals(gp, this.g2.getPaint());
        assertEquals(Color.MAGENTA, this.g2.getColor());
    }

    /**
     * Verifies that setting the old AWT color attribute also updates the
     * Java2D paint attribute.
     *
     * @see #checkSetPaintAlsoUpdatesColorButNotBackground()
     */
    @Test
    public void checkSetColorAlsoUpdatesPaint() {
        this.g2.setColor(Color.MAGENTA);
        assertEquals(Color.MAGENTA, this.g2.getPaint());
        assertEquals(Color.MAGENTA, this.g2.getColor());
    }
    
    /**
     * The behaviour of the reference implementation has been observed as
     * ignoring null.  This matches the documented behaviour of the
     * setPaint() method.
     */
    @Test
    public void checkSetColorNull() {
        this.g2.setColor(Color.RED);
        this.g2.setColor(null);
        assertEquals(Color.RED, this.g2.getColor());
    }
    
    /**
     * Setting the background color does not change the color or paint.
     */
    @Test
    public void checkSetBackground() {
        this.g2.setBackground(Color.CYAN);
        assertEquals(Color.CYAN, this.g2.getBackground());
        assertNotEquals(Color.CYAN, this.g2.getColor());
        assertNotEquals(Color.CYAN, this.g2.getPaint());
    }

    /**
     * The behaviour of the reference implementation has been observed as
     * allowing null (this is inconsistent with the behaviour of setColor()).
     */
    @Test
    public void checkSetBackgroundNull() {
        this.g2.setBackground(Color.RED);
        this.g2.setBackground(null);
        assertNull(this.g2.getBackground());
    }
    
    /**
     * Since the setBackground() method is allowing null, we should ensure
     * that the clearRect() method doesn't fail in this case.  With no
     * background color, the clearRect() method should be a no-op but there
     * is no easy way to test for that.
     */
    @Test
    public void checkClearRectWithNullBackground() {
        this.g2.setBackground(null);
        this.g2.clearRect(1, 2, 3, 4);
        //no exceptions and we're good
    }

    /**
     * In the reference implementation, setting a null composite has been 
     * observed to throw an IllegalArgumentException.
     */
    @Test
    public void checkSetCompositeNull() {
        try {
            this.g2.setComposite(null);
            fail("Expected an IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // this exception is expected in the test   
        }
    }
    
    @Test
    public void checkSetStrokeNull() {
        try {
            this.g2.setStroke(null);
            fail("Expected an IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // this exception is expected in the test   
        }
    }
    
    /**
     * Basic check of set then get.
     */
    @Test
    public void checkSetRenderingHint() {
        this.g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, 
                RenderingHints.VALUE_STROKE_PURE);
        assertEquals(RenderingHints.VALUE_STROKE_PURE, 
                this.g2.getRenderingHint(RenderingHints.KEY_STROKE_CONTROL));
    }
    
    /**
     * The reference implementation has been observed to throw a 
     * NullPointerException when the key is null.
     */
    @Test
    public void checkSetRenderingHintWithNullKey() {
        try {
            this.g2.setRenderingHint(null, "XYZ");
            fail("NullPointerException is expected here.");
        } catch (NullPointerException e) {
            // this is expected
        }
    }
    
    /**
     * The reference implementation has been observed to accept a null key 
     * and return null in that case.
     */
    @Test
    public void checkGetRenderingHintWithNullKey() {
        assertNull(this.g2.getRenderingHint(null));
    }
    
    /**
     * Check setting a hint with a value that doesn't match the key.
     */
    @Test
    public void checkSetRenderingHintWithInconsistentValue() {
        try {
            this.g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, 
                    RenderingHints.VALUE_ANTIALIAS_DEFAULT);
            fail("Expected an IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            // we expect this exception
        }
    }
    
    /**
     * A call to getRenderingHints() is returning a copy of the hints, so 
     * changing it will not affect the state of the Graphics2D instance.
     */
    @Test
    public void checkGetRenderingHintsSafety() {
        this.g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_OFF);
        RenderingHints hints = this.g2.getRenderingHints();
        hints.put(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        assertEquals(RenderingHints.VALUE_ANTIALIAS_OFF, 
                this.g2.getRenderingHint(RenderingHints.KEY_ANTIALIASING));   
    }
    
    @Test
    public void checkSetRenderingHintsNull() {
        try {
            this.g2.setRenderingHints(null);
            fail("NullPointerException expected.");
        } catch (NullPointerException e) {
            // this is expected
        }
    }
    
    @Test
    public void checkHit() {
        Shape shape = new Rectangle2D.Double(0.0, 0.0, 1.0, 1.0);
        Rectangle r = new Rectangle(2, 2, 2, 2);
        assertFalse(this.g2.hit(r, shape, false));
        this.g2.scale(3.0, 3.0);
        assertTrue(this.g2.hit(r, shape, false));
    }
    
    @Test
    public void checkHitForOutline() {
        Shape shape = new Rectangle2D.Double(0.0, 0.0, 3.0, 3.0);
        Rectangle r = new Rectangle(1, 1, 1, 1);
        assertFalse(this.g2.hit(r, shape, true));
        this.g2.scale(0.5, 0.5);
        // now the rectangle is entirely inside the shape, but does not touch
        // the outline...
        assertTrue(this.g2.hit(r, shape, true));
    } 
    
    /**
     * We have observed in the reference implementation that setting the font
     * to null does not change the current font setting.
     */
    @Test
    public void checkSetFontNull() {
        Font f = new Font("Serif", Font.PLAIN, 8);
        this.g2.setFont(f);
        assertEquals(f, this.g2.getFont());
        this.g2.setFont(null);
        assertEquals(f, this.g2.getFont());
    }
    
    @Test
    public void checkDefaultStroke() {
        BasicStroke s = (BasicStroke) this.g2.getStroke();
        assertEquals(BasicStroke.CAP_SQUARE, s.getEndCap());
        assertEquals(1.0f, s.getLineWidth(), EPSILON);
        assertEquals(BasicStroke.JOIN_MITER, s.getLineJoin());
    }
    
    /**
     * Check that a null GlyphVector throws a {@code NullPointerException}.
     */
    @Test
    public void drawGlyphVectorNull() {
        try {
            g2.drawGlyphVector(null, 10, 10);
            fail("Expecting a NullPointerException.");
        } catch (NullPointerException e) {
            // expected
        }
    }
    
    /**
     * Check the shear() method.
     */
    @Test
    public void shear() {
        g2.setTransform(new AffineTransform());
        g2.shear(2.0, 3.0);
        assertEquals(new AffineTransform(1, 3, 2, 1, 0, 0), g2.getTransform());
    }
    
    /**
     * Checks a call to translate() followed by a shear().
     */
    @Test
    public void shearFollowingTranslate() {
        g2.setTransform(new AffineTransform());
        g2.translate(10.0, 20.0);
        g2.shear(2.0, 3.0);
        assertEquals(new AffineTransform(1, 3, 2, 1, 10, 20), g2.getTransform());
    }
    
    @Test
    public void drawImageWithNullBackground() {
        Image img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        g2.drawImage(img, 10, 10, null, null);
        assertTrue(true); // won't get here if there's an exception above
    }
    
    /**
     * https://github.com/jfree/jfreesvg/issues/6
     */
    @Test
    public void drawImageWithNullTransform() {
        Image img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        g2.drawImage(img, null, null);
        assertTrue(true); // won't get here if there's an exception above
    }
    
    @Test
    public void drawImageWithNullImage() {
        // API docs say method does nothing if img is null
        // still seems to return true
        assertTrue(g2.drawImage(null, 10, 20, null));
        assertTrue(g2.drawImage(null, 10, 20, 30, 40, null));
        assertTrue(g2.drawImage(null, 10, 20, Color.YELLOW, null));
        assertTrue(g2.drawImage(null, 1, 2, 3, 4, Color.RED, null));
        assertTrue(g2.drawImage(null, 1, 2, 3, 4, 5, 6, 7, 8, null));
        assertTrue(g2.drawImage(null, 1, 2, 3, 4, 5, 6, 7, 8, Color.RED, null));
    }
    
    @Test
    public void drawImageWithNegativeDimensions() {
        Image img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        assertTrue(g2.drawImage(img, 1, 2, -10, 10, null));
        assertTrue(g2.drawImage(img, 1, 2, 10, -10, null)); 
    }

    /**
     * Check that the color is not changed by setting a clip.  In some
     * implementations the clip is saved/restored as part of the overall
     * graphics state so clipping can impact other attributes.
     */
    @Test
    public void checkColorAfterSetClip() {
        this.g2.setColor(Color.RED);
        assertEquals(Color.RED, this.g2.getColor());
        this.g2.setClip(0, 0, 10, 10);
        assertEquals(Color.RED, this.g2.getColor());
        this.g2.setColor(Color.BLUE);
        assertEquals(Color.BLUE, this.g2.getColor());
        this.g2.setClip(0, 0, 20, 20);
        assertEquals(Color.BLUE, this.g2.getColor());
    }

    /**
     * See https://github.com/jfree/fxgraphics2d/issues/6
     */
    @Test
    public void checkFontAfterSetClip() {
        this.g2.setFont(new Font(Font.DIALOG, Font.BOLD, 12));
        assertEquals(new Font(Font.DIALOG, Font.BOLD, 12), this.g2.getFont());
        this.g2.setClip(0, 0, 10, 10);
        assertEquals(new Font(Font.DIALOG, Font.BOLD, 12), this.g2.getFont());
        this.g2.setFont(new Font(Font.DIALOG, Font.BOLD, 24));
        assertEquals(new Font(Font.DIALOG, Font.BOLD, 24), this.g2.getFont());
        this.g2.setClip(0, 0, 20, 20);
        assertEquals(new Font(Font.DIALOG, Font.BOLD, 24), this.g2.getFont());
    }

    /**
     * See https://github.com/jfree/fxgraphics2d/issues/6
     */
    @Test
    public void checkStrokeAfterSetClip() {
        this.g2.setStroke(new BasicStroke(1.0f));
        assertEquals(new BasicStroke(1.0f), this.g2.getStroke());
        this.g2.setClip(0, 0, 10, 10);
        assertEquals(new BasicStroke(1.0f), this.g2.getStroke());
        this.g2.setStroke(new BasicStroke(2.0f));
        assertEquals(new BasicStroke(2.0f), this.g2.getStroke());
        this.g2.setClip(0, 0, 20, 20);
        assertEquals(new BasicStroke(2.0f), this.g2.getStroke());
    }
    
    /** 
     * A test to check whether setting a transform on the Graphics2D affects
     * the results of text measurements performed via getFontMetrics().
     */
    @Test
    public void testGetFontMetrics() {
        Font f = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
        FontMetrics fm = this.g2.getFontMetrics(f);
        int w = fm.stringWidth("ABC");
        Rectangle2D bounds = fm.getStringBounds("ABC", this.g2);
        
        // after scaling, the string width is not changed
        this.g2.setTransform(AffineTransform.getScaleInstance(3.0, 2.0));
        fm = this.g2.getFontMetrics(f);
        assertEquals(w, fm.stringWidth("ABC"));
        assertEquals(bounds.getWidth(), fm.getStringBounds("ABC", this.g2).getWidth(), EPSILON);
    }

    @Test
    public void drawImageWithNullImageOp() {
        BufferedImage img = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        g2.drawImage(img, null, 2, 3);
        assertTrue(true); // won't get here if there's an exception above        
    }
    
    /**
     * API docs say the method does nothing when called with a null image.
     */
    @Test 
    public void drawRenderedImageWithNullImage() {
        g2.drawRenderedImage(null, AffineTransform.getTranslateInstance(0, 0));
        assertTrue(true); // won't get here if there's an exception above                
    }

    /**
     * Filling and/or stroking a Rectangle2D with a negative width will not display anything but
     * should not throw an exception.
     */
    @Test
    public void fillOrStrokeRectangleWithNegativeWidthMustNotFail() {
        g2.draw(new Rectangle2D.Double(0, 0, 0, 10));
        g2.draw(new Rectangle2D.Double(0, 0, -10, 10));
        g2.fill(new Rectangle2D.Double(0, 0, 0, 10));
        g2.fill(new Rectangle2D.Double(0, 0, -10, 10));
        assertTrue(true); // won't get here if there's an exception above
    }

    /**
     * Filling and/or stroking a Rectangle2D with a negative height will not display anything but
     * should not throw an exception.
     */
    @Test
    public void fillOrStrokeRectangleWithNegativeHeightMustNotFail() {
        g2.draw(new Rectangle2D.Double(0, 0, 0, 10));
        g2.draw(new Rectangle2D.Double(0, 0, -10, 10));
        g2.fill(new Rectangle2D.Double(0, 0, 0, 10));
        g2.fill(new Rectangle2D.Double(0, 0, -10, 10));
        assertTrue(true); // won't get here if there's an exception above
    }

    @Test
    public void checkClipAfterCreate() {
        this.g2.setClip(10, 20, 30, 40);
        assertEquals(new Rectangle(10, 20, 30, 40), g2.getClip().getBounds2D());

        Graphics2D g2copy = (Graphics2D) this.g2.create();
        g2copy.clipRect(11, 21, 10, 10);
        assertEquals(new Rectangle(11, 21, 10, 10), g2copy.getClip().getBounds2D());
        g2copy.dispose();
        assertEquals(new Rectangle(10, 20, 30, 40), g2.getClip().getBounds2D());
    }

}
