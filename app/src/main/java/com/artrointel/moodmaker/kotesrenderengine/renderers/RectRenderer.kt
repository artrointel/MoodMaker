package com.artrointel.moodmaker.kotesrenderengine.renderers

import android.opengl.GLES30
import android.renderscript.Matrix3f
import com.artrointel.moodmaker.kotesrenderengine.common.Mesh
import com.artrointel.moodmaker.kotesrenderengine.gl.*
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.DataType
import com.artrointel.moodmaker.kotesrenderengine.utils.Assets

class RectRenderer : RendererBase() {
    private var program: Program
    // Uniforms
    var uModelMatrix: Uniform

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
        uModelMatrix = Uniform(program, DataType.MAT3, "modelMatrix").set(Matrix3f().array)
        aPos = Attribute(program, DataType.VEC3, "aPos").set(Mesh.QUAD_3D.data)
        aColor = Attribute(program, DataType.VEC3, "aColor").set(Mesh.QUAD_3D.data)

        attrSet.set(aPos, aColor)

        attachGlObjects(program, uModelMatrix, attrSet)
    }

    override fun onPrepare() {
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f)
    }

    override fun onRender() {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        GLES30.glDrawArrays(Mesh.QUAD_3D.order, 0, Mesh.QUAD_2D.getDataCount())
    }

    override fun onDispose() {
        TODO("Not yet implemented")
    }

}