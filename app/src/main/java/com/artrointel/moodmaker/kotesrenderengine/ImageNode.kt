package com.artrointel.moodmaker.kotesrenderengine

import com.artrointel.moodmaker.kotesrenderengine.renderers.TextureRenderer
import java.nio.Buffer

class ImageNode(bufferFromImage: Buffer, _width: Int, _height: Int) : Node3D() {
    private var renderer = TextureRenderer(bufferFromImage, _width, _height)

    init {
        add(renderer)
    }
}