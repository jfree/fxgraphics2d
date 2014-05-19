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
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.TitleAnchor;
import com.orsoncharts.axis.NumberAxis3D;
import com.orsoncharts.axis.StandardCategoryAxis3D;
import com.orsoncharts.data.DataUtils;
import com.orsoncharts.data.DefaultKeyedValues;
import com.orsoncharts.data.JSONUtils;
import com.orsoncharts.data.KeyedValues3D;
import com.orsoncharts.data.PieDataset3D;
import com.orsoncharts.data.StandardPieDataset3D;
import com.orsoncharts.data.category.CategoryDataset3D;
import com.orsoncharts.data.category.StandardCategoryDataset3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.legend.LegendAnchor;
import com.orsoncharts.marker.CategoryMarker;
import com.orsoncharts.marker.RangeMarker;
import com.orsoncharts.plot.CategoryPlot3D;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;
import com.orsoncharts.util.Orientation;

import org.jfree.graphics2d.svg.SVGGraphics2D;

/**
 * A demo showing the creation of an HTML page containing three charts in
 * SVG format, with tooltips and mouse-click interactivity.  This demo creates
 * a file SVGDemo1.html - this file also requires some other support files, 
 * see the 'svg' directory in the distribution.
 * 
 * This demo isn't intended to show how you should generate HTML content in 
 * a production system, but just to provide a concrete example of the SVG
 * output from Orson Charts with JFreeSVG.
 */
public class SVGDemo1 {

    static String generateSVGForChart(Chart3D chart, int width, int height,
            String defsPrefix) {
        SVGGraphics2D g2 = new SVGGraphics2D(width, height);
        g2.setDefsKeyPrefix(defsPrefix);
        chart.setElementHinting(true);
        chart.draw(g2, new Rectangle(width, height));
        return g2.getSVGElement(chart.getID());
    }
    
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    static PieDataset3D createPieChartDataset() {
        StandardPieDataset3D dataset = new StandardPieDataset3D();
        dataset.add("Milk Products", 11625);
        dataset.add("Meat", 5114);
        dataset.add("Wood/Logs", 3060);
        dataset.add("Crude Oil", 2023);
        dataset.add("Machinery", 1865);
        dataset.add("Fruit", 1587);
        dataset.add("Fish", 1367);
        dataset.add("Wine", 1177);
        dataset.add("Other", 18870);
        return dataset; 
    }

    static Chart3D createPieChart(String id) {
        Chart3D chart = Chart3DFactory.createPieChart(
                "New Zealand Exports 2012", 
                "http://www.stats.govt.nz/browse_for_stats/snapshots-of-nz/nz-in-profile-2013.aspx", 
                createPieChartDataset());
        chart.setID(id);
        chart.setTitleAnchor(TitleAnchor.TOP_LEFT);
        chart.setLegendPosition(LegendAnchor.BOTTOM_CENTER,
                Orientation.HORIZONTAL);
        return chart;
    }
    
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    private static CategoryDataset3D createBarChartDataset() {    
        StandardCategoryDataset3D dataset = new StandardCategoryDataset3D();

        DefaultKeyedValues<Double> s1 = new DefaultKeyedValues<Double>();
        s1.put("Q2/11", 8.181);
        s1.put("Q3/11", 8.792);
        s1.put("Q4/11", 9.039);
        s1.put("Q1/12", 10.916);
        s1.put("Q2/12", 8.181);
        s1.put("Q3/12", 9.094);
        s1.put("Q4/12", 8.958);
        s1.put("Q1/13", 10.947);
        s1.put("Q2/13", 8.372);
        s1.put("Q3/13", 9.275);
        dataset.addSeriesAsRow("Oracle", s1);

        DefaultKeyedValues<Double> s2 = new DefaultKeyedValues<Double>();
        s2.put("Q2/11", 9.03);
        s2.put("Q3/11", 9.72);
        s2.put("Q4/11", 10.58);
        s2.put("Q1/12", 10.65);
        s2.put("Q2/12", 12.214);
        s2.put("Q3/12", 14.101);
        s2.put("Q4/12", 14.419);
        s2.put("Q1/13", 13.969);
        s2.put("Q2/13", 14.105);
        s2.put("Q3/13", 14.893);
        s2.put("Q4/13", 16.858);
        dataset.addSeriesAsRow("Google", s2);
        
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
        dataset.addSeriesAsRow("Microsoft", s3);
        
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
        dataset.addSeriesAsRow("Apple", s4);
        
        return dataset;
    }

    static Chart3D createBarChart(String id) {
        CategoryDataset3D dataset = createBarChartDataset();
        Chart3D chart = Chart3DFactory.createBarChart("Quarterly Revenues", 
                "For some large IT companies", dataset, null, "Quarter", 
                "$billion Revenues");
        chart.setID(id);
        chart.setChartBoxColor(new Color(255, 255, 255, 127));
        chart.setLegendAnchor(LegendAnchor.BOTTOM_RIGHT);
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.setGridlinePaintForValues(Color.BLACK);
        StandardCategoryAxis3D rowAxis = (StandardCategoryAxis3D) plot.getRowAxis();
        CategoryMarker rowMarker = new CategoryMarker("Apple");
        rowAxis.setMarker("Apple", rowMarker);
        StandardCategoryAxis3D columnAxis = (StandardCategoryAxis3D) plot.getColumnAxis();
        CategoryMarker columnMarker = new CategoryMarker("Q4/12");
        columnAxis.setMarker("Q4/12", columnMarker);
        chart.getViewPoint().setRho(1.3 * chart.getViewPoint().getRho());
        return chart;
    }

    /**
     * Reads a dataset from the file iris.txt.
     * 
     * @return A sample dataset.
     */
    private static XYZDataset createScatterDataset(Comparable<?> xKey, 
            Comparable<?> yKey, Comparable<?> zKey) {
        Reader in = new InputStreamReader(
                SVGDemo1.class.getResourceAsStream("iris.txt"));
        KeyedValues3D data;
        try {
            data = JSONUtils.readKeyedValues3D(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return DataUtils.extractXYZDatasetFromColumns(data, xKey, yKey, zKey);
    }
    
    static Chart3D createScatterChart(String id) {
        XYZDataset dataset = createScatterDataset("sepal length", 
                "sepal width", "petal length");
        Chart3D chart = Chart3DFactory.createScatterChart("Iris Dataset", 
                null, dataset, "Sepal Length", "Sepal Width", "Petal Length");
        chart.setID(id);
        chart.setLegendAnchor(LegendAnchor.BOTTOM_LEFT);
        chart.setLegendOrientation(Orientation.VERTICAL);
        XYZPlot plot = (XYZPlot) chart.getPlot();
        NumberAxis3D yAxis = (NumberAxis3D) plot.getYAxis();
        RangeMarker marker1 = new RangeMarker(3.5, 4.0);
        yAxis.setMarker("M1", marker1);
        ScatterXYZRenderer renderer = (ScatterXYZRenderer) plot.getRenderer();
        renderer.setSize(0.15);
        chart.getViewPoint().panLeftRight(Math.PI / 12);
        chart.getViewPoint().roll(Math.PI / 12);
        chart.getViewPoint().setRho(1.6 * chart.getViewPoint().getRho());
        return chart;
    }

    public static void main(String[] args) throws Exception {
                BufferedWriter writer = null;
        try {
            FileOutputStream fos = new FileOutputStream("SVGDemo1.html");
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
            writer = new BufferedWriter(osw);
            writer.write("<!DOCTYPE html>\n");
            writer.write("<html>\n");
            
            writer.write("<head>\n");
            writer.write("<title>SVG Demo 1</title>\n");
            writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"); 
            writer.write("<script src=\"lib/opentip-native.js\"></script>");
            writer.write("<link href=\"css/opentip.css\" rel=\"stylesheet\" type=\"text/css\" />");
            writer.write("<script src=\"lib/orsoncharts.js\"></script>");
            writer.write("</head>\n");
            
            writer.write("<body>\n");
            
            writer.write("<h1>SVG Chart Demo</h1>\n");
            writer.write("<p>Click on an item in the chart or just hover and look at the tooltip (the ");
            writer.write("reference is a string in JSON format that should contain enough information to ");
            writer.write("identify the chart element):</p>\n");
            
            writer.write("  <script type=\"application/javascript\">\n");
            writer.write("    function pieChartData() {\n");
            writer.write("      return " + JSONUtils.writeKeyedValues(createPieChartDataset()) + "\n");
            writer.write("    }\n");
            writer.write("  </script>\n");
            
            writer.write("  <script type=\"application/javascript\">\n");
            writer.write("    function barChartData() {\n");
            writer.write("      return " + JSONUtils.writeKeyedValues3D(createBarChartDataset()) + "\n");
            writer.write("    }\n");
            writer.write("  </script>\n");

            writer.write("  <script type=\"application/javascript\">\n");
            writer.write("    function scatterChartData() {\n");
            XYZDataset dataset = createScatterDataset("sepal length", 
                    "sepal width", "petal length");
            writer.write("      return " + JSONUtils.writeXYZDataset(dataset) + "\n");
            writer.write("    }\n");
            writer.write("  </script>\n");

            writer.write("  <script type=\"application/javascript\">\n");
            writer.write("    // wait until all the resources are loaded\n");
            writer.write("    window.addEventListener(\"load\", initialise, false);\n");

            writer.write("    function initialise() {\n");
            
            writer.write("      orsoncharts.pieDataset = new orsoncharts.KeyedValuesDataset();\n");
            writer.write("      orsoncharts.pieDataset.data = pieChartData();\n");
            writer.write("      orsoncharts.pieLabelGenerator = new orsoncharts.KeyedValueLabels();\n");
            writer.write("      orsoncharts.pieLabelGenerator.valueDP = 0;\n");
            writer.write("      orsoncharts.pieLabelGenerator.format = \"{K} = NZ${V} million\";\n");
            
            writer.write("      orsoncharts.barDataset = new orsoncharts.KeyedValues3DDataset();\n");
            writer.write("      orsoncharts.barDataset.data = barChartData();\n");
            writer.write("      orsoncharts.barLabelGenerator = new orsoncharts.KeyedValue3DLabels();\n");
            writer.write("      orsoncharts.barLabelGenerator.format = \"{R}, {C} = US${V} billion\";\n");
            
            writer.write("      orsoncharts.scatterDataset = new orsoncharts.XYZDataset();\n");
            writer.write("      orsoncharts.scatterDataset.data.series = scatterChartData();\n");
            writer.write("      orsoncharts.scatterLabelGenerator = new orsoncharts.XYZLabels();\n");
            writer.write("      orsoncharts.scatterLabelGenerator.format = \"{S} = ({X}, {Y}, {Z})\";\n");

            writer.write("      var pieSVG = document.getElementById(\"PieChart1\");\n");
            writer.write("      pieSVG.onmouseover = handleMouseOver;\n");
            writer.write("      pieSVG.onclick = handleClick;\n");

            writer.write("      var barSVG = document.getElementById(\"BarChart1\");\n");
            writer.write("      barSVG.onmouseover = handleMouseOver;\n");
            writer.write("      barSVG.onclick = handleClick;\n");

            writer.write("      var scatterSVG = document.getElementById(\"ScatterChart1\");\n");
            writer.write("      scatterSVG.onmouseover = handleMouseOver;\n");
            writer.write("      scatterSVG.onclick = handleClick;\n");

            writer.write("    }\n");

            writer.write("    function handleClick(evt) {\n");
            writer.write("      var element = evt.target;\n");
            writer.write("      var ref = orsoncharts.Utils.findChartRef(element);\n");
            writer.write("      var chartId = orsoncharts.Utils.findChartId(element);\n");
            writer.write("      alert('You clicked on the item ' + ref + ' for the chart [' + chartId + ']');\n");
            writer.write("    }\n");

            writer.write("    function handleMouseOver(evt) {\n");
            writer.write("      var element = evt.target;\n");
            writer.write("      var ref = orsoncharts.Utils.findChartRef(element);\n");
            writer.write("      var content;\n");
            writer.write("      var chartId = orsoncharts.Utils.findChartId(element);\n");
            writer.write("      if (ref != null && ref != 'ORSON_CHART_TOP_LEVEL') {\n");
            writer.write("        var refObj = JSON.parse(ref);\n");
            writer.write("        if (Opentip.tips.length < 1) {\n");
            writer.write("          myOpentip = new Opentip(element, \"content\");\n");
            writer.write("        } else {\n");
            writer.write("          myOpentip = Opentip.tips[0];\n");
            writer.write("        }\n");
            writer.write("        myOpentip.target = element;\n");
            writer.write("        if (chartId == \"PieChart1\") {\n");
            writer.write("          if (refObj.hasOwnProperty(\"key\")) {\n");
            writer.write("             var itemIndex = orsoncharts.pieDataset.indexOf(refObj.key);\n");
            writer.write("             content = orsoncharts.pieLabelGenerator.itemLabel(orsoncharts.pieDataset, itemIndex);\n");
            writer.write("          } else { content = ref; }\n");
            writer.write("        } else if (chartId == \"BarChart1\") {\n");
            writer.write("          if (!refObj.hasOwnProperty(\"type\")) {\n");
            writer.write("            var seriesIndex = orsoncharts.barDataset.seriesIndex(refObj.seriesKey);\n");
            writer.write("            var rowIndex = orsoncharts.barDataset.rowIndex(refObj.rowKey);\n");
            writer.write("            var columnIndex = orsoncharts.barDataset.columnIndex(refObj.columnKey);\n");
            writer.write("            content = orsoncharts.barLabelGenerator.itemLabel(orsoncharts.barDataset, seriesIndex, rowIndex, columnIndex);\n");
            writer.write("          } else {\n");
            writer.write("          content = ref + \" for bar chart.\";\n");
            writer.write("          }\n");
            writer.write("        } else if (chartId == \"ScatterChart1\") {\n");
            writer.write("          if (!refObj.hasOwnProperty(\"type\")) {\n");
            writer.write("            content = orsoncharts.scatterLabelGenerator.itemLabel(orsoncharts.scatterDataset, refObj.seriesKey, refObj.itemIndex);\n");
            writer.write("          } else {\n");
            writer.write("            content = ref + \" for scatter plot.\";\n");
            writer.write("          }\n");
            writer.write("        }\n");
            writer.write("        myOpentip.setContent(content);\n");
            writer.write("        myOpentip.reposition();\n");
            writer.write("        myOpentip.show();\n");

            writer.write("      }\n");
            writer.write("    }\n");
            writer.write("</script>\n");
            
            writer.write("<p>\n");
            Chart3D pieChart = createPieChart("PieChart1");
            writer.write(generateSVGForChart(pieChart, 600, 370, "defs1_") + "\n");
            writer.write("</p>\n");
            
            writer.write("<p>\n");
            Chart3D barChart = createBarChart("BarChart1");
            writer.write(generateSVGForChart(barChart, 600, 370, "defs2_") + "\n");
            writer.write("</p>\n");
            
            writer.write("<p>\n");
            Chart3D scatterChart = createScatterChart("ScatterChart1");
            writer.write(generateSVGForChart(scatterChart, 600, 370, "defs3_") + "\n");
            writer.write("</p>\n");
            
            writer.write("</body>\n");
            writer.write("</html>\n");
            writer.flush();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(SVGDemo1.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        } 

    }
}
