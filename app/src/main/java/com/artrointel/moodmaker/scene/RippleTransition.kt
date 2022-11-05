package com.artrointel.moodmaker.scene

import com.artrointel.moodmaker.R
import com.artrointel.moodmaker.kotesrenderengine.Node3D
import com.artrointel.moodmaker.kotesrenderengine.RenderWorldBase
import com.artrointel.moodmaker.kotesrenderengine.common.Vector3
import com.artrointel.moodmaker.kotesrenderengine.renderers.PixelateRenderer
import com.artrointel.moodmaker.kotesrenderengine.renderers.RippleTextureRenderer
import com.artrointel.moodmaker.utils.ImageLoader

class RippleTransition (world: RenderWorldBase) : DemoTransition(world) {
    var node = Node3D()
    lateinit var renderer : RippleTextureRenderer

    override fun onInitialize() {
        val displayWidth = world.getDisplaySize().x.toFloat()
        val displayHeight = world.getDisplaySize().y.toFloat()
        node.transform.position = Vector3(displayWidth*0.5f, displayHeight*0.5f, 0.0f)
        node.transform.scale = Vector3(displayWidth, displayHeight, 0.0f)

        val imageInfo = ImageLoader.createImage(world.context, R.drawable.background_image)
        renderer = RippleTextureRenderer(imageInfo.buffer, imageInfo.width, imageInfo.height)
        renderer.setResolution(world.getDisplaySize().x, world.getDisplaySize().y)
        node.add(renderer)
        world.getRoot().appendChild(node)
    }

    override fun setTime(time: Float) {
        super.setTime(time)
        renderer.setProgress(time)
    }
}