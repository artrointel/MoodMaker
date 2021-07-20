package com.artrointel.moodmaker.kotesrenderengine.common

import android.renderscript.Matrix3f

class Matrix3 {
    companion object {
        val IDENTITY : Matrix3 = Matrix3()
    }

    var transformUpdated = true
        internal set
    private var mat3: Matrix3f = Matrix3f()

    fun scale(x: Float, y: Float): Matrix3 {
        mat3.scale(x, y)
        transformUpdated = true
        return this
    }

    fun rotate(angle: Float): Matrix3 {
        mat3.rotate(angle)
        transformUpdated = true
        return this
    }

    fun translate(x: Float, y: Float): Matrix3 {
        mat3.translate(x, y)
        transformUpdated = true
        return this
    }

    fun copyFrom(src: Matrix3) {
        mat3 = src.mat3
    }

    fun copyTo(dst: Matrix3) {
        dst.mat3 = mat3
    }

    internal fun raw(): FloatArray {
        var m = Matrix3f(mat3.array)
        m.transpose()
        return m.array
    }
}