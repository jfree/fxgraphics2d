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

package com.orsoncharts.demo.fx;

import static javafx.application.Application.launch;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.orsoncharts.Chart3D;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.demo.ScatterPlot3D3;
import com.orsoncharts.fx.Chart3DViewer;
import com.orsoncharts.graphics3d.ViewPoint3D;

/**
 * A scatter plot demo for JavaFX.
 */
public class ScatterPlot3DFXDemo3 extends Application {
    
    /**
     * Returns a panel containing the content for the demo.  This method is
     * used across all the individual demo applications to allow aggregation 
     * into a single "umbrella" demo (OrsonChartsDemo).
     * 
     * @return A panel containing the content for the demo.
     */
    public static Node createDemoNode() {
        
        XYZDataset[] datasets = ScatterPlot3D3.createDatasets();
        Chart3D chart1 = ScatterPlot3D3.createChart(
                "Iris Dataset : Combination 1", datasets[0], "Sepal Length", 
                "Sepal Width", "Petal Length");
        chart1.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(120));
        Chart3DViewer viewer1 = new Chart3DViewer(chart1);
        Chart3D chart2 = ScatterPlot3D3.createChart(
                "Iris Dataset : Combination 2", datasets[1], 
                "Sepal Length", "Sepal Width", "Petal Width");
        chart2.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(120));
        Chart3DViewer viewer2 = new Chart3DViewer(chart2);
        Chart3D chart3 = ScatterPlot3D3.createChart(
                "Iris Dataset : Combination 3", datasets[2], 
                "Sepal Length", "Petal Width", "Petal Length");
        chart3.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(120));
        Chart3DViewer viewer3 = new Chart3DViewer(chart3);
        Chart3D chart4 = ScatterPlot3D3.createChart(
                "Iris Dataset : Combination 4", datasets[3], 
                "Sepal Width", "Petal Width", "Petal Length");
        chart4.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(120));
        Chart3DViewer viewer4 = new Chart3DViewer(chart4);
        
        GridPane pane = new GridPane();
        pane.add(viewer1, 0, 0);
        pane.add(viewer2, 0, 1);
        pane.add(viewer3, 1, 0);
        pane.add(viewer4, 1, 1);
        return pane;
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane sp = new StackPane();
        sp.getChildren().add(createDemoNode());
        Scene scene = new Scene(sp, 768, 512);
        stage.setScene(scene);
        stage.setTitle("Orson Charts: ScatterPlotFXDemo3.java");
        stage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
