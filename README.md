## Custom View Components

[How Android Draws Views](https://developer.android.com/guide/topics/ui/how-android-draws.html)
[Creating Custom Views](https://developer.android.com/training/custom-views/index.html)
[@JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)
[Custom Components](https://developer.android.com/guide/topics/ui/custom-components.html#compound)
[onMeasure()](https://developer.android.com/reference/android/view/View.html#onMeasure%28int,%20int%29)
[onSizeChanged()](https://developer.android.com/reference/android/view/View.html#onSizeChanged%28int,%20int,%20int,%20int%29)
[onDraw()](https://developer.android.com/reference/android/view/View.html#onDraw%28android.graphics.Canvas%29)
[Canvas](https://developer.android.com/reference/android/graphics/Canvas.html)
[Paint](https://developer.android.com/reference/android/graphics/Paint.html)
[rawText()](https://developer.android.com/reference/android/graphics/Canvas.html#drawText%28char[],%20int,%20int,%20float,%20float,%20android.graphics.Paint%29)
[setTypeface()](https://developer.android.com/reference/android/graphics/Paint.html#setTypeface%28android.graphics.Typeface%29)
[setColor()](https://developer.android.com/reference/android/graphics/Paint.html#setColor%28int%29)
[drawRect()](https://developer.android.com/reference/android/graphics/Canvas.html#drawRect%28android.graphics.Rect,%20android.graphics.Paint%29)
[drawOval()](https://developer.android.com/reference/android/graphics/Canvas.html#drawOval%28android.graphics.RectF,%20android.graphics.Paint%29)
[drawArc()](https://developer.android.com/reference/android/graphics/Canvas.html#drawArc%28android.graphics.RectF,%20float,%20float,%20boolean,%20android.graphics.Paint%29)
[drawBitmap()](https://developer.android.com/reference/android/graphics/Canvas.html#drawBitmap%28android.graphics.Bitmap,%20android.graphics.Matrix,%20android.graphics.Paint%29)
[setStyle()](https://developer.android.com/reference/android/graphics/Paint.html#setStyle%28android.graphics.Paint.Style%29)
[invalidate()](https://developer.android.com/reference/android/view/View.html#invalidate%28%29)
[View](https://developer.android.com/reference/android/view/View.html)
[PointF](https://developer.android.com/reference/android/graphics/PointF)
[drawCircle()](https://developer.android.com/reference/android/graphics/Canvas.html#drawCircle%28float,%20float,%20float,%20android.graphics.Paint%29)
[drawText()](https://developer.android.com/reference/android/graphics/Canvas.html#drawText%28java.lang.String,%20int,%20int,%20float,%20float,%20android.graphics.Paint%29)


### CustomView interactions methods

[isClickable](https://developer.android.com/reference/android/view/View.html#isClickable%28%29)
Enables the custom view to respond to clicks

[performClick()](https://developer.android.com/reference/android/view/View#performClick%28%29)
Used to perform operation when the view is clicked.
Normally with a standard Android View, you implement the `onClickListener { }` to perform an action 
when the user clicks in that view. For the `CustomView` you have to implement the view classes 
`performClick()` method instead and call `super.performClick()`.
The default `performClick()` also calls the `onClickListener()`, so you can add you actions to 
`performClick()`, and leave the `onClickListener { }` for further customizations by you or other 
developers that might use your CustomView.

[invalidate()](https://developer.android.com/reference/android/view/View.html#invalidate%28%29)
Instruct the Android System to call the onDraw method to redraw the view.

[OnClickListener()](https://developer.android.com/reference/android/view/View.OnClickListener.html)
Used to perform an action when the user clicks the view

##### Additional Resources
[android-ktx](https://android.github.io/android-ktx/core-ktx/index.html)
[withStyledAttributes](https://android.github.io/android-ktx/core-ktx/androidx.content/android.content.-context/index.html)
[Android ktx Documentation](https://developer.android.com/kotlin/ktx)
[Original Announcement blog post](https://android-developers.googleblog.com/2018/02/introducing-android-ktx-even-sweeter.html)
[Input Events](https://developer.android.com/guide/topics/ui/ui-events.html)
[Quick Intro to Creating a Custom View in Android](https://youtu.be/ktbYUrlN_Ws)
[Android Custom View Tutorial](https://youtu.be/sb9OEl4k9Dk)


## Drawing on Canvas Object

[Drawables](https://developer.android.com/guide/topics/graphics/drawables)
[Canvas](https://developer.android.com/reference/android/graphics/Canvas.html)
[View class](https://developer.android.com/reference/android/view/View.html)

[onDraw()](https://developer.android.com/reference/android/view/View.html#onDraw%28android.graphics.Canvas%29)


[Paint](https://developer.android.com/reference/android/graphics/Paint)
[Path](https://developer.android.com/reference/kotlin/android/graphics/Path.html)
[Paint.Style](https://developer.android.com/reference/android/graphics/Paint.Style.html)
[Paint.Join](https://developer.android.com/reference/android/graphics/Paint.Join.html)
[Paint.Cap](https://developer.android.com/reference/android/graphics/Paint.Cap.html)

[MotionEvent](https://developer.android.com/reference/kotlin/android/view/MotionEvent.html)
[ViewConfiguration.get(context).scaledTouchSlop](https://developer.android.com/reference/kotlin/android/view/ViewConfiguration.html#getScaledTouchSlop%28%29)
[Bezier Curves](https://en.wikipedia.org/wiki/B%C3%A9zier_curve)

#### Series of articles (advanced)

[Graphics Architecture](https://source.android.com/devices/graphics/)

#### Clipping Canvas Objects

[Overdraw](https://developer.android.com/topic/performance/rendering/overdraw.html)

> One purpose of clipping is to reduce overdraw. Over draw is when a pixel on the screen is draw 
> more the once to display the final image.
> Reducing overdraw we minimize the number of times a pixel or region of is drawn in order 
> do maximize drawing performance
[Clipping](https://en.wikipedia.org/wiki/Clipping_%28computer_graphics%29)
[@JvmOverloads](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.jvm/-jvm-overloads/index.html)

[Canvas.clipRect(left, top, right, bottom)](https://developer.android.com/reference/android/graphics/Canvas.html)
[Paint.Align](https://developer.android.com/reference/android/graphics/Paint.Align.html)
[Canvas.drawText()](https://developer.android.com/reference/kotlin/android/graphics/Canvas.html#drawtext_1)
[addRoundRect()](https://developer.android.com/reference/kotlin/android/graphics/Path.html#addroundrect_1)
[Path.Direction](https://developer.android.com/reference/android/graphics/Path.Direction)
[RectF](https://developer.android.com/reference/kotlin/android/graphics/RectF.html)
