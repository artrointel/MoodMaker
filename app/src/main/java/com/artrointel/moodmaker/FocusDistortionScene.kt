package com.artrointel.moodmaker

import android.graphics.BitmapFactory
import android.graphics.PointF
import com.artrointel.moodmaker.kotesrenderengine.*
import com.artrointel.moodmaker.kotesrenderengine.common.Vector3
import com.artrointel.moodmaker.kotesrenderengine.renderers.RendererBase
import com.artrointel.moodmaker.kotesrenderengine.renderers.WarpTransitionRenderer
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
        val screenWidth = world.getDisplaySize().x.toFloat()
        val screenHeight = world.getDisplaySize().y.toFloat()
        distortionNode = createFocusDistortionNode(R.drawable.background_image)
        distortionNode.transform.position = Vector3(
            screenWidth*0.5f,
            screenHeight*0.5f, 0f)
        distortionNode.transform.scale = Vector3(
            screenWidth,
            screenHeight, 0f)
        distortionNode.setResolution(screenWidth.toInt(), screenHeight.toInt())
        distortionNode.setRadius(1.0f)
        world.getRoot().appendChild(distortionNode)

        maskNode = createImageNode(R.drawable.circle_masking)
        maskNode.transform.scale = Vector3(screenWidth, screenHeight, 0f)
        world.getRoot().appendChild(maskNode)
    }

    fun setMaskImageAlpha(alpha: Float) {
        maskNode.setAlpha(alpha)
    }

    fun setMaskImageScale(xScale: Float, yScale: Float) {
        maskNode.transform.position = Vector3(world.getDisplaySize().x * 0.5f, world.getDisplaySize().y * 0.5f, 0.0f)
        maskNode.transform.scale = Vector3(world.getDisplaySize().x * xScale, world.getDisplaySize().y * yScale, 0.0f)
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

    private fun createWarpRenderer(resourceIdBackground: Int, resourceIdAod: Int): WarpTransitionRenderer {
        var bmp = BitmapFactory.decodeResource(world.context.resources, resourceIdBackground)

        return WarpTransitionRenderer(
            createBuffer(resourceIdBackground),
            createBuffer(resourceIdAod), bmp.width, bmp.height)
    }

    private fun createBuffer(resourceId: Int): ByteBuffer {
        var bmp = BitmapFactory.decodeResource(world.context.resources, resourceId)
        var buffer = ByteBuffer.allocate(bmp.byteCount)
        bmp.copyPixelsToBuffer(buffer)
        buffer.rewind()
        return buffer
    }
}