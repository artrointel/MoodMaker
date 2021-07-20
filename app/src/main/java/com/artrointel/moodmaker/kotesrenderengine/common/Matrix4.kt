package com.artrointel.moodmaker.kotesrenderengine.common

import android.renderscript.Matrix4f

class Matrix4 {
    // TODO make quaternion in future
    companion object {
        val IDENTITY : Matrix4 = Matrix4()
    }

    var transformUpdated = true
        internal set
    private var mat4: Matrix4f = Matrix4f()

    internal fun raw(): FloatArray {
        return mat4.array
    }

    fun translate(x: Float, y: Float, z: Float) {
        mat4.translate(x, y, z)
    }

    fun rotate(angle: Float, axisX: Float, axisY: Float, axisZ: Float) {
        mat4.rotate(angle, axisX, axisY, axisZ)
    }

    fun scale(x: Float, y: Float, z: Float) {
        mat4.scale(x, y, z)
    }

    fun loadProperOrtho(width: Int, height: Int) {
        mat4.loadOrtho(0f, width.toFloat(), 0f, height.toFloat(), -1.0f, 1.0f)
    }
}