package com.michel.vkmap.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import com.yandex.runtime.image.ImageProvider

private const val DEFAULT_ICON_SIZE: Int = 192
private const val DEFAULT_BORDER_WIDTH: Int = 0
private const val OFFLINE_BORDER_COLOR: Int = Color.WHITE
private const val ONLINE_BORDER_COLOR: Int = Color.GREEN
private const val DEFAULT_TEXT_COLOR: Int = Color.GRAY
private const val DEFAULT_LABEL_WIDTH: Int = 128
private const val DEFAULT_LABEL_HEIGHT: Int = 32
private const val DEFAULT_TEXT_SIZE: Float = 28f

class PlaceMarkIconView(private val iconBitmap: Bitmap): ImageProvider() {

    private val centerX: Float = (DEFAULT_ICON_SIZE / 2).toFloat()
    private val placeMarkIconBitMap = Bitmap.createBitmap(
        DEFAULT_ICON_SIZE,
        (DEFAULT_ICON_SIZE + DEFAULT_LABEL_HEIGHT),
        Bitmap.Config.ARGB_8888
    )

    init{
        val canvas = Canvas(placeMarkIconBitMap)
        drawIcon(canvas)
        drawLabel(canvas)
    }

    override fun getId(): String {
        Log.e("VKMAP", "ImageProvider getId()")
        return "id"
    }

    override fun getImage(): Bitmap {
        Log.e("VKMAP", "ImageProvider getImage()")
        return placeMarkIconBitMap
    }

    private fun drawIcon(canvas: Canvas){
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.WHITE

        val rect = RectF(Rect(0, 0, DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE))

        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawRoundRect(rect, centerX, centerX, paint)

        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))

        canvas.drawBitmap(iconBitmap, 0f, 0f, paint)
    }

    private fun drawLabel(canvas: Canvas){
        val paint = Paint()
        paint.color = DEFAULT_TEXT_COLOR
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = DEFAULT_TEXT_SIZE

        canvas.drawText("", centerX, (DEFAULT_ICON_SIZE + 16).toFloat(), paint)
    }

}