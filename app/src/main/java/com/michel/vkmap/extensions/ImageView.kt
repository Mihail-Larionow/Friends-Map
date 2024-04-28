package com.michel.vkmap.extensions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.widget.ImageView

fun ImageView.setIconByteArray(input: ByteArray){
    val resourceBitmap = BitmapFactory.decodeByteArray(input, 0, input.size)

    val size = resourceBitmap.height
    val center: Float = (size / 2).toFloat()

    val placeMarkIconBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)

    val paint = Paint()
    paint.isAntiAlias = true

    val canvas = Canvas(placeMarkIconBitmap)
    val rect = RectF(Rect(0, 0, size, size))

    canvas.drawARGB(0, 0, 0, 0)
    canvas.drawRoundRect(rect, center, center, paint)
    paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
    canvas.drawBitmap(resourceBitmap, 0f, 0f, paint)

    this.setImageBitmap(placeMarkIconBitmap)
}