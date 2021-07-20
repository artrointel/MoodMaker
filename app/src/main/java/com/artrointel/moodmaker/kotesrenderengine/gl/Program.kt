package com.artrointel.moodmaker.kotesrenderengine.gl

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.utils.Debugger
import java.nio.IntBuffer

class Program(vsh: Shader, fsh: Shader) : IGLObject  {
    private val vShader: Shader = vsh
    private val fShader: Shader = fsh
    private var isAlive: Boolean = false

    internal var id: Int = -1

    override fun create() {
        isAlive = true

        if(!vShader.isAlive())
            vShader.create()

        if(!fShader.isAlive())
            fShader.create()

        if (vShader.isAlive() && fShader.isAlive()) {
            id = GLES30.glCreateProgram()
            GLES30.glAttachShader(id, vShader.id)
            GLES30.glAttachShader(id, fShader.id)
            GLES30.glLinkProgram(id);

            var success: IntBuffer = IntBuffer.allocate(1)
            GLES30.glGetProgramiv(id, GLES30.GL_LINK_STATUS, success)
            if(success.get(0) == GLES30.GL_FALSE) {
                var err = GLES30.glGetProgramInfoLog(id)
                Debugger.assert("GL Shader Program Not exists: $err")
            }
            GLES30.glUseProgram(id)
        }
        else {
            isAlive = false
        }
    }

    override fun bind() {
        Debugger.assertIfNot(isAlive, "GL Shader Program Not exists")
        GLES30.glUseProgram(id)
    }

    override fun dispose() {
        if(isAlive) {
            GLES30.glDeleteProgram(id)
            isAlive = false
        }
    }
}