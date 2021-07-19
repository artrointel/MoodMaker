package com.artrointel.moodmaker.renderengine.gl

import com.artrointel.moodmaker.renderengine.utils.Debugger
import android.opengl.GLES20
import android.opengl.GLES30
import java.nio.IntBuffer


class Shader(_type: TYPE, _shaderString: String) : IGLObject {
    enum class TYPE {
        VERTEX,
        FRAGMENT,
    }

    private val shaderString: String = _shaderString
    private val type: TYPE = _type

    internal var id: Int = -1
    private var alive = false

    override fun bind() {}

    override fun create() {
        alive = true

        // make shader object
        if(type == TYPE.VERTEX) {
            id = GLES30.glCreateShader(GLES30.GL_VERTEX_SHADER);
        }
        else if (type == TYPE.FRAGMENT) {
            id = GLES30.glCreateShader(GLES30.GL_FRAGMENT_SHADER);
        }
        GLES30.glShaderSource(id, shaderString)
        GLES30.glCompileShader(id)

        var success : IntBuffer = IntBuffer.allocate(1)
        GLES30.glGetShaderiv(id, GLES20.GL_COMPILE_STATUS, success)
        if(success.get(0) == GLES30.GL_FALSE) {
            var err = GLES30.glGetShaderInfoLog(id)
            Debugger.assert("GL Shader not compiled: $err")
        }
    }

    internal fun isAlive(): Boolean {
        return alive;
    }

    override fun dispose() {
        if(alive) {
            GLES30.glDeleteShader(id)
            alive = false
        }
    }
}