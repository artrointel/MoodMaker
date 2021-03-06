package com.artrointel.moodmaker.kotesrenderengine

import com.artrointel.moodmaker.kotesrenderengine.renderers.RectRenderer

class RectNode2D() : Node2D() {
    protected var rectRenderer: RectRenderer = RectRenderer()

    init {
        add(rectRenderer)
    }
}