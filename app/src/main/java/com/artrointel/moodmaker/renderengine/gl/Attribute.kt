package com.artrointel.moodmaker.renderengine.gl

import android.opengl.GLES30
import com.artrointel.moodmaker.renderengine.gl.utils.DataType
import com.artrointel.moodmaker.renderengine.gl.utils.GLBuffer
import com.artrointel.moodmaker.renderengine.utils.Debugger
import java.nio.IntBuffer

class Attribute(_program: Program, _type: DataType, attributeName: String) : IGLObject {
    private val program: Program = _program

    private val name: String = attributeName
    private val type: DataType = _type

    private lateinit var vboId: IntBuffer
    private var location: Int = -1
    private lateinit var buffer: GLBuffer

    // int, float, int array, float array
    fun <T: Any> set(data: T) : Attribute {
        type.assertIfTypeMismatch(data)
        buffer = GLBuffer().set(data)
        return this
    }


    override fun bind() {
        location = GLES30.glGetAttribLocation(program.id, name)
        GLES30.glVertexAttribPointer(location, type.dataLength, findAttribType(), false, 0, 0)
        GLES30.glEnableVertexAttribArray(location) // enable the attribute
    }

    override fun create() {
        // Create a vbo and upload buffer data
        vboId = IntBuffer.allocate(1)
        GLES30.glGenBuffers(1, vboId)
        vboId.rewind()
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboId.get(0))
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, 4 * buffer.size(), buffer.get(), GLES30.GL_STATIC_DRAW)

        // Find location from the program by name and notify the config. of this attribute
        location = GLES30.glGetAttribLocation(program.id, name)
        GLES30.glVertexAttribPointer(location, type.dataLength, findAttribType(), false, 0, 0)
        GLES30.glEnableVertexAttribArray(location) // enable the attribute
    }

    private fun findAttribType(): Int {
        return when(type) {
            DataType.INT -> GLES30.GL_INT
            else -> GLES30.GL_FLOAT
        }
    }

    override fun dispose() {
        GLES30.glDeleteBuffers(1, vboId)
    }
}