package com.artrointel.moodmaker.renderengine

import com.artrointel.moodmaker.renderengine.renderers.RectRenderer

class RectNode2D() : Node2D() {
    protected var rectRenderer: RectRenderer = RectRenderer()

    init {
        add(rectRenderer)
    }
    override fun setSize(_width: Float, _height: Float) {
        super.setSize(_width, _height)
    }
}