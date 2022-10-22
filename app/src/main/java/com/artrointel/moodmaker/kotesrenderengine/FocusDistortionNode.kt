package com.artrointel.moodmaker.kotesrenderengine

import android.graphics.PointF
import com.artrointel.moodmaker.kotesrenderengine.renderers.FocusDistortionRenderer
import java.nio.Buffer

class FocusDistortionNode(bufferFromImage: Buffer, width: Int, height: Int) : Node3D() {
    private var renderer = FocusDistortionRenderer(bufferFromImage, width, height)

    init {
        add(renderer)
    }

    fun setResolution(width: Int, height: Int) {
        renderer.setResolution(width, height)
    }

    fun setRadius(radius: Float) {
        renderer.setRadius(radius)
    }

    fun setDepth(depth: Float) {
        renderer.setDepth(depth)
    }

    fun setFocus(focus: PointF) {
        renderer.setFocus(focus)
    }

}