package com.example.clippinggraphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
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

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		drawBackAndUnclippedRectangle(canvas = canvas)
		drawDifferenceClippingExample(canvas = canvas)
		drawCircularClippingExample(canvas = canvas)
		drawIntersectionClippingExample(canvas = canvas)
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
}
