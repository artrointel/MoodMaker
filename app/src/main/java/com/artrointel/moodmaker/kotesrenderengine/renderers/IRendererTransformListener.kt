package com.artrointel.moodmaker.kotesrenderengine.renderers

import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4

internal interface IRendererTransformListener {
    fun onTransformUpdated(transformMatrix: Matrix4)
}