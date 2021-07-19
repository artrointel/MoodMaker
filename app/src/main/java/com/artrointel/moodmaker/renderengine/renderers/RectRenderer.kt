package com.artrointel.moodmaker.renderengine.renderers

import android.opengl.GLES30
import com.artrointel.moodmaker.renderengine.Mesh
import com.artrointel.moodmaker.renderengine.gl.*
import com.artrointel.moodmaker.renderengine.gl.utils.DataType
import com.artrointel.moodmaker.renderengine.gl.utils.GLBuffer
import com.artrointel.moodmaker.renderengine.utils.Assets

class RectRenderer : RendererBase() {
    private var program: Program

    // Attributes
    private var attrSet: AttributeSet = AttributeSet()
    private var aPos: Attribute
    private var aColor: Attribute

    init {
        var vShader = Shader(
            Shader.TYPE.VERTEX,
            Assets.getShaderString("base2D.vsh.glsl"))
        var fShader = Shader(
            Shader.TYPE.FRAGMENT,
            Assets.getShaderString("base2D.fsh.glsl"))

        program = Program(vShader, fShader)

        aPos = Attribute(program, DataType.VEC2, "aPos")
        aPos.set(Mesh.QUAD_2D.data)
        aColor = Attribute(program, DataType.VEC2, "aColor")
        aColor.set(Mesh.QUAD_2D.data)

        attrSet.set(aPos, aColor)

        attachGlObjects(program, attrSet)
    }

    override fun onPrepare() {
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
    }

    override fun onRender() {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        GLES30.glDrawArrays(Mesh.QUAD_2D.order, 0, Mesh.QUAD_2D.getDataCount())
    }

    override fun onDispose() {
        TODO("Not yet implemented")
    }

}