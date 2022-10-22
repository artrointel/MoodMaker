package com.artrointel.moodmaker.kotesrenderengine.gl

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.DataType
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.GLBuffer
import com.artrointel.moodmaker.kotesrenderengine.utils.Debugger
import java.nio.IntBuffer

class Attribute(_program: Program, _type: DataType, attributeName: String) : IGLObject {
    companion object {
        const val tag = "Attribute"
    }
    private val program: Program = _program

    private val name: String = attributeName
    private val type: DataType = _type

    private lateinit var vboId: IntBuffer
    private var location: Int = -1
    private lateinit var buffer: GLBuffer

    // int, float, int array, float array
    fun <T: Any> set(data: T) : Attribute {
        type.check(data)
        buffer = GLBuffer().set(data)
        return this
    }

    override fun create() {
        location = GLES30.glGetAttribLocation(program.id, name)
        if (location == -1) {
            Debugger.log(tag, "Attribute:$name doesn't exist in current program${program.id}")
            return
        }

        vboId = IntBuffer.allocate(1)
        GLES30.glGenBuffers(1, vboId)
        vboId.rewind()
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vboId.get(0))
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, 4 * buffer.size(), buffer.get(), GLES30.GL_STATIC_DRAW)

        GLES30.glVertexAttribPointer(location, type.dataLength, findAttributeType(), false, 0, 0)
        GLES30.glEnableVertexAttribArray(location)
    }

    override fun bind() {
        if (location == -1) return
        GLES30.glVertexAttribPointer(location, type.dataLength, findAttributeType(), false, 0, 0)
        GLES30.glEnableVertexAttribArray(location)
    }

    private fun findAttributeType(): Int {
        return when(type) {
            DataType.INT -> GLES30.GL_INT
            else -> GLES30.GL_FLOAT
        }
    }

    override fun dispose() {
        GLES30.glDeleteBuffers(1, vboId)
    }
}