package com.artrointel.moodmaker.kotesrenderengine

import com.artrointel.moodmaker.kotesrenderengine.renderers.CircleRenderer

class CircleNode2D() : Node2D() {
    protected var circleRenderer: CircleRenderer = CircleRenderer()

    init {
        add(circleRenderer)
    }
}