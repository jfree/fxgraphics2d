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

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Colors;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.renderer.category.AreaRenderer3D;

/**
 * 3D area chart configuration for demo applications.
 */
public class AreaChart3D2 {
  
    /**
     * Creates an area chart using the specified <code>dataset</code>.
     * 
     * @param dataset  the dataset.
     * 
     * @return An area chart. 
     */
    public static Chart3D createChart(CategoryDataset3D dataset) {
        Chart3D chart = Chart3DFactory.createAreaChart("AreaChart3DDemo2", 
                "Chart created with Orson Charts", dataset, "Row", 
                "Category", "Value");
        chart.setChartBoxColor(new Color(255, 255, 255, 128));
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.getRowAxis().setVisible(false);
        AreaRenderer3D renderer = (AreaRenderer3D) plot.getRenderer();
        renderer.setBaseColor(Color.GRAY);
        renderer.setColors(Colors.getSAPMultiColor());
        return chart;
    }
    
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    public static CategoryDataset3D createDataset() {    
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();
                
        DefaultKeyedValues<Number> s1 = new DefaultKeyedValues<Number>();
        s1.put("A", -1);
        s1.put("B", -4);
        s1.put("C", -9);
        s1.put("D", -5);
        s1.put("E", -5);
        s1.put("F", -2);
        s1.put("G", -4);
        s1.put("H", -7);
        s1.put("I", -3);
        s1.put("J", -1);
        dataset.addSeriesAsRow("Series 1", s1);
        
        DefaultKeyedValues<Number> s2 = new DefaultKeyedValues<Number>();
        s2.put("A", 1);
        s2.put("B", 12);
        s2.put("C", 14);
        s2.put("D", 7);
        s2.put("E", 2);
        s2.put("F", -9);
        s2.put("G", -14);
        s2.put("H", 0);
        s2.put("I", 12);
        s2.put("J", 4);
        dataset.addSeriesAsRow("Series 2", s2);
        
        DefaultKeyedValues<Number> s3 = new DefaultKeyedValues<Number>();
        s3.put("A", 5);
        s3.put("B", 13);
        s3.put("C", 19);
        s3.put("D", 11);
        s3.put("E", 10);
        s3.put("F", 5);
        s3.put("G", -7);
        s3.put("H", -3);
        s3.put("I", -15);
        s3.put("J", -20);
        dataset.addSeriesAsRow("Series 3", s3);

        DefaultKeyedValues<Number> s4 = new DefaultKeyedValues<Number>();
        s4.put("A", 5);
        s4.put("B", 18);
        s4.put("C", 20);
        s4.put("D", 17);
        s4.put("E", 11);
        s4.put("F", 19);
        s4.put("G", 25);
        s4.put("H", 12);
        s4.put("I", 4);
        s4.put("J", 2);
        dataset.addSeriesAsRow("Series 4", s4);

        return dataset;
    }

}