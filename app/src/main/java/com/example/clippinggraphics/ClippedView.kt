package com.example.clippinggraphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Region
import android.os.Build
import android.util.AttributeSet
import android.view.View

/**
 * Created by jesselima on 12/04/21.
 * This is a part of the project ClippingGraphics.
 */
class ClippedView @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null,
	defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {

	private val paint = Paint().apply {
		isAntiAlias = true
		strokeWidth = resources.getDimension(R.dimen.strokeWidth)
		textSize = resources.getDimension(R.dimen.textSize)
	}

	private val path = Path()

	// Dimensions for a clipping rectangle around the whole set of shapes.
	private val clipRectRight = resources.getDimension(R.dimen.clipRectRight)
	private val clipRectBottom = resources.getDimension(R.dimen.clipRectBottom)
	private val clipRectTop = resources.getDimension(R.dimen.clipRectTop)
	private val clipRectLeft = resources.getDimension(R.dimen.clipRectLeft)

	// Variables for the inset of a rectangle and the offset of a small rectangle.
	private val rectInset = resources.getDimension(R.dimen.rectInset)
	private val smallRectOffset = resources.getDimension(R.dimen.smallRectOffset)

	// Variable for the radius of a circle. This is the circle that is drawn inside the rectangle.
	private val circleRadius = resources.getDimension(R.dimen.circleRadius)

	// Offset and a text size for text that is drawn inside the rectangle.
	private val textOffSet = resources.getDimension(R.dimen.textOffset)
	private val textSize = resources.getDimension(R.dimen.textSize)

	// Set up the coordinates for two columns.
	private val columnOne = rectInset
	private val columnTwo = columnOne + rectInset + clipRectRight

	// Add the coordinates for each row, including the final row for the transformed text.
	private val rowOne = rectInset
	private val rowTwo = rowOne + rectInset + clipRectBottom
	private val rowThree = rowTwo + rectInset + clipRectBottom
	private val rowFour = rowThree + rectInset + clipRectBottom
	private val rowText = rowFour + rectInset + clipRectBottom
	private val rejectRow = rowFour + rectInset + 2 * clipRectBottom

	private var rectF = RectF(
		rectInset,
		rectInset,
		clipRectRight - rectInset,
		clipRectBottom - rectInset
	)

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		drawBackAndUnclippedRectangle(canvas = canvas)
		drawDifferenceClippingExample(canvas = canvas)
		drawCircularClippingExample(canvas = canvas)
		drawIntersectionClippingExample(canvas = canvas)
		drawCombinedClippingExample(canvas = canvas)
		drawRoundedRectangleClippingExample(canvas = canvas)
		drawOutsideClippingExample(canvas = canvas)
		drawTranslatedTextExample(canvas = canvas)
		drawSkewedTextExample(canvas = canvas)
		drawQuickRejectExample(canvas = canvas)
	}

	private fun drawBackAndUnclippedRectangle(canvas: Canvas) {
		canvas.drawColor(Color.WHITE)
		canvas.save()
		// Translate to the first row and column position.
		canvas.translate(columnOne, rowOne)
		// Draw
		drawClippedRectangle(canvas = canvas)
		// restore the canvas to its previous state.
		canvas.restore()
	}

	private fun drawClippedRectangle(canvas: Canvas) {
		// Set the boundaries for the clipping rectangle where draw operations can write to.
		canvas.clipRect(clipRectLeft, clipRectTop, clipRectRight, clipRectBottom)
		canvas.drawColor(Color.GRAY)

		// Draws the red line
		paint.color = Color.RED
		canvas.drawLine(clipRectLeft, clipRectTop, clipRectRight, clipRectBottom, paint)

		// Draws the green circle
		paint.color = Color.GREEN
		canvas.drawCircle(circleRadius, clipRectBottom - circleRadius, circleRadius, paint)

		// Draws the blue text
		paint.color = Color.BLUE
		paint.textSize = textSize
		/**
		 * The Paint.Align property specifies which side of the text to align to the origin
		 * (not which side of the origin the text goes, or where in the region it is aligned!).
		 * Aligning the right side of the text to the origin places it on the left of the origin.
		 */
		paint.textAlign = Paint.Align.RIGHT
		canvas.drawText(
			context.getString(R.string.clipping),
			clipRectRight,
			textOffSet,
			paint
		)
	}

	/**
	 * - Save the canvas.
	 * - Translate the origin of the canvas into open space to the first row, second column, to the right of the first rectangle.
	 * - Apply two clipping rectangles. The DIFFERENCE operator subtracts the second rectangle from the first one.
	 * - Call the drawClippedRectangle() method to draw the modified canvas.
	 * - Restore the canvas state.
	 * - Run your app and it should look like this.
	 */
	private fun drawDifferenceClippingExample(canvas: Canvas) {
		canvas.save()

		// Move the origin to the right for the next rectangle.
		canvas.translate(columnTwo,rowOne)

		// Use the subtraction of two clipping rectangles to create a frame.
		canvas.clipRect(
			2 * rectInset,2 * rectInset,
			clipRectRight - 2 * rectInset,
			clipRectBottom - 2 * rectInset
		)

		// The method clipRect(float, float, float, float, Region.Op
		// .DIFFERENCE) was deprecated in API level 26. The recommended
		// alternative method is clipOutRect(float, float, float, float),
		// which is currently available in API level 26 and higher.
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
			canvas.clipRect(
				4 * rectInset,4 * rectInset,
				clipRectRight - 4 * rectInset,
				clipRectBottom - 4 * rectInset,
				Region.Op.DIFFERENCE
			)
		else {
			canvas.clipOutRect(
				4 * rectInset,4 * rectInset,
				clipRectRight - 4 * rectInset,
				clipRectBottom - 4 * rectInset
			)
		}
		drawClippedRectangle(canvas)
		canvas.restore()
	}

	private fun drawCircularClippingExample(canvas: Canvas) {

		canvas.save()
		canvas.translate(columnOne, rowTwo)
		// Clears any lines and curves from the path but unlike reset(),
		// keeps the internal data structure for faster reuse.
		path.rewind()
		path.addCircle(
			circleRadius,clipRectBottom - circleRadius,
			circleRadius,Path.Direction.CCW
		)
		// The method clipPath(path, Region.Op.DIFFERENCE) was deprecated in
		// API level 26. The recommended alternative method is
		// clipOutPath(Path), which is currently available in
		// API level 26 and higher.
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
			canvas.clipPath(path, Region.Op.DIFFERENCE)
		} else {
			canvas.clipOutPath(path)
		}
		drawClippedRectangle(canvas)
		canvas.restore()
	}

	private fun drawIntersectionClippingExample(canvas: Canvas) {
		canvas.save()
		canvas.translate(columnTwo, rowTwo)
		canvas.clipRect(
			clipRectLeft, clipRectTop,
			clipRectRight - smallRectOffset,
			clipRectBottom - smallRectOffset
		)
		// The method clipRect(float, float, float, float, Region.Op
		// .INTERSECT) was deprecated in API level 26. The recommended
		// alternative method is clipRect(float, float, float, float), which
		// is currently available in API level 26 and higher.
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
			canvas.clipRect(
				clipRectLeft + smallRectOffset,
				clipRectTop + smallRectOffset,
				clipRectRight, clipRectBottom,
				Region.Op.INTERSECT
			)
		} else {
			canvas.clipRect(
				clipRectLeft + smallRectOffset,
				clipRectTop + smallRectOffset,
				clipRectRight, clipRectBottom
			)
		}
		drawClippedRectangle(canvas)
		canvas.restore()
	}

	private fun drawCombinedClippingExample(canvas: Canvas) {
		canvas.save()
		canvas.translate(columnOne, rowThree)
		path.rewind()
		path.addCircle(
			clipRectLeft + rectInset + circleRadius,
			clipRectTop + circleRadius + rectInset,
			circleRadius,Path.Direction.CCW
		)
		path.addRect(
			clipRectRight / 2 - circleRadius,
			clipRectTop + circleRadius + rectInset,
			clipRectRight / 2 + circleRadius,
			clipRectBottom - rectInset,Path.Direction.CCW
		)
		canvas.clipPath(path)
		drawClippedRectangle(canvas)
		canvas.restore()
	}

	/**
	 * The addRoundRect() function takes a rectangle.
	 * Values for the x and y values of the corner radius.
	 * The direction to wind the round-rectangle's contour.
	 * Path.Direction specifies how closed shapes (e.g. rects, ovals) are oriented when they are
	 * added to a path. CCW stands for counter-clockwise.
	 */
	private fun drawRoundedRectangleClippingExample(canvas: Canvas) {
		canvas.save()
		canvas.translate(columnTwo,rowThree)
		path.rewind()
		path.addRoundRect(
			rectF,
			clipRectRight / 4,
			clipRectRight / 4,
			Path.Direction.CCW
		)
		canvas.clipPath(path)
		drawClippedRectangle(canvas)
		canvas.restore()
	}

	/**
	 * Clips outside the rectangle by doubling the insets of the clipping rectangle
	 */
	private fun drawOutsideClippingExample(canvas: Canvas) {
		canvas.save()
		canvas.translate(columnOne,rowFour)
		canvas.clipRect(
			2 * rectInset,
			2 * rectInset,
			clipRectRight - 2 * rectInset,
			clipRectBottom - 2 * rectInset
		)
		drawClippedRectangle(canvas)
		canvas.restore()
	}

	private fun drawTranslatedTextExample(canvas: Canvas) {
		canvas.save()
		paint.color = Color.RED
		// Align the RIGHT side of the text with the origin.
		paint.textAlign = Paint.Align.LEFT
		// Apply transformation to canvas.
		canvas.translate(columnTwo, rowText)
		// Draw text.
		canvas.drawText(
			context.getString(R.string.translated),
			clipRectLeft,
			clipRectTop,
			paint
		)
		canvas.restore()
	}

	private fun drawSkewedTextExample(canvas: Canvas) {
		canvas.save()
		paint.color = Color.DKGRAY
		paint.textAlign = Paint.Align.RIGHT
		// Position text.
		canvas.translate(columnTwo, rowText)
		// Apply skew transformation.
		canvas.skew(0.2f, 0.3f)
		canvas.drawText(
			context.getString(R.string.skewed),
			clipRectLeft,
			clipRectTop,
			paint
		)
		canvas.restore()
	}

	/**
		The quickReject() method allows you to check whether a specified rectangle or path would
		lie	completely outside the currently visible regions, after all transformations have been
		applied.

		The quickReject() method is incredibly useful when you are constructing more complex
		drawings and need to do so as fast as possible. With quickReject(), you can decide
		efficiently which objects you do not have to draw at all, and there is no need to write
		your own intersection logic.

		The quickReject() method returns true if the rectangle or path would not be visible at all
		on the screen. For partial overlaps, you still have to do your own checking.
		The EdgeType is either AA (Antialiased: Treat edges by rounding-out, because they may be
		antialiased) or BW (Black-White: Treat edges by just rounding to nearest pixel boundary)
		for just rounding to the nearest pixel.
	*/
	private fun drawQuickRejectExample(canvas: Canvas) {
		val inClipRectangle = RectF(clipRectRight / 2,
			clipRectBottom / 2,
			clipRectRight * 2,
			clipRectBottom * 2)

		val notInClipRectangle = RectF(RectF(
			clipRectRight + 1,
			clipRectBottom+ 1,
			clipRectRight * 2,
			clipRectBottom * 2))

		canvas.save()
		canvas.translate(columnOne, rejectRow)
		canvas.clipRect(
			clipRectLeft,
			clipRectTop,
			clipRectRight,
			clipRectBottom
		)
		if (canvas.quickReject(inClipRectangle, Canvas.EdgeType.AA)) {
			canvas.drawColor(Color.WHITE)
		} else {
			canvas.drawColor(Color.BLACK)
			canvas.drawRect(inClipRectangle, paint)
		}
		canvas.restore()
	}

}
