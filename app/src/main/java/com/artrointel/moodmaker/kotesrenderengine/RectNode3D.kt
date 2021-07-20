package com.artrointel.moodmaker.kotesrenderengine

import com.artrointel.moodmaker.kotesrenderengine.renderers.RectRenderer

class RectNode3D() : Node3D() {
    private var rectRenderer: RectRenderer = RectRenderer()

    init {
        add(rectRenderer)
    }
}