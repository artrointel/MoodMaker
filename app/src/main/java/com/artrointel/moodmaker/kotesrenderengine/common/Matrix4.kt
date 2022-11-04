package com.artrointel.moodmaker.kotesrenderengine.common

import android.renderscript.Matrix4f

class Matrix4 {
    // TODO make quaternion in future
    companion object {
        val IDENTITY : Matrix4 = Matrix4(floatArrayOf(
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f
        ))

        fun multiply(l: Matrix4, r: Matrix4): Matrix4 {
            var ret = Matrix4()
            for(i in 0..3) {
                for(j in 0..3) {
                    var sum = 0f
                    for(k in 0..3) {
                        sum += l.array[4*k + i] * r.array[4*j + k]
                    }
                    ret.array[i + 4*j] = sum
                }
            }
            return ret
        }
    }

    constructor() {
        array = IDENTITY.array.clone()
    }

    constructor(matrix4: Matrix4) {
        array = matrix4.array.clone()
    }

    constructor(floatArray16: FloatArray) {
        array = floatArray16.clone()
    }

    var array: FloatArray = FloatArray(16)

    internal fun raw(): FloatArray {
        return array
    }

    fun setScale(scale: Vector3) {
        array[0] = scale.x
        array[5] = scale.y
        array[10] = scale.z
    }

    fun loadProperOrtho(width: Int, height: Int) {
        val mat4 = Matrix4f()
        mat4.loadOrtho(0f, width.toFloat(), 0f, height.toFloat(), -1.0f, 1.0f)
        array = mat4.array
    }
}