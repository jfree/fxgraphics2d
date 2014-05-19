/* ===================
 * Orson Charts - Demo
 * ===================
 * 
 * Copyright (c) 2013, 2014, Object Refinery Limited.
 * All rights reserved.
 *
 * http://www.object-refinery.com/orsoncharts/index.html
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
 * Note that the above terms apply to the demo source only, and not the 
 * Orson Charts library.
 * 
 */

package com.orsoncharts.demo;

import java.awt.Color;

import com.orsoncharts.Colors;
import com.orsoncharts.renderer.category.CategoryColorSource;
import com.orsoncharts.renderer.category.StandardCategoryColorSource;
import com.orsoncharts.util.ArgChecks;

/**
 * A custom implementation of the {@link CategoryColorSource} interface.
 */
@SuppressWarnings("serial")
public class HighlightCategoryColorSource extends StandardCategoryColorSource {
    
    /** The row index of an item to highlight. */
    private int highlightRowIndex;
    
    /** The column index of an item to highlight. */
    private int highlightColumnIndex;
    
    /** The highlight color. */
    private Color highlightColor;

    /**
     * Creates a new instance with default colors.
     */
    public HighlightCategoryColorSource() {
        this(-1, -1, Color.RED, Colors.getDefaultColors());
    }
    
    /**
     * Creates a new instance with the supplied sequence of colors.  The
     * supplied array must have at least one entry, and all entries must be
     * non-<code>null</code>.
     * 
     * @param row  the row index of the item to highlight (or -1).
     * @param column  the column index of the item to highlight (or -1).
     * @param highlightColor  the highlight color (<code>null</code> not 
     *     permitted).
     * @param colors  the colors (<code>null</code> not permitted). 
     */
    public HighlightCategoryColorSource(int row, int column, 
            Color highlightColor, Color... colors) {
        super(colors);
        this.highlightRowIndex = row;
        this.highlightColumnIndex = column;
        this.highlightColor = highlightColor;
    }

    /**
     * Returns the row index of the item to be highlighted.  The default 
     * value is <code>-1</code>.
     * 
     * @return The row index. 
     */
    public int getHighlightRowIndex() {
        return highlightRowIndex;
    }

    /**
     * Sets the row index of the item to highlight (you can set this to -1 to
     * have no item highlighted).
     * 
     * @param index  the row index. 
     */
    public void setHighlightRowIndex(int index) {
        this.highlightRowIndex = index;
    }

    /**
     * Returns the column index of the item to be highlighted.  The default 
     * value is <code>-1</code>.
     * 
     * @return The row index. 
     */
    public int getHighlightColumnIndex() {
        return highlightColumnIndex;
    }

    /**
     * Sets the column index of the item to highlight (you can set this to -1 to
     * have no item highlighted).
     * 
     * @param index  the row index. 
     */
    public void setHighlightColumnIndex(int index) {
        this.highlightColumnIndex = index;
    }

    /**
     * Returns the highlight color.  The default value is 
     * <code>Color.RED</code>.
     * 
     * @return The highlight color (never <code>null</code>). 
     */
    public Color getHighlightColor() {
        return highlightColor;
    }

    /**
     * Sets the highlight color.
     * 
     * @param color  the color (<code>null</code> not permitted). 
     */
    public void setHighlightColor(Color color) {
        ArgChecks.nullNotPermitted(color, "color");
        this.highlightColor = color;
    }
 
    /**
     * Returns the color to use for the specified item.
     * 
     * @param series  the series index.
     * @param row  the row index.
     * @param column  the column index.
     * 
     * @return The color (never <code>null</code>). 
     */
    @Override
    public Color getColor(int series, int row, int column) {
        Color result = super.getColor(series, row, column);
        if (row == this.highlightRowIndex && column 
                == this.highlightColumnIndex) {
            result = this.highlightColor;
        }
        return result;
    }
    
    /**
     * Tests this color source for equality with an arbitrary object.
     * 
     * @param obj  the object (<code>null</code> permitted).
     * 
     * @return A boolean. 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof HighlightCategoryColorSource)) {
            return false;
        }
        HighlightCategoryColorSource that 
                = (HighlightCategoryColorSource) obj;
        if (this.highlightColumnIndex != that.highlightColumnIndex) {
            return false;
        }
        if (this.highlightRowIndex != that.highlightRowIndex) {
            return false;
        }
        if (!this.highlightColor.equals(that.highlightColor)) {
            return false;
        }
        return super.equals(obj);
    }

}