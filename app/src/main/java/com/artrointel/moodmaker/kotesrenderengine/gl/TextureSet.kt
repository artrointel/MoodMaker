package com.artrointel.moodmaker.kotesrenderengine.gl

import android.opengl.GLES30

class TextureSet: IGLObject {
    var textures: ArrayList<Texture> = ArrayList()

    fun set(vararg _textures: Texture) {
        textures.clear()
        textures.addAll(_textures)
    }

    fun add(texture: Texture) {
        textures.add(texture)
    }

    fun remove(texture: Texture) {
        textures.remove(texture)
    }

    override fun create() {
        for (texture in textures) {
            texture.create()
        }
    }

    override fun bind() {
        for(i in textures.indices) {
            GLES30.glUniform1i(textures[0].location, i)
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0 + i)
            textures[i].bind()
            // todo if texture updated texImage2D
        }
    }

    override fun dispose() {}
}