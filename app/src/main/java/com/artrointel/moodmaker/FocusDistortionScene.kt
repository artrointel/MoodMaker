package com.artrointel.moodmaker

import android.graphics.BitmapFactory
import android.graphics.Point
import com.artrointel.moodmaker.kotesrenderengine.ImageNode
import com.artrointel.moodmaker.kotesrenderengine.RenderWorldBase
import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4
import java.nio.ByteBuffer

class FocusDistortionScene {
    private constructor()

    lateinit var world: RenderWorldBase
    lateinit var maskNode: ImageNode

    companion object {
        @Volatile private var instance: FocusDistortionScene? = null
        @JvmStatic fun getInstance() : FocusDistortionScene? {
            synchronized(this) {
                if(instance == null) {
                    instance = FocusDistortionScene()
                }
                return instance
            }
        }
    }

    fun initWorld() {
        var imgNode = createImageNode(R.drawable.background_image)
        imgNode.setSize(world.getDisplaySize().x, world.getDisplaySize().y, 0)
        world.getRoot().appendChild(imgNode)

        maskNode = createImageNode(R.drawable.circle_masking)
        maskNode.setSize(world.getDisplaySize().x, world.getDisplaySize().y, 0)
        world.getRoot().appendChild(maskNode)
    }

    //fun setFocus(center: Point)
    //fun setFocusImageScale(scale: Point)
    fun setFocusImageAlpha(alpha: Float) {
        maskNode.setAlpha(alpha)
    }

    fun setFocusImageScale(xScale: Float, yScale: Float) {
        maskNode.resetMatrix()
        maskNode.transform.translate(world.getDisplaySize().x * 0.5f, world.getDisplaySize().y * 0.5f, 0.0f)
        maskNode.transform.scale(world.getDisplaySize().x * 0.5f * xScale, world.getDisplaySize().y * 0.5f * yScale, 0.0f)
    }


    private fun createImageNode(resourceId: Int): ImageNode {
        var bmp = BitmapFactory.decodeResource(world.context.resources, resourceId)
        var buffer = ByteBuffer.allocate(bmp.byteCount)
        bmp.copyPixelsToBuffer(buffer)
        buffer.rewind()
        var imgNode = ImageNode(buffer, bmp.width, bmp.height)
        return imgNode
    }
}