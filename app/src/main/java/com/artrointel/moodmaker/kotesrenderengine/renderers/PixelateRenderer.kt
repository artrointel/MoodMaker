package com.artrointel.moodmaker.kotesrenderengine.renderers

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4
import com.artrointel.moodmaker.kotesrenderengine.common.Mesh
import com.artrointel.moodmaker.kotesrenderengine.gl.*
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.DataType
import com.artrointel.moodmaker.kotesrenderengine.utils.Assets
import java.nio.Buffer

class PixelateRenderer(bufferFromImage: Buffer, width: Int, height: Int)
    : RendererBase(), IRendererProjectionListener, IRendererTransformListener {
    private var buffer: Buffer = bufferFromImage
    private var width: Int = width
    private var height: Int = height

    private lateinit var program: Program

    // Uniforms
    private lateinit var uProjMatrix: Uniform
    private lateinit var uModelMatrix: Uniform
    private var textureSet: TextureSet = TextureSet()

    // Attributes
    private lateinit var uResolution: Uniform
    private var attrSet: AttributeSet = AttributeSet()
    private lateinit var uAlpha: Uniform
    private lateinit var uProgress: Uniform
    private lateinit var aPos: Attribute
    private lateinit var aUv: Attribute

    // Data
    private var alpha: Float = 1.0f
    private var progress: Float = 1.0f
    private var displayWidth: Int = 1
    private var displayHeight: Int = 1

    fun setAlpha(alpha: Float) {
        this.alpha = alpha
    }

    fun setProgress(progress: Float) {
        this.progress = progress
    }

    fun setResolution(width: Int, height: Int) {
        displayWidth = width;
        displayHeight = height;
    }

    override fun onInitializeGLObjects(): Array<IGLObject> {
        val vShader = Shader(
            Shader.TYPE.VERTEX,
            Assets.getShaderString("transition_pixelate.vsh.glsl"))
        val fShader = Shader(
            Shader.TYPE.FRAGMENT,
            Assets.getShaderString("transition_pixelate.fsh.glsl"))
        program = Program(vShader, fShader)

        uProjMatrix = Uniform(program, DataType.MAT4,"projMatrix").set(Matrix4.IDENTITY.raw())
        uModelMatrix = Uniform(program, DataType.MAT4, "modelMatrix").set(Matrix4.IDENTITY.raw())
        val uTexture = Texture(program, "tex", buffer, width, height)
        textureSet.add(uTexture)

        uResolution = Uniform(program, DataType.VEC2, "uResolution", 1).set(
            floatArrayOf(1.0f, 1.0f))
        uAlpha = Uniform(program, DataType.FLOAT, "uAlpha", 1).set(1.0f)
        uProgress = Uniform(program, DataType.FLOAT, "uProgress", 1).set(1.0f)
        aPos = Attribute(program, DataType.VEC3, "aPos").set(Mesh.QUAD_3D.data)
        aUv = Attribute(program, DataType.VEC2, "aUv").set(Mesh.QUAD_2D_UV_FLIP.data)

        attrSet.set(aPos, aUv)

        return arrayOf(program, uProjMatrix, uModelMatrix, textureSet, uAlpha, uProgress, uResolution, attrSet)
    }

    override fun onPrepare() {}

    override fun onRender() {
        GLES30.glDrawArrays(Mesh.QUAD_2D.order, 0, Mesh.QUAD_2D.getDataCount())
    }

    override fun onGLObjectUpdated() {
        uAlpha.set(alpha)
        uProgress.set(progress)
        uResolution.set(floatArrayOf(displayWidth.toFloat(), displayHeight.toFloat()))
    }

    override fun onDispose() {}

    override fun onProjectionUpdated(projectionMatrix: Matrix4) {
        uProjMatrix.set(projectionMatrix.raw())
    }

    override fun onTransformUpdated(transformMatrix: Matrix4) {
        uModelMatrix.set(transformMatrix.raw())
    }

}