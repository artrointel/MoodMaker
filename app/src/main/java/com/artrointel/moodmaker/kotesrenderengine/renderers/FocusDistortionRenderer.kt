package com.artrointel.moodmaker.kotesrenderengine.renderers

import android.graphics.PointF
import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4
import com.artrointel.moodmaker.kotesrenderengine.common.Mesh
import com.artrointel.moodmaker.kotesrenderengine.gl.*
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.DataType
import com.artrointel.moodmaker.kotesrenderengine.utils.Assets
import java.nio.Buffer

class FocusDistortionRenderer(bufferFromImage: Buffer, width: Int, height: Int)
    : RendererBase(), IRendererProjectionListener, IRendererTransformListener {
    private var buffer: Buffer = bufferFromImage
    private var width: Int = width
    private var height: Int = height

    private lateinit var program: Program

    // Uniforms
    private lateinit var uProjMatrix: Uniform
    private lateinit var uModelMatrix: Uniform
    private var uTexture: Texture? = null

    // Attributes
    private var attrSet: AttributeSet = AttributeSet()
    private lateinit var uResolution: Uniform
    private lateinit var uRadius: Uniform
    private lateinit var uDepth: Uniform
    private lateinit var uFocus: Uniform
    private lateinit var aPos: Attribute
    private lateinit var aUv: Attribute

    // Data
    private var radius: Float = 1.0f
    private var depth: Float = 1.0f
    private var focus: PointF = PointF(0.5f, 0.5f)
    private var displayWidth: Int = 1
    private var displayHeight: Int = 1

    fun set(bufferFromImage: Buffer, _width: Int, _height: Int) {
        buffer = bufferFromImage
        width = _width
        height = _height
        uTexture?.set(buffer, width, height)
    }

    fun setResolution(width: Int, height: Int) {
        displayWidth = width;
        displayHeight = height;
    }

    fun setRadius(radius: Float) {
        this.radius = radius
    }

    fun setDepth(depth: Float) {
        this.depth = depth;
    }

    fun setFocus(focus: PointF) {
        this.focus = focus;
    }

    override fun onInitializeGLObjects(): Array<IGLObject> {
        val vShader = Shader(
            Shader.TYPE.VERTEX,
            Assets.getShaderString("focusDistortion.vsh.glsl"))
        val fShader = Shader(
            Shader.TYPE.FRAGMENT,
            Assets.getShaderString("focusDistortion.fsh.glsl"))
        program = Program(vShader, fShader)

        uProjMatrix = Uniform(program, DataType.MAT4,"projMatrix").set(Matrix4.IDENTITY.raw())
        uModelMatrix = Uniform(program, DataType.MAT4, "modelMatrix").set(Matrix4.IDENTITY.raw())
        uTexture = Texture(program, "tex", buffer, width, height)

        uResolution = Uniform(program, DataType.VEC2, "uResolution", 1).set(
            floatArrayOf(1.0f, 1.0f))
        uRadius = Uniform(program, DataType.FLOAT, "uRadius", 1).set(1.0f)
        uDepth = Uniform(program, DataType.FLOAT, "uDepth", 1).set(1.0f)
        uFocus = Uniform(program, DataType.VEC2, "uFocus", 1).set(
            floatArrayOf(0.5f, 0.5f))
        aPos = Attribute(program, DataType.VEC3, "aPos").set(Mesh.QUAD_3D.data)
        aUv = Attribute(program, DataType.VEC2, "aUv").set(Mesh.QUAD_2D_UV.data)

        attrSet.set(aPos, aUv)

        return arrayOf(program, uProjMatrix, uModelMatrix, uTexture!!, uResolution, uRadius, uDepth, uFocus, attrSet)
    }

    override fun onPrepare() {}

    override fun onRender() {
        GLES30.glDrawArrays(Mesh.QUAD_2D.order, 0, Mesh.QUAD_2D.getDataCount())
    }

    override fun onGLObjectUpdated() {
        uResolution.set(floatArrayOf(displayWidth.toFloat(), displayHeight.toFloat()))
        uRadius.set(radius)
        uDepth.set(depth)
        uFocus.set(floatArrayOf(focus.x, focus.y))
    }

    override fun onDispose() {}

    override fun onProjectionUpdated(projectionMatrix: Matrix4) {
        uProjMatrix.set(projectionMatrix.raw())
    }

    override fun onTransformUpdated(transformMatrix: Matrix4) {
        uModelMatrix.set(transformMatrix.raw())
    }

}