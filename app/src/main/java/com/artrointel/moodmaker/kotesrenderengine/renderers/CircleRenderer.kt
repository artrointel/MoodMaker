package com.artrointel.moodmaker.kotesrenderengine.renderers

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.common.Matrix3
import com.artrointel.moodmaker.kotesrenderengine.common.Mesh
import com.artrointel.moodmaker.kotesrenderengine.gl.*
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.DataType
import com.artrointel.moodmaker.kotesrenderengine.utils.Assets

class CircleRenderer: RendererBase(), ITransformSupport {
    private var program: Program
    // Uniforms
    private var uModelMatrix: Uniform

    // Attributes
    private var attrSet: AttributeSet = AttributeSet()
    private var aPos: Attribute
    private var aColor: Attribute

    init {
        var vShader = Shader(
            Shader.TYPE.VERTEX,
            Assets.getShaderString("circle2D.vsh.glsl"))
        var fShader = Shader(
            Shader.TYPE.FRAGMENT,
            Assets.getShaderString("circle2D.fsh.glsl"))

        program = Program(vShader, fShader)
        uModelMatrix = Uniform(program, DataType.MAT3, "modelMatrix").set(Matrix3.IDENTITY.raw())
        aPos = Attribute(program, DataType.VEC3, "aPos").set(Mesh.QUAD_3D.data)
        aColor = Attribute(program, DataType.VEC3, "aColor").set(Mesh.QUAD_3D_UV.data)

        attrSet.set(aPos, aColor)

        attachGlObjects(program, uModelMatrix, attrSet)
    }

    override fun setTransformMatrix(xForm: Matrix3) {
        uModelMatrix.set(xForm.raw())
    }

    override fun onPrepare() {}

    override fun onRender() {
        GLES30.glEnable(GLES30.GL_BLEND)
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA)
        GLES30.glDrawArrays(Mesh.QUAD_3D.order, 0, Mesh.QUAD_2D.getDataCount())
        GLES30.glDisable(GLES30.GL_BLEND)
    }

    override fun onDispose() {
        TODO("Not yet implemented")
    }
}