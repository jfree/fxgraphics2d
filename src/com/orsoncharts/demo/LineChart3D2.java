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
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.legend.LegendAnchor;

/**
 * 3D line chart configuration for demo applications.
 */
public class LineChart3D2 {
  
    public static Chart3D createChart(CategoryDataset3D dataset) {
        Chart3D chart = Chart3DFactory.createLineChart("Quarterly Profits", 
                "Large Banks in USA", dataset, null, "Quarter", "$ millions");
        chart.setChartBoxColor(new Color(255, 255, 255, 128));
        chart.setLegendAnchor(LegendAnchor.TOP_RIGHT);
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
        
        // http://investor.bankofamerica.com/phoenix.zhtml?c=71595&p=quarterlyearnings#fbid=Ke_-yRMOTA4
        DefaultKeyedValues<Number> s0 = new DefaultKeyedValues<Number>();
        s0.put("Q3/11", 5889);
        s0.put("Q4/11", 1584);
        s0.put("Q1/12", 328);
        s0.put("Q2/12", 2098);
        s0.put("Q3/12", -33);
        s0.put("Q4/12", 367);
        s0.put("Q1/13", 1110);
        s0.put("Q2/13", 3571);
        s0.put("Q3/13", 2218);
        dataset.addSeriesAsRow("Bank of America", s0);

        // http://www.citigroup.com/citi/investor/data/qer313s.pdf
        DefaultKeyedValues<Number> s1 = new DefaultKeyedValues<Number>();
        s1.put("Q3/11", 3771);
        s1.put("Q4/11", 956);
        s1.put("Q1/12", 2931);
        s1.put("Q2/12", 2946);
        s1.put("Q3/12", 468);
        s1.put("Q4/12", 1196);
        s1.put("Q1/13", 3808);
        s1.put("Q2/13", 4182);
        s1.put("Q3/13", 3227);
        dataset.addSeriesAsRow("Citigroup", s1);
        
        // https://www.wellsfargo.com/downloads/pdf/press/3q13pr.pdf 
        DefaultKeyedValues<Number> s3 = new DefaultKeyedValues<Number>();
        s3.put("Q3/11", 4055);
        s3.put("Q4/11", 4107);
        s3.put("Q1/12", 4248);
        s3.put("Q2/12", 4622);
        s3.put("Q3/12", 4937);
        s3.put("Q4/12", 4857);
        s3.put("Q1/13", 4931);
        s3.put("Q2/13", 5272);
        s3.put("Q3/13", 5317);

        dataset.addSeriesAsRow("Wells Fargo", s3);

        // http://files.shareholder.com/downloads/ONE/2724973994x0x696270/df38c408-0315-43dd-b896-6fe6bc895050/3Q13_Earnings_Earnings_Supplement.pdf
        DefaultKeyedValues<Number> s2 = new DefaultKeyedValues<Number>();
        s2.put("Q3/11", 4262);
        s2.put("Q4/11", 3728);
        s2.put("Q1/12", 4924);
        s2.put("Q2/12", 4960);
        s2.put("Q3/12", 5708);
        s2.put("Q4/12", 5692);
        s2.put("Q1/13", 6529);
        s2.put("Q2/13", 6496);
        s2.put("Q3/13", -380);
        dataset.addSeriesAsRow("J.P.Morgan", s2);
        
        return dataset;
    }

}