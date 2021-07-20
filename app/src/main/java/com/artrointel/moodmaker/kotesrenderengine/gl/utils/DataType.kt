package com.artrointel.moodmaker.kotesrenderengine.gl.utils

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.utils.Debugger

enum class DataType(val dataLength: Int, val glConstantType: Int) {
    INT(1, GLES30.GL_INT),
    FLOAT(1, GLES30.GL_FLOAT),
    VEC2(2, GLES30.GL_FLOAT_VEC2),
    VEC3(3, GLES30.GL_FLOAT_VEC3),
    VEC4(4, GLES30.GL_FLOAT_VEC4),
    MAT3(9, GLES30.GL_FLOAT_MAT3),
    MAT4(16, GLES30.GL_FLOAT_MAT4);

    fun <T: Any> assertIfTypeMismatch(data: T) {
        var errString = "Mismatched Type(${this.toString()}) and actual data($data)"
        when(data) {
            is Int -> {
                Debugger.assertIfNot(this == INT, errString)
            }
            is IntArray -> {
                Debugger.assertIfNot(this == INT, errString)
            }
            is Float -> {
                Debugger.assertIfNot(this == FLOAT, errString)
            }
            is FloatArray -> {
                Debugger.assertIf(this == INT, errString)
            }
            else -> {
                Debugger.assert(errString)
            }
        }
    }
}