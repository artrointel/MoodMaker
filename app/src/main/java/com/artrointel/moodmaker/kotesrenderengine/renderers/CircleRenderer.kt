package com.artrointel.moodmaker.kotesrenderengine.renderers

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4
import com.artrointel.moodmaker.kotesrenderengine.common.Mesh
import com.artrointel.moodmaker.kotesrenderengine.gl.*
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.DataType
import com.artrointel.moodmaker.kotesrenderengine.utils.Assets

class CircleRenderer: RendererBase(), IRendererProjectionListener, IRendererTransformListener {
    private lateinit var program: Program

    // Uniforms
    private lateinit var uProjMatrix: Uniform
    private lateinit var uModelMatrix: Uniform

    // Attributes
    private var attrSet: AttributeSet = AttributeSet()
    private lateinit var aPos: Attribute
    private lateinit var aColor: Attribute

    override fun onInitializeGLObjects(): Array<IGLObject> {
        val vShader = Shader(
            Shader.TYPE.VERTEX,
            Assets.getShaderString("circle.vsh.glsl"))
        val fShader = Shader(
            Shader.TYPE.FRAGMENT,
            Assets.getShaderString("circle.fsh.glsl"))

        program = Program(vShader, fShader)
        uProjMatrix = Uniform(program, DataType.MAT4,"projMatrix").set(Matrix4.IDENTITY.raw())
        uModelMatrix = Uniform(program, DataType.MAT4, "modelMatrix").set(Matrix4.IDENTITY.raw())
        aPos = Attribute(program, DataType.VEC3, "aPos").set(Mesh.QUAD_3D.data)
        aColor = Attribute(program, DataType.VEC3, "aColor").set(Mesh.QUAD_3D_UV.data)

        attrSet.set(aPos, aColor)

        return arrayOf(program, uProjMatrix, uModelMatrix, attrSet)
    }

    override fun onPrepare() {}

    override fun onRender() {
        GLES30.glEnable(GLES30.GL_BLEND)
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA, GLES30.GL_ONE_MINUS_SRC_ALPHA)
        GLES30.glDrawArrays(Mesh.QUAD_2D.order, 0, Mesh.QUAD_2D.getDataCount())
        GLES30.glDisable(GLES30.GL_BLEND)
    }

    override fun onDispose() {}

    override fun onProjectionUpdated(_matrix: Matrix4) {
        uProjMatrix.set(_matrix.raw())
    }

    override fun onTransformUpdated(_matrix: Matrix4) {
        uModelMatrix.set(_matrix.raw())
    }
}