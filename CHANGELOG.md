Change Log
==========

Version 4.0.0 *(2015-9-15)*
--------------------------

* Renamed Artiste class to Paths in accordance with Effective Java, Item 1
* Removed telescoping methods with different parameters for Path creation
* Moved majority of implementation into Paths class, with exception of small MathUtils
* Upgraded Android dependencies
* Enforced no instances and no subclasses for Paths and MathUtils

Version 3.0.0 *(2015-2-22)*
--------------------------

* Changed API to collection of static methods for creating Path objects

Version 2.0.1 *(2015-1-20)*
--------------------------

* Stripped AndroidManifest.xml to fix manifest merging conflicts
* Added example module
* Upgraded build tools to 2.1.2

Version 2.0.0 *(2014-12-7)*
--------------------------

* Big API change -- let Canvas do drawing, Shape only responsible for Path

Version 1.1.4 *(2014-12-3)*
--------------------------

* Prepare for jCenter release with fixes

Version 1.1.4 *(2014-12-3)*
--------------------------

* Prepare for jCenter release

Version 1.1.3 *(2014-12-2)*
--------------------------

* Improved intersection find algorithm in `RegularStarPolygon`

Version 1.1.2 *(2014-12-1)*
--------------------------

* Changed setRotation() parameter to float from int
* Added offsets to path calculation for when `Rect` isn't at origin

Version 1.1.1 *(2014-12-1)*
--------------------------

* Marked some methods as final
* Added `RegularConvexPolygonTest` and `RegularStarPolygonTest`
* Added test in `ArtisteTest`

Version 1.1.0 *(2014-12-1)*
--------------------------

* Added `setRotation(int rotationDegrees)` to `Shape`
* Added `setOutlined(boolean outlined)` to `RegularStarPolygon`

Version 1.0.5 *(2014-11-30)*
--------------------------

* Updated testOnCanvas() test in ArtisteTest

Version 1.0.4 *(2014-11-30)*
--------------------------

* Moved drawing responsibility into Shape with draw(Canvas canvas, Paint paint)
* Added Circle to Shapes class

Version 1.0.3 *(2014-11-30)*
--------------------------

* Added Change Log

Version 1.0.2 *(2014-11-29)*
--------------------------

* Added `RegularStarPolygon` and `FivePointedStar`

Version 1.0.1 *(2014-11-29)*
--------------------------

 * Changed API to use `setBounds(Rect rect)` instead of `inRect(Rect rect)`
 * Added unit tests for `Artiste` class


Version 1.0.0 *(2014-11-29)*
----------------------------

 * Initial release
 
