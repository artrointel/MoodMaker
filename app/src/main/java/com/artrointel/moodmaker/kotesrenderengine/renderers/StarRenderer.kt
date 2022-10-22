package com.artrointel.moodmaker.kotesrenderengine.renderers

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4
import com.artrointel.moodmaker.kotesrenderengine.common.Mesh
import com.artrointel.moodmaker.kotesrenderengine.gl.*
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.DataType
import com.artrointel.moodmaker.kotesrenderengine.utils.Assets

class StarRenderer() : RendererBase(), IRendererProjectionListener, IRendererTransformListener {
    private lateinit var program: Program

    // Uniforms
    private var uProjMatrix: Uniform
    private var uModelMatrix: Uniform

    private var uTime: Uniform
    // TODO use UBO instead
    // Attributes
    private var aPos: Attribute
    private var aUv: Attribute
    private var attrSet: AttributeSet

    init {
        val vShader = Shader(
            Shader.TYPE.VERTEX,
            Assets.getShaderString("stars.vsh.glsl"))
        val fShader = Shader(
            Shader.TYPE.FRAGMENT,
            Assets.getShaderString("stars.fsh.glsl"))
        program = Program(vShader, fShader)

        uTime = Uniform(program, DataType.FLOAT, "uTime").set(0f)
        uProjMatrix = Uniform(program, DataType.MAT4,"projMatrix").set(Matrix4.IDENTITY.raw())
        uModelMatrix = Uniform(program, DataType.MAT4, "modelMatrix").set(Matrix4.IDENTITY.raw())

        aPos = Attribute(program, DataType.VEC3, "aPos").set(Mesh.QUAD_3D.data)
        aUv = Attribute(program, DataType.VEC2, "aUv").set(Mesh.QUAD_2D_UV_FLIP.data)
        attrSet = AttributeSet().set(aPos, aUv)
    }

    fun setTime(_timeInMs: Float) {
        uTime.set(_timeInMs)
    }

    override fun onInitializeGLObjects(): Array<IGLObject> {
        return arrayOf(program,
            uTime, uProjMatrix, uModelMatrix,
            attrSet)
    }

    override fun onPrepare() {}

    override fun onRender() {
        GLES30.glDrawArrays(Mesh.QUAD_2D.order, 0, Mesh.QUAD_2D.getDataCount())
    }

    override fun onGLObjectUpdated() {}

    override fun onDispose() {}

    override fun onProjectionUpdated(projectionMatrix: Matrix4) {
        uProjMatrix.set(projectionMatrix.raw())
    }

    override fun onTransformUpdated(transformMatrix: Matrix4) {
        uModelMatrix.set(transformMatrix.raw())
    }
}