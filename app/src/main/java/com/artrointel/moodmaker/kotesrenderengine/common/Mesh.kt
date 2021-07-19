package com.artrointel.moodmaker.kotesrenderengine.common

import android.opengl.GLES30

class Mesh(_vertices: FloatArray, _dimension: Int, _order: Int) {
    companion object {

        val QUAD_2D = Mesh(floatArrayOf(
            -1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, -1.0f,
            1.0f, 1.0f
        ), 2, GLES30.GL_TRIANGLE_STRIP)

        val QUAD_2D_UV = Mesh(floatArrayOf(
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f
        ), 2, GLES30.GL_TRIANGLE_STRIP)

        val QUAD_3D = Mesh(floatArrayOf(
            -1.0f, -1.0f, 0.0f,
            -1.0f, 1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            1.0f, 1.0f, 0.0f
        ), 3, GLES30.GL_TRIANGLE_STRIP)

        val QUAD_3D_UV = Mesh(floatArrayOf(
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f
        ), 3, GLES30.GL_TRIANGLE_STRIP)
    }

    val data: FloatArray = _vertices
    val dimension: Int = _dimension
    val order: Int = _order

    fun getDataCount() : Int {
        return data.size / dimension
    }
}