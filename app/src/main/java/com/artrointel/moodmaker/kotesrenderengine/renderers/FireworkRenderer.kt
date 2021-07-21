package com.artrointel.moodmaker.kotesrenderengine.renderers

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4
import com.artrointel.moodmaker.kotesrenderengine.common.Mesh
import com.artrointel.moodmaker.kotesrenderengine.gl.*
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.DataType
import com.artrointel.moodmaker.kotesrenderengine.utils.Assets

class FireworkRenderer() : RendererBase(), IRendererProjectionListener, IRendererTransformListener {
    private lateinit var program: Program

    // Uniforms
    private var uProjMatrix: Uniform
    private var uModelMatrix: Uniform

    private var uTime: Uniform
    // TODO use UBO instead
    private var uSpeedFactor: Uniform
    private var uMinCount: Uniform
    private var uMaxCount: Uniform
    private var uMinParticles: Uniform
    private var uMaxParticles: Uniform
    private var uMinBrightness: Uniform
    private var uMaxBrightness: Uniform

    // Attributes
    private var aPos: Attribute
    private var aUv: Attribute
    private var attrSet: AttributeSet

    init {
        val vShader = Shader(
            Shader.TYPE.VERTEX,
            Assets.getShaderString("fireworks.vsh.glsl"))
        val fShader = Shader(
            Shader.TYPE.FRAGMENT,
            Assets.getShaderString("fireworks.fsh.glsl"))
        program = Program(vShader, fShader)

        uTime = Uniform(program, DataType.FLOAT, "uTime").set(0f)
        uSpeedFactor = Uniform(program, DataType.FLOAT, "uSpeedFactor").set(1f)
        uMinCount = Uniform(program, DataType.FLOAT, "uMinCount").set(3f)
        uMaxCount = Uniform(program, DataType.FLOAT, "uMaxCount").set(5f)
        uMinParticles = Uniform(program, DataType.FLOAT, "uMinParticles").set(15f)
        uMaxParticles = Uniform(program, DataType.FLOAT, "uMaxParticles").set(30f)
        uMinBrightness = Uniform(program, DataType.FLOAT, "uMinBrightness").set(0.0004f)
        uMaxBrightness = Uniform(program, DataType.FLOAT, "uMaxBrightness").set(0.0016f)
        uProjMatrix = Uniform(program, DataType.MAT4,"projMatrix").set(Matrix4.IDENTITY.raw())
        uModelMatrix = Uniform(program, DataType.MAT4, "modelMatrix").set(Matrix4.IDENTITY.raw())

        aPos = Attribute(program, DataType.VEC3, "aPos").set(Mesh.QUAD_3D.data)
        aUv = Attribute(program, DataType.VEC2, "aUv").set(Mesh.QUAD_2D_UV_FLIP.data)
        attrSet = AttributeSet().set(aPos, aUv)
    }

    fun setTime(_timeInMs: Float) {
        uTime.set(_timeInMs)
    }

    fun setConfigs(_speed: Float = 1f,
                   _minCount: Float, _maxCount: Float,
                   _minParticles: Float, _maxParticles: Float,
                   _minBrightness: Float, _maxBrightness: Float) {
        uSpeedFactor.set(_speed)
        uMinCount.set(_minCount)
        uMaxCount.set(_maxCount)
        uMinParticles.set(_minParticles)
        uMaxParticles.set(_maxParticles)
        uMinBrightness.set(_minBrightness)
        uMaxBrightness.set(_maxBrightness)
    }

    override fun onInitializeGLObjects(): Array<IGLObject> {
        return arrayOf(program,
            uTime, uSpeedFactor,
            uMinCount, uMaxCount,
            uMinParticles, uMaxParticles,
            uMinBrightness, uMaxBrightness,
            uProjMatrix, uModelMatrix,
            attrSet)
    }

    override fun onPrepare() {}

    override fun onRender() {
        GLES30.glDrawArrays(Mesh.QUAD_2D.order, 0, Mesh.QUAD_2D.getDataCount())
    }

    override fun onDispose() {}

    override fun onProjectionUpdated(projectionMatrix: Matrix4) {
        uProjMatrix.set(projectionMatrix.raw())
    }

    override fun onTransformUpdated(transformMatrix: Matrix4) {
        uModelMatrix.set(transformMatrix.raw())
    }
}