package com.artrointel.moodmaker.scene

import android.graphics.PointF
import com.artrointel.moodmaker.R
import com.artrointel.moodmaker.kotesrenderengine.*
import com.artrointel.moodmaker.kotesrenderengine.common.Vector3
import com.artrointel.moodmaker.utils.ImageLoader

class FocusDistortionTransition(world: RenderWorldBase) : DemoTransition(world) {
    private var distortionNode: FocusDistortionNode = createFocusDistortionNode(R.drawable.background_image)

    override fun onInitialize() {
        val screenWidth = world.getDisplaySize().x.toFloat()
        val screenHeight = world.getDisplaySize().y.toFloat()
        distortionNode.transform.position = Vector3(
            screenWidth*0.5f,
            screenHeight*0.5f, 0f)
        distortionNode.transform.scale = Vector3(
            screenWidth,
            screenHeight, 0f)
        distortionNode.setResolution(screenWidth.toInt(), screenHeight.toInt())
        distortionNode.setRadius(1.0f)
        world.getRoot().appendChild(distortionNode)
    }

    override fun setTime(time: Float) {
        super.setTime(time)
        val squareProgress = time * time
        setRadius(squareProgress * 4.0f + 1.0f)
        setDepth(squareProgress * 6.0f)
    }

    private fun setRadius(radius: Float) {
        distortionNode.setRadius(radius)
    }

    private fun setDepth(depth: Float) {
        distortionNode.setDepth(depth)
    }

    private fun setFocus(focus: PointF) {
        distortionNode.setFocus(focus)
    }

    private fun createFocusDistortionNode(resourceId: Int): FocusDistortionNode {
        val imageInfo = ImageLoader.createImage(world.context, resourceId)
        return FocusDistortionNode(imageInfo.buffer, imageInfo.width, imageInfo.height)
    }
}