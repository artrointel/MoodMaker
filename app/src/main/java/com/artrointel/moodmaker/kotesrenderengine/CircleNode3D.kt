package com.artrointel.moodmaker.kotesrenderengine

import com.artrointel.moodmaker.kotesrenderengine.renderers.CircleRenderer

class CircleNode3D() : Node3D() {
    private var circleRenderer: CircleRenderer = CircleRenderer()

    init {
        add(circleRenderer)
    }
}