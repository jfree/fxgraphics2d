FXGraphics2D
============

Version 2.1.5, 22 May 2025.

[![Maven Central](https://img.shields.io/maven-central/v/org.jfree/org.jfree.fxgraphics2d)](https://central.sonatype.com/artifact/org.jfree/org.jfree.fxgraphics2d/versions) [![javadoc](https://javadoc.io/badge2/org.jfree/org.jfree.fxgraphics2d/javadoc.svg)](https://javadoc.io/doc/org.jfree/org.jfree.fxgraphics2d) [![JFXCentral](https://img.shields.io/badge/Find_me_on-JFXCentral-blue?logo=googlechrome&logoColor=white)](https://www.jfx-central.com/libraries/fxgraphics2d)

Overview
--------
**FXGraphics2D** is an implementation of Java's `Graphics2D` API that targets the JavaFX `Canvas`.  It makes it 
possible to reuse existing Java2D code in a JavaFX application.  The following example is generated by [JFreeChart-FX](https://github.com/jfree/jfreechart-fx):

![FXGraphics2D sample](https://camo.githubusercontent.com/b245cd04d09839805cf4db6b8d6a34755ec7a2105a15b23224ec7741fb3d38e2/687474703a2f2f6a667265652e6f72672f6a6672656563686172742f696d616765732f636f666665655f7072696365732e706e67)

Background
----------
The code has been developed for the use of **Orson Charts** (https://github.com/jfree/orson-charts/) 
and **JFreeChart** (http://www.jfree.org/jfreechart).  Numerous demos can be
found in the [JFree-FXDemos](https://github.com/jfree/jfree-fxdemos "JFree-FXDemos Project Page at GitHub")
project page at GitHub.

You can also read about *FXGraphics2D* in this Java Magazine article:

- http://www.jfree.org/fxgraphics2d/javamagazine20141112-dl.pdf

*FXGraphics2D* requires JDK 11 or later and is licensed under the terms of a (three clause) BSD-style license.


Include
-------
To include `FXGraphics2D` in your own project, add the following Maven dependency:

    <dependency>
        <groupId>org.jfree</groupId>
        <artifactId>org.jfree.fxgraphics2d</artifactId>
        <version>2.1.5</version>
    </dependency>

Build
-----
You can build `FXGraphics2D` from sources using Maven:

    mvn clean install

Testing
-------
FXGraphics2D is being tested using [Graphics2D Tester](https://github.com/jfree/graphics2d-tester) and produces the output shown below.  There are several areas that still need work:

- the compositing rules in `AlphaComposite` cannot be mapped 100% to JavaFX equivalents
- font metrics are approximated
- image drawing with clipping shows a strange greying out effect
- `TexturePaint` is not supported yet (but see [pull request #14](https://github.com/jfree/fxgraphics2d/pull/14))

![fxgraphics2d](https://github.com/user-attachments/assets/3460b359-2f88-4110-adc9-39f27df977a6)

Feedback
--------
If you have feedback about the `FXGraphics2D` library, please use the [discussion area](https://github.com/jfree/fxgraphics2d/discussions).

License
-------

`FXGraphics2D` is licensed under a BSD-style license:

```
Copyright (c) 2014-present, David Gilbert.

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

History
-------
##### Next release
- added support for `TexturePaint` (see [#14](https://github.com/jfree/fxgraphics2d/pull/14))
- set JavaFX dependency to 24.0.1 (requires Java 22 or later)

##### 22-May-2025 : Version 2.1.5
- fixed bug in `paintsAreEqual()` method
- set JavaFX dependency to 21.0.5
- updated Maven publishing flow

##### 12-Feb-2023 : Version 2.1.4
- fixed clip handling with child Graphics2D.create() (PR #11 by Laurent Bourgès)
- fix bug in drawArc()/fillArc()
- set JavaFX dependency to version 19

##### 23-Jan-2022 : Version 2.1.3
- set JavaFX dependency to version 17
- apply code clean-up suggested by IntelliJ

##### 5-Aug-2021 : Version 2.1.2
- set winding rule for `Path2D` instances;
- set cycle method on gradient paints;
- fix focus distance on `RadialGradientPaint`.

##### 24-May-2021 : Version 2.1.1
- fix handling for `null` arguments in `drawImage()` methods;
- update JavaFX dependency to version 16;
- update JUnit test dependency to 5.7.2.

##### 3-Oct-2020 : Version 2.1
- update JavaFX dependencies to version 15;
- migrated tests to JUnit 5.

##### 27-Feb-2020 : Version 2.0
- converted to a module (`org.jfree.fxgraphics2d`);
- now requires JDK 11 or later.

##### 7-Feb-2019 : Version 1.8
- fixed bug #8 where `draw(Shape)` alters the shape state for `RenderingHints.VALUE_STROKE_PURE`;
- added automatic module name (`org.jfree.fxgraphics2d`).

##### 5-Jul-2018 : Version 1.7
- fixed bug #6 where color, stroke and font attributes are changed after calling `setClip()` twice.

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

##### 23-Jan-2016 : Version 1.2.1 
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
