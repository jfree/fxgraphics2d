FXGraphics2D
============

Version 1.3, 28 January 2016

Overview
--------
FXGraphics2D is a free implementation of Java's Graphics2D API that targets the JavaFX Canvas.  It makes it possible to reuse existing Java2D code in a JavaFX application.  The code has been developed for the use of Orson Charts (http://www.object-refinery.com/orsoncharts/) and JFreeChart (http://www.jfree.org/jfreechart).  You can read about FXGraphics2D in this Java Magazine article:

http://www.oraclejavamagazine-digital.com/javamagazine/november_december_2014#pg63

The home page for the project is:

http://www.jfree.org/fxgraphics2d/

FXGraphics2D requires JDK 1.8.0_40 or later and is licensed under the terms of a (three clause) BSD-style license.


Demo
----
Two demos are included in the distribution.  The first shows the basic usage of the FXGraphics2D class by displaying a JFreeChart instance within a resizable JavaFX canvas.  To run this demo from the command line type the following from the root directory of the project:

    java -cp fxgraphics2d-1.3-demo.jar org.jfree.fx.demo.FXGraphics2DDemo1

The second demo is taken from the Orson Charts project and shows various 3D charts displayed on a custom JavaFX control.  The Orson Charts library is using FXGraphics2D internally for the viewer control.  To run this demo, type the following from the root directory of the project:

    java -jar fxgraphics2d-1.3-demo.jar

The demo has the following dependencies (jar files are included in the 'lib' folder):
- Orson Charts 1.5 : licensed under the terms of the GNU General Public License v3 (GPLv3).  See http://www.object-refinery.com/orsoncharts;
- Orson PDF 1.7 : licensed under the terms of the GPLv3.  See http://www.object-refinery.com/orsonpdf;
- JFreeSVG 3.0 : licensed under the terms of the GPLv3.  See http://www.jfree.org/jfreesvg;
- JFreeChart : licensed under the terms of the GNU Lesser General Public License v2.1 or later (LGPL).  See https://github.com/jfree/jfreechart-fse;

These dependencies are *not* required for building the FXGraphics2D jar file, only the demos.

Build
-----
You can build FXGraphics2D with either Ant:

    ant -f ant/build.xml

...or Maven:

    mvn clean install

Note that the pom.xml file in the root folder will build only the FXGraphics2D jar file.  If you want to work with FXGraphics2D in NetBeans, there is an alternative pom file (pom-nb.xml) in the 'maven' directory that will build the project including the demo files and their dependencies.  Simply copy the *content* of pom-nb.xml over to the pom.xml in the root directory for the project, then open the project in NetBeans.


License
-------

The following license applies to the FXGraphics2D library (other licenses apply to the demo code dependencies such as JFreeChart and Orson Charts):

Copyright (c) 2014-2016, Object Refinery Limited.

All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


Implementation Notes
--------------------
(1) Initial versions of the JavaFX Canvas did not have support for dashed lines.  We submitted this feature request:

    https://bugs.openjdk.java.net/browse/JDK-8097787

The feature was added in Java 1.8.0_40.

(2) In performance testing we have noted that path rendering is not as fast as it could be.  This is confirmed by this bug report:

    https://bugs.openjdk.java.net/browse/JDK-8092373


Feedback
--------
If you have feedback about the FXGraphics2D library, please visit the forum at http://www.jfree.org/fxgraphics2d.


History
-------

28-Jan-2016 : Version 1.3
- fixed bug in drawImage() method;
- add 'maven' directory with a pom that can be copied over to load the project
  in NetBeans;
- updated Ant script to copy over pom.xml to distribution.

23-Jan-2016 : Version 1.2.1 
- implemented getGraphicsDevice() method;
- add support for dashed lines;
- in setPaint() and setStroke(), don't update anything if the value is the same;
- various performance fixes;
- fixed bug in drawImage() method;
- updated demos.
Note that version 1.2.1 corrects a Maven dependency problem that sneaked into the 1.2 release.

30-Jul-2014 : Version 1.1
- fix clipping so that combined plots in JFreeChart work correctly;
- observe KEY_STROKE_CONTROL rendering hint for lines and rectangles (used to sharpen gridlines and borders in JFreeChart);
- adjust fontRenderContext so that glyph positioning is more precise for TextLayout;
- add clearRect() call in the JFreeChart demo;
- add Maven support (pom.xml).

19-May-2014 : Version 1.0
- initial public release.
