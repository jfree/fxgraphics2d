FXGraphics2D
============

Version 1.6, 5 November 2017.

Overview
--------
**FXGraphics2D** is a free implementation of Java's `Graphics2D` API that targets the JavaFX Canvas.  It makes it possible to reuse existing Java2D code in a JavaFX application. 

![FXGraphics2D sample](http://www.object-refinery.com/blog/images/fxgraphics2d_normalised.png)

 The code has been developed for the use of **Orson Charts** (http://www.object-refinery.com/orsoncharts/) 
and **JFreeChart** (http://www.jfree.org/jfreechart).  Numerous demos can be
found in the [JFree-FXDemos](https://github.com/jfree/jfree-fxdemos "JFree-FXDemos Project Page at GitHub")
project page at GitHub.

You can also read about *FXGraphics2D* in this Java Magazine article:

http://www.oraclejavamagazine-digital.com/javamagazine/november_december_2014#pg63

The home page for the project is:

http://www.jfree.org/fxgraphics2d/

*FXGraphics2D* requires JDK 1.8.0_40 or later and is licensed under the terms of a (three clause) BSD-style license.


Include
-------
To include `FXGraphics2D` in your own project, add the following Maven dependency:

        <dependency>
            <groupId>org.jfree</groupId>
            <artifactId>fxgraphics2d</artifactId>
            <version>1.6</version>
        </dependency>

Build
-----
You can build `FXGraphics2D` from sources using Maven:

    mvn clean install


License
-------

`FXGraphics2D` is licensed under a BSD-style license:

```
Copyright (c) 2014-2017, Object Refinery Limited.

All rights reserved.

Redistribution and use in source and binary forms, with or without modification, 
are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, 
this list of conditions and the following disclaimer in the documentation 
and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors 
may be used to endorse or promote products derived from this software without 
specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE 
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES 
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; 
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON 
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT 
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS 
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
```

Implementation Notes
--------------------
(1) Initial versions of the JavaFX Canvas did not have support for dashed lines.  We submitted this feature request:

    https://bugs.openjdk.java.net/browse/JDK-8097787

The feature was added in Java 1.8.0_40.

(2) In performance testing we have noted that path rendering is not as fast as it could be.  This is confirmed by this bug report:

    https://bugs.openjdk.java.net/browse/JDK-8092373


Feedback
--------
If you have feedback about the `FXGraphics2D` library, please visit the forum at http://www.jfree.org/fxgraphics2d.


History
-------

##### 5-Nov-2017 : Version 1.6
- added support for JDK9 by removing the use of some non-public classes;
- simplified the project set-up by removing Ant as a build option, and removing the demos (these will be provided in a separate Git project https://github.com/jfree/jfree-fxdemos).

##### 15-Oct-2016 : Version 1.5
- add rendering hint to provide option for measuring fonts via JavaFX API;
- fix exception in `drawImage()` method with `null` transform;
- updated JFreeSVG to version 3.2 (affects demo only).

##### 27-Apr-2016 : Version 1.4
- improve handling of `setComposite()` for alpha values;
- fixed bug in rotate method, incorrect conversion to degrees.

##### 28-Jan-2016 : Version 1.3
- fixed bug in `drawImage()` method;
- add 'maven' directory with a pom that can be copied over to load the project
  in NetBeans;
- updated Ant script to copy over `pom.xml` to distribution.

##### 23-Jan-2016 : Version 1.2.1 
- implemented `getGraphicsDevice()` method;
- add support for dashed lines;
- in `setPaint()` and `setStroke()`, don't update anything if the value is the same;
- various performance fixes;
- fixed bug in `drawImage()` method;
- updated demos.

Note that version 1.2.1 corrects a Maven dependency problem that sneaked into the 1.2 release.

##### 30-Jul-2014 : Version 1.1
- fix clipping so that combined plots in JFreeChart work correctly;
- observe `KEY_STROKE_CONTROL` rendering hint for lines and rectangles (used to 
  sharpen gridlines and borders in JFreeChart);
- adjust `fontRenderContext` so that glyph positioning is more precise for 
  `TextLayout`;
- add `clearRect()` call in the JFreeChart demo;
- add Maven support (pom.xml).

##### 19-May-2014 : Version 1.0
- initial public release.
