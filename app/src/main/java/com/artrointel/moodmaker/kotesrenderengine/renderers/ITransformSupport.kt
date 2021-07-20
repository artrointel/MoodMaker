package com.artrointel.moodmaker.kotesrenderengine.renderers

import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4

internal interface ITransformSupport {
    fun setTransformMatrix(xForm: Matrix4)
}