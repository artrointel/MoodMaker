package com.artrointel.moodmaker.kotesrenderengine.renderers

import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4

internal interface IRendererProjectionListener {
    fun onProjectionUpdated(projectionMatrix: Matrix4)
}