package com.artrointel.moodmaker.kotesrenderengine

import com.artrointel.moodmaker.kotesrenderengine.renderers.TextureRenderer
import java.nio.Buffer

class ImageNode(bufferFromImage: Buffer, width: Int, height: Int) : Node3D() {
    private var renderer = TextureRenderer(bufferFromImage, width, height)

    init {
        add(renderer)
    }

    fun setAlpha(alpha: Float) {
        renderer.setAlpha(alpha)
    }
}