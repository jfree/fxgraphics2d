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

import com.orsoncharts.Range;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.renderer.xyz.StandardXYZColorSource;
import com.orsoncharts.renderer.xyz.XYZColorSource;

/**
 * A custom implementation of the {@link XYZColorSource} interface.
 */
@SuppressWarnings("serial")
public class HighlightXYZColorSource extends StandardXYZColorSource {
    
    private XYZDataset dataset;
    
    /** The range of x-values for the highlight region. */
    private Range xRange;
    
    /** The range of y-values for the highlight region. */
    private Range yRange;
    
    /** The range of z-values for the highlight region. */
    private Range zRange;
    
    /** The highlight color. */
    private Color highlightColor;
    
    /**
     * Creates a new instance.
     * 
     * @param dataset  the dataset.
     * @param highlightColor
     * @param xRange
     * @param yRange
     * @param zRange
     * @param colors 
     */
    public HighlightXYZColorSource(XYZDataset dataset, Color highlightColor, 
            Range xRange, Range yRange, Range zRange, Color... colors) {
        super(colors);
        this.dataset = dataset;
        this.xRange = xRange;
        this.yRange = yRange;
        this.zRange = zRange;
        this.highlightColor = highlightColor;
    }

    @Override
    public Color getColor(int series, int item) {
        double x = this.dataset.getX(series, item);
        double y = this.dataset.getY(series, item);
        double z = this.dataset.getZ(series, item);
        if (this.xRange.contains(x) && this.yRange.contains(y) 
                && this.zRange.contains(z)) {
            return this.highlightColor; 
        } else {
            return super.getColor(series, item);
        }
    }

}