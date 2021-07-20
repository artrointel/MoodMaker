package com.artrointel.moodmaker.kotesrenderengine.gl

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.GLES
import java.nio.Buffer
import java.nio.IntBuffer

class Texture(_program: Program, _samplerName: String,
              _buffer: Buffer, _width: Int, _height: Int,
              useMipmap: Boolean = false) : IGLObject {
    private var program: Program = _program
    internal var id: Int = -1
        private set
    private var location: Int = -1
    private var name: String = _samplerName
    private var buffer: Buffer = _buffer
    private var width: Int = _width
    private var height: Int = _height

    private var isBufferUpdated = false

    // fun setFiltering() mipmap, min/mag filter, wrapping options

    fun set(textureBuffer: Buffer, _width: Int, _height: Int): Texture {
        buffer = textureBuffer!!
        width = _width
        height = _height
        isBufferUpdated = true
        return this
    }

    override fun create() {
        GLES.glCheckError()
        location = GLES30.glGetUniformLocation(program.id, name)
        GLES30.glUniform1i(location, 0)

        GLES.glCheckError()
        var bufferId = IntBuffer.allocate(1)
        GLES30.glGenTextures(1, bufferId)
        id = bufferId.get(0)
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, id)
        GLES.glCheckError()
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES30.GL_REPEAT);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);
        GLES.glCheckError()
        GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0,
            GLES30.GL_RGBA, width, height, 0, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, buffer)
        GLES.glCheckError()
        // mipmap if necessary
    }

    override fun bind() {
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0)
        // todo use textureSet for more textures
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, id)
        if(isBufferUpdated) {
            GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0,
                GLES30.GL_RGBA, width, height, 0, GLES30.GL_RGBA, GLES30.GL_UNSIGNED_BYTE, buffer)
            isBufferUpdated = false
        }
        GLES.glCheckError()
        // todo is option updated?
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }

}