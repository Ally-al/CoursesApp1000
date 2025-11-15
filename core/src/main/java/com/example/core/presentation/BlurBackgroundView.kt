package com.example.core.presentation

import android.content.Context
import android.graphics.*
import android.renderscript.*
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.ImageView

class BlurBackgroundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var sourceImageView: ImageView? = null
    private var blurredBitmap: Bitmap? = null
    private val overlayPaint = Paint().apply {
        color = 0x4D32333A.toInt()
    }
    private val blurRadius = 16f
    var cornerRadius = 8f
        set(value) {
            field = value
            invalidate()
        }

    init {
        setWillNotDraw(false)
    }

    fun setSourceImageView(imageView: ImageView) {
        sourceImageView = imageView
        updateBlur()
    }

    private fun updateBlur() {
        val imageView = sourceImageView ?: return
        if (width == 0 || height == 0) return

        val location = IntArray(2)
        getLocationInWindow(location)

        val imageLocation = IntArray(2)
        imageView.getLocationInWindow(imageLocation)

        val relativeX = (location[0] - imageLocation[0]).coerceAtLeast(0)
        val relativeY = (location[1] - imageLocation[1]).coerceAtLeast(0)

        val drawable = imageView.drawable ?: return
        val fullBitmap = Bitmap.createBitmap(
            imageView.width,
            imageView.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(fullBitmap)
        drawable.setBounds(0, 0, imageView.width, imageView.height)
        drawable.draw(canvas)

        val croppedWidth = width.coerceAtMost(fullBitmap.width - relativeX)
        val croppedHeight = height.coerceAtMost(fullBitmap.height - relativeY)

        if (croppedWidth <= 0 || croppedHeight <= 0) return

        val croppedBitmap = Bitmap.createBitmap(
            fullBitmap,
            relativeX,
            relativeY,
            croppedWidth,
            croppedHeight
        )

        blurredBitmap?.recycle()
        blurredBitmap = blurBitmap(croppedBitmap)

        fullBitmap.recycle()
        croppedBitmap.recycle()

        invalidate()
    }

    private fun blurBitmap(original: Bitmap): Bitmap {
        val scale = 0.5f
        val width = (original.width * scale).toInt().coerceAtLeast(1)
        val height = (original.height * scale).toInt().coerceAtLeast(1)

        val inputBitmap = Bitmap.createScaledBitmap(original, width, height, true)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        try {
            val rs = RenderScript.create(context)
            val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
            val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
            val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)

            blurScript.setRadius(blurRadius.coerceIn(0.1f, 25f))
            blurScript.setInput(tmpIn)
            blurScript.forEach(tmpOut)
            tmpOut.copyTo(outputBitmap)

            tmpIn.destroy()
            tmpOut.destroy()
            blurScript.destroy()
            rs.destroy()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        inputBitmap.recycle()
        return outputBitmap
    }

    override fun dispatchDraw(canvas: Canvas) {
        blurredBitmap?.let { bitmap ->
            val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())

            val path = Path().apply {
                addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW)
            }

            canvas.save()
            canvas.clipPath(path)

            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true)
            canvas.drawBitmap(scaledBitmap, 0f, 0f, null)
            scaledBitmap.recycle()

            canvas.drawRect(rect, overlayPaint)
            canvas.restore()
        }
        super.dispatchDraw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w > 0 && h > 0) {
            post { updateBlur() }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        blurredBitmap?.recycle()
        blurredBitmap = null
    }
}
