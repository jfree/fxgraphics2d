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

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;

/**
 * 3D stacked bar chart configuration for demo applications.
 */
public class StackedBarChart3D1 {
  
    /**
     * Creates a stacked bar chart based on the supplied dataset.
     * 
     * @param dataset  the dataset.
     * 
     * @return A stacked bar chart. 
     */
    public static Chart3D createChart(CategoryDataset3D dataset) {
        Chart3D chart = Chart3DFactory.createStackedBarChart(
                "Stacked Bar Chart", "Put the data source here", dataset, null, 
                null, "Value");
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

        DefaultKeyedValues<Double> s1 = new DefaultKeyedValues<Double>();
        s1.put("A", 4.0);
        s1.put("B", 2.0);
        s1.put("C", 3.0);
        s1.put("D", 5.0);
        s1.put("E", 2.0);
        s1.put("F", 1.0);
        DefaultKeyedValues<Double> s2 = new DefaultKeyedValues<Double>();
        s2.put("A", 1.0);
        s2.put("B", 2.0);
        s2.put("C", 3.0);
        s2.put("D", 2.0);
        s2.put("E", 3.0);
        s2.put("F", 1.0);
        DefaultKeyedValues<Double> s3 = new DefaultKeyedValues<Double>();
        s3.put("A", 6.0);
        s3.put("B", 6.0);
        s3.put("C", 6.0);
        s3.put("D", 4.0);
        s3.put("E", 4.0);
        s3.put("F", 4.0);
        DefaultKeyedValues<Double> s4 = new DefaultKeyedValues<Double>();
        s4.put("A", 9.0);
        s4.put("B", 8.0);
        s4.put("C", 7.0);
        s4.put("D", 6.0);
        s4.put("D", 3.0);
        s4.put("E", 4.0);
        s4.put("F", 6.0);
        DefaultKeyedValues<Double> s5 = new DefaultKeyedValues<Double>();
        s5.put("A", 9.0);
        s5.put("B", 8.0);
        s5.put("C", 7.0);
        s5.put("D", 6.0);
        s5.put("E", 7.0);
        s5.put("F", 9.0);

        dataset.addSeriesAsRow("Series 1", "Row 1", s1);
        dataset.addSeriesAsRow("Series 2", "Row 2", s2);
        dataset.addSeriesAsRow("Series 3", "Row 2", s3);
        dataset.addSeriesAsRow("Series 4", "Row 3", s4);
        dataset.addSeriesAsRow("Series 5", "Row 3", s5);
        return dataset;
    }
    
}