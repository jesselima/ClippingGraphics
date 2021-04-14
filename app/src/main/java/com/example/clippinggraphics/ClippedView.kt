package com.example.clippinggraphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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

	// Dimensions for a clipping rectangle around the whole set of shapes.
	private val clipRectRight = resources.getDimension(R.dimen.clipRectRight)
	private val clipRectBottom = resources.getDimension(R.dimen.clipRectBottom)
	private val clipRectTop = resources.getDimension(R.dimen.clipRectTop)
	private val clipRectLeft = resources.getDimension(R.dimen.clipRectLeft)

	// Variable for the radius of a circle. This is the circle that is drawn inside the rectangle.
	private val circleRadius = resources.getDimension(R.dimen.circleRadius)

	// Offset and a text size for text that is drawn inside the rectangle.
	private val textOffSet = resources.getDimension(R.dimen.textOffset)
	private val textSize = resources.getDimension(R.dimen.textSize)

	override fun onDraw(canvas: Canvas) {
		super.onDraw(canvas)
		drawTranslatedTextExample(canvas)
	}

	private fun drawTranslatedTextExample(canvas: Canvas) {
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
}
