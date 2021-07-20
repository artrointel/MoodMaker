package com.artrointel.moodmaker.kotesrenderengine
import android.content.Context
import android.opengl.GLES30

import com.artrointel.moodmaker.kotesrenderengine.utils.Assets
import com.artrointel.moodmaker.kotesrenderengine.utils.Debugger

abstract class RenderWorldBase(_context: Context) {
    private var context: Context = _context
    private var width: Int = 0
    private var height: Int = 0
    private var sizeUpdated: Boolean = true

    private var rootNode: Node2D = Node2D()

    init {
        Assets.asset = context.assets
    }

    abstract fun invalidate()

    open fun initialize() {
        KotESContext.createContext()
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    }

    fun render() {
        // todo bind default framebuffer
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)

        if(sizeUpdated) {
            var esContext = KotESContext.getCurrent()
            GLES30.glViewport(0, 0, width, height)
            esContext!!.updateScreenSize(width, height)
            sizeUpdated = false
        }

        rootNode.render()

        var err = GLES30.glGetError()
        while(err != GLES30.GL_NO_ERROR) {
            Debugger.log("World", "GLError:$err")
        }
    }

    fun updateSize(_width: Int, _height: Int) {
        width = _width
        height = _height
        sizeUpdated = true
        invalidate()
    }

    fun getRoot(): Node2D {
        return rootNode
    }
}