package com.artrointel.moodmaker.utils

import android.content.Context
import android.graphics.BitmapFactory
import java.nio.ByteBuffer

class ImageLoader {
    companion object {
        class BitmapInfo(
            var resourceId: Int,
            var width: Int,
            var height: Int,
            var buffer: ByteBuffer) {
        }

        fun createImage(context: Context, resourceId: Int): BitmapInfo {
            var bmp = BitmapFactory.decodeResource(context.resources, resourceId)
            var buffer = ByteBuffer.allocate(bmp.byteCount)
            bmp.copyPixelsToBuffer(buffer)
            buffer.rewind()
            return BitmapInfo(resourceId, bmp.width, bmp.height, buffer)
        }
    }
}