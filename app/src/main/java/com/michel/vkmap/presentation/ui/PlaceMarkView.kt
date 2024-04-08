package com.michel.vkmap.presentation.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import com.yandex.runtime.image.ImageProvider

class PlaceMarkView(){

    companion object{
        private const val DEFAULT_ICON_SIZE: Int = 192
        private const val DEFAULT_BORDER_WIDTH: Float = 20f
        private const val OFFLINE_BORDER_COLOR: Int = Color.WHITE
        private const val ONLINE_BORDER_COLOR: Int = Color.GREEN
        private const val DEFAULT_TEXT_COLOR: Int = Color.GRAY
        private const val DEFAULT_LABEL_WIDTH: Int = 128
        private const val DEFAULT_LABEL_HEIGHT: Int = 32
        private const val DEFAULT_TEXT_SIZE: Float = 28f
    }

    var resourceBitmap: Bitmap = Bitmap.createBitmap(
        DEFAULT_ICON_SIZE,
        (DEFAULT_ICON_SIZE + DEFAULT_LABEL_HEIGHT),
        Bitmap.Config.ALPHA_8
    )

    private val placeMarkIconBitmap = Bitmap.createBitmap(
        DEFAULT_ICON_SIZE,
        (DEFAULT_ICON_SIZE + DEFAULT_LABEL_HEIGHT),
        Bitmap.Config.ARGB_8888
    )

    private val centerX: Float = (DEFAULT_ICON_SIZE / 2).toFloat()

    fun getImage(): ImageProvider {
        this.update()
        return ImageProvider.fromBitmap(placeMarkIconBitmap)
    }

    fun changeColor(){
        val canvas = Canvas(placeMarkIconBitmap)
        drawIcon(canvas)
        drawBorder(canvas, OFFLINE_BORDER_COLOR)
        drawLabel(canvas)
    }

    private fun update(){
        val canvas = Canvas(placeMarkIconBitmap)
        drawIcon(canvas)
        drawBorder(canvas, ONLINE_BORDER_COLOR)
        drawLabel(canvas)
    }

    private fun drawIcon(canvas: Canvas){
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.WHITE

        val rect = RectF(Rect(0, 0, DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE))

        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawRoundRect(rect, centerX, centerX, paint)

        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))

        canvas.drawBitmap(resourceBitmap, 0f, 0f, paint)
    }

    private fun drawBorder(canvas: Canvas, borderColor: Int){
        var paint = Paint()
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = DEFAULT_BORDER_WIDTH

        canvas.drawCircle(centerX, centerX, centerX - DEFAULT_BORDER_WIDTH /2, paint)

        val offSet = (DEFAULT_ICON_SIZE - DEFAULT_LABEL_WIDTH)/2
        val rect = RectF(Rect(offSet, DEFAULT_ICON_SIZE -16, offSet + DEFAULT_LABEL_WIDTH, DEFAULT_ICON_SIZE + DEFAULT_LABEL_HEIGHT))

        paint = Paint()
        paint.color = borderColor
        paint.style = Paint.Style.FILL
        canvas.drawRoundRect(rect, DEFAULT_BORDER_WIDTH /2, DEFAULT_BORDER_WIDTH /2, paint)
    }

    private fun drawLabel(canvas: Canvas){
        val paint = Paint()
        paint.color = DEFAULT_TEXT_COLOR
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = DEFAULT_TEXT_SIZE

        canvas.drawText("", centerX, (DEFAULT_ICON_SIZE + 16).toFloat(), paint)
    }

}