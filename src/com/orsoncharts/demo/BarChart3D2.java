/* ===================
 * Orson Charts - Demo
 * ===================
 * 
 * Copyright (c) 2013-2016, Object Refinery Limited.
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
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.axis.StandardCategoryAxis3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.label.StandardCategoryItemLabelGenerator;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.util.Orientation;
import com.orsonpdf.PDFHints;
import java.awt.BasicStroke;

/**
 * 3D bar chart configuration for demo applications.
 */
public class BarChart3D2 {
  
    /**
     * Creates a bar chart with the supplied dataset.
     * 
     * @param dataset  the dataset.
     * 
     * @return A bar chart. 
     */
    public static Chart3D createChart(CategoryDataset3D dataset) {
        Chart3D chart = Chart3DFactory.createBarChart(
                "Average Maximum Temperature", 
                "http://www.worldclimateguide.co.uk/climateguides/", dataset, 
                null, null, "Temp °C");
        
        // we use the following hint to render text as vector graphics
        // rather than text when exporting to PDF...otherwise the degree
        // symbol on the axis title does not display correctly.
//        chart.getRenderingHints().put(PDFHints.KEY_DRAW_STRING_TYPE, 
//                PDFHints.VALUE_DRAW_STRING_TYPE_VECTOR);
        chart.setLegendPosition(LegendAnchor.BOTTOM_RIGHT, 
                Orientation.VERTICAL);
        chart.getViewPoint().panLeftRight(-Math.PI / 60);
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        StandardCategoryAxis3D xAxis = (StandardCategoryAxis3D) 
                plot.getColumnAxis();
        NumberAxis3D yAxis = (NumberAxis3D) plot.getValueAxis();
        StandardCategoryAxis3D zAxis = (StandardCategoryAxis3D) 
                plot.getRowAxis();
        plot.setGridlineStrokeForValues(new BasicStroke(0.0f));
        xAxis.setLineColor(new Color(0, 0, 0, 0));
        yAxis.setLineColor(new Color(0, 0, 0, 0));
        zAxis.setLineColor(new Color(0, 0, 0, 0));
        plot.getRenderer().setColors(Colors.createPastelColors());
        plot.setToolTipGenerator(new StandardCategoryItemLabelGenerator(
                "%2$s (%3$s) = %4$s degrees"));        return chart;    
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
                
        DefaultKeyedValues<Number> s3 = new DefaultKeyedValues<Number>();
        s3.put("Jan", 7);
        s3.put("Feb", 7);
        s3.put("Mar", 10);
        s3.put("Apr", 13);
        s3.put("May", 17);
        s3.put("Jun", 20);
        s3.put("Jul", 22);
        s3.put("Aug", 21);
        s3.put("Sep", 19);
        s3.put("Oct", 15);
        s3.put("Nov", 10);
        s3.put("Dec", 8);
        dataset.addSeriesAsRow("London", s3);

        DefaultKeyedValues<Number> s1 = new DefaultKeyedValues<Number>();
        s1.put("Jan", 3);
        s1.put("Feb", 5);
        s1.put("Mar", 9);
        s1.put("Apr", 14);
        s1.put("May", 18);
        s1.put("Jun", 22);
        s1.put("Jul", 25);
        s1.put("Aug", 24);
        s1.put("Sep", 20);
        s1.put("Oct", 14);
        s1.put("Nov", 8);
        s1.put("Dec", 4);
        dataset.addSeriesAsRow("Geneva", s1);
        
        DefaultKeyedValues<Number> s2 = new DefaultKeyedValues<Number>();
        s2.put("Jan", 9);
        s2.put("Feb", 11);
        s2.put("Mar", 13);
        s2.put("Apr", 16);
        s2.put("May", 20);
        s2.put("Jun", 23);
        s2.put("Jul", 26);
        s2.put("Aug", 26);
        s2.put("Sep", 24);
        s2.put("Oct", 19);
        s2.put("Nov", 13);
        s2.put("Dec", 9);
        dataset.addSeriesAsRow("Bergerac", s2);

        DefaultKeyedValues<Number> s4 = new DefaultKeyedValues<Number>();
        s4.put("Jan", 22);
        s4.put("Feb", 22);
        s4.put("Mar", 20);
        s4.put("Apr", 17);
        s4.put("May", 14);
        s4.put("Jun", 11);
        s4.put("Jul", 11);
        s4.put("Aug", 12);
        s4.put("Sep", 14);
        s4.put("Oct", 17);
        s4.put("Nov", 19);
        s4.put("Dec", 21);
        dataset.addSeriesAsRow("Christchurch", s4);

        DefaultKeyedValues<Number> s5 = new DefaultKeyedValues<Number>();
        s5.put("Jan", 20);
        s5.put("Feb", 20);
        s5.put("Mar", 19);
        s5.put("Apr", 17);
        s5.put("May", 14);
        s5.put("Jun", 12);
        s5.put("Jul", 11);
        s5.put("Aug", 12);
        s5.put("Sep", 13);
        s5.put("Oct", 15);
        s5.put("Nov", 17);
        s5.put("Dec", 19);
        dataset.addSeriesAsRow("Wellington", s5);

        return dataset;
    }

}