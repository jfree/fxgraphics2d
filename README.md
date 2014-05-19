FXGraphics2D
============

Overview
--------
FXGraphics2D is a free implementation of the Graphics2D API that targets the JavaFX Canvas.  The code has been developed for the use of Orson Charts (http://www.object-refinery.com/orsoncharts/) and JFreeChart (http://www.jfree.org/jfreechart).  The home page for the project is:

    http://www.jfree.org/fxgraphics2d/

FXGraphics2D requires JDK 1.8.0 or later and is licensed under the terms of a (three clause) BSD-style license.


Demo
----
Two demos are included in the distribution.  The first shows the basic usage of the FXGraphics2D class by displaying a JFreeChart instance within a resizable JavaFX canvas.  To run this demo from the command line type the following from the root directory of the project:

    java -cp fxgraphics2d-1.0-demo.jar org.jfree.demo.fx.FXGraphics2DDemo1

The second demo is taken from the Orson Charts project and shows various 3D charts displayed on a custom JavaFX control.  The Orson Charts library is using FXGraphics2D internally for the viewer control.  To run this demo, type the following from the root directory of the project:

    java -jar fxgraphics2d-1.0-demo.jar


Build
-----
An Ant script is provided for the build.  To run it, type the following from the project root directory:

   ant -f ant/build.xml


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


Feedback
--------
If you have feedback about the FXGraphics2D library, please visit the forum at http://www.jfree.org/fxgraphics2d.


History
-------

19-May-2014 : Version 1.0 released.
