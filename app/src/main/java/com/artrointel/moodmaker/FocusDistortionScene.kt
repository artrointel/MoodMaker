package com.artrointel.moodmaker

import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.PointF
import com.artrointel.moodmaker.kotesrenderengine.FocusDistortionNode
import com.artrointel.moodmaker.kotesrenderengine.ImageNode
import com.artrointel.moodmaker.kotesrenderengine.RenderWorldBase
import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4
import java.nio.ByteBuffer

class FocusDistortionScene {
    private constructor()

    lateinit var world: RenderWorldBase
    lateinit var maskNode: ImageNode
    lateinit var distortionNode: FocusDistortionNode

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
        distortionNode = createFocusDistortionNode(R.drawable.background_image)
        distortionNode.setSize(world.getDisplaySize().x, world.getDisplaySize().y, 0)
        distortionNode.setResolution(world.getDisplaySize().x, world.getDisplaySize().y)
        distortionNode.setRadius(1.0f)
        world.getRoot().appendChild(distortionNode)

        maskNode = createImageNode(R.drawable.circle_masking)
        maskNode.setSize(world.getDisplaySize().x, world.getDisplaySize().y, 0)
        world.getRoot().appendChild(maskNode)
    }

    fun setMaskImageAlpha(alpha: Float) {
        maskNode.setAlpha(alpha)
    }

    fun setMaskImageScale(xScale: Float, yScale: Float) {
        maskNode.resetMatrix()
        maskNode.transform.translate(world.getDisplaySize().x * 0.5f, world.getDisplaySize().y * 0.5f, 0.0f)
        maskNode.transform.scale(world.getDisplaySize().x * 0.5f * xScale, world.getDisplaySize().y * 0.5f * yScale, 0.0f)
    }

    fun setRadius(radius: Float) {
        distortionNode.setRadius(radius)
    }

    fun setDepth(depth: Float) {
        distortionNode.setDepth(depth)
    }

    fun setFocus(focus: PointF) {
        distortionNode.setFocus(focus)
    }

    private fun createFocusDistortionNode(resourceId: Int): FocusDistortionNode {
        var bmp = BitmapFactory.decodeResource(world.context.resources, resourceId)
        var buffer = ByteBuffer.allocate(bmp.byteCount)
        bmp.copyPixelsToBuffer(buffer)
        buffer.rewind()
        return FocusDistortionNode(buffer, bmp.width, bmp.height)
    }

    private fun createImageNode(resourceId: Int): ImageNode {
        var bmp = BitmapFactory.decodeResource(world.context.resources, resourceId)
        var buffer = ByteBuffer.allocate(bmp.byteCount)
        bmp.copyPixelsToBuffer(buffer)
        buffer.rewind()
        return ImageNode(buffer, bmp.width, bmp.height)
    }
}