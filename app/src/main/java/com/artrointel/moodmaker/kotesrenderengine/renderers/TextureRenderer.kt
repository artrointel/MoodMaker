package com.artrointel.moodmaker.kotesrenderengine.renderers

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4
import com.artrointel.moodmaker.kotesrenderengine.common.Mesh
import com.artrointel.moodmaker.kotesrenderengine.gl.*
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.DataType
import com.artrointel.moodmaker.kotesrenderengine.utils.Assets
import java.nio.Buffer

class TextureRenderer(bufferFromImage: Buffer, _width: Int, _height: Int)
    : RendererBase(), IRendererProjectionListener, IRendererTransformListener {
    private var buffer: Buffer = bufferFromImage
    private var width: Int = _width
    private var height: Int = _height

    private lateinit var program: Program

    // Uniforms
    private lateinit var uProjMatrix: Uniform
    private lateinit var uModelMatrix: Uniform
    private var uTexture: Texture? = null

    // Attributes
    private var attrSet: AttributeSet = AttributeSet()
    private lateinit var aPos: Attribute
    private lateinit var aUv: Attribute

    fun set(bufferFromImage: Buffer, _width: Int, _height: Int) {
        buffer = bufferFromImage
        width = _width
        height = _height
        uTexture?.set(buffer, width, height)
    }

    override fun onInitializeGLObjects(): Array<IGLObject> {
        val vShader = Shader(
            Shader.TYPE.VERTEX,
            Assets.getShaderString("texturing.vsh.glsl"))
        val fShader = Shader(
            Shader.TYPE.FRAGMENT,
            Assets.getShaderString("texturing.fsh.glsl"))
        program = Program(vShader, fShader)

        uProjMatrix = Uniform(program, DataType.MAT4,"projMatrix").set(Matrix4.IDENTITY.raw())
        uModelMatrix = Uniform(program, DataType.MAT4, "modelMatrix").set(Matrix4.IDENTITY.raw())
        uTexture = Texture(program, "tex", buffer, width, height)

        aPos = Attribute(program, DataType.VEC3, "aPos").set(Mesh.QUAD_3D.data)
        aUv = Attribute(program, DataType.VEC2, "aUv").set(Mesh.QUAD_2D_UV_FLIP.data)

        attrSet.set(aPos, aUv)

        return arrayOf(program, uProjMatrix, uModelMatrix, uTexture!!, attrSet)
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