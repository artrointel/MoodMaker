package com.artrointel.moodmaker.kotesrenderengine.gl

import android.opengl.GLES30
import java.nio.IntBuffer

class AttributeSet : IGLObject {
    private var vaoId: Int = -1
    private var attributes: ArrayList<Attribute> = ArrayList()

    fun set(vararg _attrs: Attribute) {
        attributes.clear()
        for(attr in _attrs) {
            attributes.add(attr)
        }
    }

    override fun create() {
        // Create a vao
        var _vaoId = IntBuffer.allocate(1)
        GLES30.glGenVertexArrays(1, _vaoId)
        this.vaoId = _vaoId.get(0)
        GLES30.glBindVertexArray(this.vaoId)

        for(attribute in attributes) {
            attribute.create()
        }
    }

    override fun bind() {
        GLES30.glBindVertexArray(vaoId)
    }

    override fun dispose() {
        TODO("Not yet implemented")
    }
}