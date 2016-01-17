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

import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;

/**
 * Sample datasets.  The datasets here are hard-coded for the purpose of 
 * keeping the demo self-contained - in practice you would normally read your 
 * data from a file, database or other source.
 */
public class SampleData {

    /**
     * Creates a sample dataset containing revenue data for several large
     * companies in the computer industry.  
     * 
     * @return The dataset.
     */
    public static CategoryDataset3D createCompanyRevenueDataset() {    
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();

        // http://investor.oracle.com/financial-reporting/quarterly-reports/default.aspx
        DefaultKeyedValues<Double> s1 = new DefaultKeyedValues<Double>();
        s1.put("Q2/11", 10.775);  // May11
        s1.put("Q3/11", 8.181);   // Aug11
        s1.put("Q4/11", 8.792);   // Nov11
        s1.put("Q1/12", 9.039);   // Feb12
        s1.put("Q2/12", 10.916);  // May12
        s1.put("Q3/12", 8.181);   // Aug12
        s1.put("Q4/12", 9.094);   // Nov12
        s1.put("Q1/13", 8.958);   // Feb13
        s1.put("Q2/13", 10.947);  // May13
        s1.put("Q3/13", 8.372);   // Aug13
        s1.put("Q4/13", 9.275);   // Nov13
        s1.put("Q1/14", 9.307);   // Feb14
        s1.put("Q2/14", 11.320);  // May14
        s1.put("Q3/14", 8.596);   // Aug14
        s1.put("Q4/14", 9.598);   // Nov14
        s1.put("Q1/15", 9.327);   // Feb15
        s1.put("Q2/15", 10.706);  // May15
        s1.put("Q3/15", 8.448);   // Aug15
        dataset.addSeriesAsRow("Oracle", s1);

        DefaultKeyedValues<Double> s2 = new DefaultKeyedValues<Double>();
        s2.put("Q2/11", 9.026);  // Jun11
        s2.put("Q3/11", 9.720);  // Sep11
        s2.put("Q4/11", 10.584);  // Dec11 
        s2.put("Q1/12", 10.645);  // Mar12
        s2.put("Q2/12", 10.964);  // Jun12
        s2.put("Q3/12", 11.526);  // Sep12
        s2.put("Q4/12", 12.905);  // Dec12
        s2.put("Q1/13", 12.951);  // Mar13
        s2.put("Q2/13", 13.107);  // Jun13
        s2.put("Q3/13", 13.754);  // Sep13
        s2.put("Q4/13", 15.707);  // Dec13
        s2.put("Q1/14", 15.420);  // Mar14
        s2.put("Q2/14", 15.955);  // Jun14
        s2.put("Q3/14", 16.523);  // Sep14
        s2.put("Q4/14", 18.103);  // Dec14
        s2.put("Q1/15", 17.258);  // Mar15
        s2.put("Q2/15", 17.727);  // Jun15
        s2.put("Q3/15", 18.675);  // Sep15
        dataset.addSeriesAsRow("Google", s2);
        
        // https://www.microsoft.com/investor/EarningsAndFinancials/TrendedHistory/default.aspx
        DefaultKeyedValues<Double> s3 = new DefaultKeyedValues<Double>();
        s3.put("Q2/11", 17.37);
        s3.put("Q3/11", 17.37);
        s3.put("Q4/11", 20.89);
        s3.put("Q1/12", 17.41);
        s3.put("Q2/12", 18.06);
        s3.put("Q3/12", 16.008);
        s3.put("Q4/12", 21.456);
        s3.put("Q1/13", 20.489);
        s3.put("Q2/13", 19.896);
        s3.put("Q3/13", 18.529);
        s3.put("Q4/13", 24.519);        
        s3.put("Q1/14", 20.403);  // Mar14
        s3.put("Q2/14", 23.382);  // Jun14
        s3.put("Q3/14", 23.201);  // Sep14
        s3.put("Q4/14", 26.470);  // Dec14
        s3.put("Q1/15", 21.729);  // Mar15
        s3.put("Q2/15", 22.180);  // Jun15
        s3.put("Q3/15", 20.379);  // Sep15

        dataset.addSeriesAsRow("Microsoft", s3);
        
        // http://investor.apple.com/results.cfm
        DefaultKeyedValues<Double> s4 = new DefaultKeyedValues<Double>();
        s4.put("Q2/11", 28.57);
        s4.put("Q3/11", 28.27);
        s4.put("Q4/11", 46.33);
        s4.put("Q1/12", 39.20);
        s4.put("Q2/12", 35.00);
        s4.put("Q3/12", 36.00);
        s4.put("Q4/12", 54.5);
        s4.put("Q1/13", 43.6);
        s4.put("Q2/13", 35.323);
        s4.put("Q3/13", 37.5);
        s4.put("Q4/13", 57.594);
        s4.put("Q1/14", 45.646);
        s4.put("Q2/14", 37.432);
        s4.put("Q3/14", 42.125);
        s4.put("Q4/14", 74.599);
        s4.put("Q1/15", 58.010);
        s4.put("Q2/15", 49.605);
        s4.put("Q3/15", 51.501);  // Sep-15
        dataset.addSeriesAsRow("Apple", s4);
        return dataset;
    }

}
