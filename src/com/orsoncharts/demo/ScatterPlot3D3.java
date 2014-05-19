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

import java.awt.Font;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Colors;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.JSONUtils;
import com.orsoncharts.data.KeyedValues3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;
import com.orsoncharts.style.StandardChartStyle;
import com.orsoncharts.table.TextElement;
import com.orsoncharts.util.Orientation;

/**
 * Demo chart configuration.
 */
public class ScatterPlot3D3 {
    
    
    public static XYZDataset[] createDatasets() {
        XYZDataset[] datasets = new XYZDataset[4];
        datasets[0] = createDataset("sepal length", "sepal width", 
                "petal length");
        datasets[1] = createDataset("sepal length", "sepal width", 
                "petal width");
        datasets[2] = createDataset("sepal length", "petal width", 
                "petal length");
        datasets[3] = createDataset("sepal width", "petal width", 
                "petal length");
        return datasets;
    }
    
    public static Chart3D createChart(String title, XYZDataset dataset, 
            String xLabel, String yLabel, String zLabel) {
        Chart3D chart = Chart3DFactory.createScatterChart(null, null, 
                dataset, xLabel, yLabel, zLabel);
        TextElement title1 = new TextElement(title);
        title1.setFont(StandardChartStyle.createDefaultFont(Font.PLAIN, 16));
        chart.setTitle(title1);
        chart.setLegendAnchor(LegendAnchor.BOTTOM_LEFT);
        chart.setLegendOrientation(Orientation.VERTICAL);
        XYZPlot plot = (XYZPlot) chart.getPlot();
        ScatterXYZRenderer renderer = (ScatterXYZRenderer) plot.getRenderer();
        renderer.setSize(0.15);
        renderer.setColors(Colors.createIntenseColors());
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        return chart;
    }
    
    /**
     * Reads a dataset from the file iris.txt.
     * 
     * @return A sample dataset.
     */
    private static XYZDataset createDataset(Comparable<?> xKey, 
            Comparable<?> yKey, Comparable<?> zKey) {
        Reader in = new InputStreamReader(
                ScatterPlot3D3.class.getResourceAsStream("iris.txt"));
        KeyedValues3D data;
        try {
            data = JSONUtils.readKeyedValues3D(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return DataUtils.extractXYZDatasetFromColumns(data, xKey, yKey, zKey);
    }

 
}
