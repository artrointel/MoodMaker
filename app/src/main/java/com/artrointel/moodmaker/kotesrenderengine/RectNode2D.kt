package com.artrointel.moodmaker.kotesrenderengine

import com.artrointel.moodmaker.kotesrenderengine.common.Matrix3
import com.artrointel.moodmaker.kotesrenderengine.renderers.RectRenderer

class RectNode2D() : Node2D() {
    protected var rectRenderer: RectRenderer = RectRenderer()

    open var transform: Matrix3 = Matrix3()
        private set

    init {
        add(rectRenderer)
    }

    override fun render() {
        if(transform.transformUpdated) {
            rectRenderer.setTransformMatrix(transform)
            transform.transformUpdated = false
        }
        super.render()
    }
}