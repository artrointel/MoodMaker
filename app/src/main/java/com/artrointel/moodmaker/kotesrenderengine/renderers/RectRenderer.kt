package com.artrointel.moodmaker.kotesrenderengine.renderers

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.common.Matrix3
import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4
import com.artrointel.moodmaker.kotesrenderengine.common.Mesh
import com.artrointel.moodmaker.kotesrenderengine.gl.*
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.DataType
import com.artrointel.moodmaker.kotesrenderengine.utils.Assets

class RectRenderer : RendererBase(), ITransformSupport {
    private var program: Program
    // Uniforms
    private var uModelMatrix: Uniform

    // Attributes
    private var attrSet: AttributeSet = AttributeSet()
    private var aPos: Attribute
    private var aColor: Attribute

    init {
        val vShader = Shader(
            Shader.TYPE.VERTEX,
            Assets.getShaderString("base2D.vsh.glsl"))
        val fShader = Shader(
            Shader.TYPE.FRAGMENT,
            Assets.getShaderString("base2D.fsh.glsl"))

        program = Program(vShader, fShader)
        uModelMatrix = Uniform(program, DataType.MAT4, "modelMatrix").set(Matrix4.IDENTITY.raw())
        aPos = Attribute(program, DataType.VEC3, "aPos").set(Mesh.QUAD_3D.data)
        aColor = Attribute(program, DataType.VEC3, "aColor").set(Mesh.QUAD_3D_UV.data)

        attrSet.set(aPos, aColor)

        attachGlObjects(program, uModelMatrix, attrSet)
    }

    override fun setTransformMatrix(xForm: Matrix4) {
        uModelMatrix.set(xForm.raw())
    }

    override fun onPrepare() {}

    override fun onRender() {
        GLES30.glDrawArrays(Mesh.QUAD_3D.order, 0, Mesh.QUAD_2D.getDataCount())
    }

    override fun onDispose() {}
}