FXGraphics2D
============

Version 1.2, ?? January 2015

Overview
--------
FXGraphics2D is a free implementation of the Graphics2D API that targets the JavaFX Canvas.  The code has been developed for the use of Orson Charts (http://www.object-refinery.com/orsoncharts/) and JFreeChart (http://www.jfree.org/jfreechart).  The home page for the project is:

http://www.jfree.org/fxgraphics2d/

FXGraphics2D requires JDK 1.8.0 or later and is licensed under the terms of a (three clause) BSD-style license.


Demo
----
Two demos are included in the distribution.  The first shows the basic usage of the FXGraphics2D class by displaying a JFreeChart instance within a resizable JavaFX canvas.  To run this demo from the command line type the following from the root directory of the project:

    java -cp fxgraphics2d-1.1-demo.jar org.jfree.fx.demo.FXGraphics2DDemo1

The second demo is taken from the Orson Charts project and shows various 3D charts displayed on a custom JavaFX control.  The Orson Charts library is using FXGraphics2D internally for the viewer control.  To run this demo, type the following from the root directory of the project:

    java -jar fxgraphics2d-1.1-demo.jar


Build
-----
You can build FXGraphics2D with either Ant:

    ant -f ant/build.xml

...or Maven:

    mvn clean install


License
-------

The following license applies to the FXGraphics2D library (other licenses apply to the demo code dependencies such as JFreeChart and Orson Charts):

Copyright (c) 2014, Object Refinery Limited.

All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


Implementation Notes
--------------------
(1) The JavaFX Canvas does not have support for dashed lines.  We have submitted this feature request:

    https://javafx-jira.kenai.com/browse/RT-37999

The feature is coming in Java 1.8.0_40.

(2) In performance testing we have noted that path rendering is not as fast as it could be.  This is confirmed by this bug report:

    https://javafx-jira.kenai.com/browse/RT-20405

(3) There is a rendering bug that is triggered when a clip is applied.  We submitted this bug report and the fix is available in Java 1.8.0_20:

    https://javafx-jira.kenai.com/browse/RT-36891

As a workaround for earlier Java versions, the FXGraphics2D class has a setClippingDisabled(boolean) method which enables clipping to be enabled or disabled.


Feedback
--------
If you have feedback about the FXGraphics2D library, please visit the forum at http://www.jfree.org/fxgraphics2d.


History
-------

??-???-2015 : Version 1.2
- add support for dashed lines (implemented via reflection, as the required JavaFX API methods won't be in a production release until March 2015);
- implemented getGraphicsDevice() method;
- minor performance fixes.

30-Jul-2014 : Version 1.1
- fix clipping so that combined plots in JFreeChart work correctly;
- observe KEY_STROKE_CONTROL rendering hint for lines and rectangles (used to sharpen gridlines and borders in JFreeChart);
- adjust fontRenderContext so that glyph positioning is more precise for TextLayout;
- add clearRect() call in the JFreeChart demo;
- add Maven support (pom.xml).

19-May-2014 : Version 1.0
- initial public release.