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
            // if updated?
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0 + i)
            // todo use textureSet for more textures
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textures[i].id)
        }
    }

    override fun dispose() {}
}