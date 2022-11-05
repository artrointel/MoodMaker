package com.artrointel.moodmaker.scene

import com.artrointel.moodmaker.R
import com.artrointel.moodmaker.kotesrenderengine.ImageNode
import com.artrointel.moodmaker.kotesrenderengine.RenderWorldBase
import com.artrointel.moodmaker.kotesrenderengine.Scene
import com.artrointel.moodmaker.kotesrenderengine.common.Vector3
import com.artrointel.moodmaker.utils.ImageLoader
import kotlin.math.sqrt

abstract class DemoTransition(world: RenderWorldBase) : Scene(world)  {
    private var maskNode: ImageNode = createImageNode(R.drawable.circle_masking)


    private fun setMaskImageAlpha(alpha: Float) {
        maskNode.setAlpha(alpha)
    }

    private fun setMaskImageScale(xScale: Float, yScale: Float) {
        maskNode.transform.position = Vector3(world.getDisplaySize().x * 0.5f, world.getDisplaySize().y * 0.5f, 0.0f)
        maskNode.transform.scale = Vector3(world.getDisplaySize().x * xScale, world.getDisplaySize().y * yScale, 0.0f)
    }

    private fun createImageNode(resourceId: Int): ImageNode {
        val imageInfo = ImageLoader.createImage(world.context, resourceId)
        return ImageNode(imageInfo.buffer, imageInfo.width, imageInfo.height)
    }

    final override fun initialize() {
        onInitialize()
        world.getRoot().appendChild(maskNode)
    }

    abstract fun onInitialize()

    override fun setTime(time: Float) {
        val sqrtProgress = sqrt(time.toDouble()).toFloat()

        setMaskImageAlpha(sqrtProgress)
        setMaskImageScale(
            5.0f - 4.0f * sqrtProgress,
            5.0f - 4.0f * sqrtProgress)
    }
}