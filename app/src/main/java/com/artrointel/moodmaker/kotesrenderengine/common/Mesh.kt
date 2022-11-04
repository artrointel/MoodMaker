package com.artrointel.moodmaker.kotesrenderengine.common

import android.opengl.GLES30

class Mesh(val data: FloatArray, val dimension: Int, val order: Int) {
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

        val QUAD_2D_UV_FLIP = Mesh(floatArrayOf(
            0.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
        ), 2, GLES30.GL_TRIANGLE_STRIP)

        val QUAD_3D = Mesh(floatArrayOf(
            -0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f
        ), 3, GLES30.GL_TRIANGLE_STRIP)

        val QUAD_3D_UV = Mesh(floatArrayOf(
            0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 1.0f, 0.0f
        ), 3, GLES30.GL_TRIANGLE_STRIP)
    }

    fun getDataCount() : Int {
        return data.size / dimension
    }
}