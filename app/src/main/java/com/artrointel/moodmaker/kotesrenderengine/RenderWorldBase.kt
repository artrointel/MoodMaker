package com.artrointel.moodmaker.kotesrenderengine
import android.content.Context
import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4

import com.artrointel.moodmaker.kotesrenderengine.utils.Assets
import com.artrointel.moodmaker.kotesrenderengine.utils.Debugger

abstract class RenderWorldBase(_context: Context) {
    companion object {
        private var worldMap = HashMap<Long, RenderWorldBase>()

        internal fun put(world: RenderWorldBase) {
            Debugger.assertIf(
                worldMap[Thread.currentThread().id] != null,
                "Already exists the world !!")
            worldMap[Thread.currentThread().id] = world
        }

        internal fun get(): RenderWorldBase {
            return worldMap[Thread.currentThread().id]!!
        }
    }

    var context: Context = _context
        private set
    private var width: Int = 0
    private var height: Int = 0
    var projectionMatrix: Matrix4 = Matrix4()
        private set

    private var sizeUpdated: Boolean = true

    private var rootNode: Node3D = Node3D()

    init {
        Assets.asset = context.assets
    }

    abstract fun invalidate()

    open fun initialize() {
        put(this)
        GLES30.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    }

    fun render() {
        // todo bind default framebuffer
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)

        if(sizeUpdated) {
            GLES30.glViewport(0, 0, width, height)
            sizeUpdated = false
        }

        rootNode.render()

        projectionMatrix.transformUpdated = false
    }

    fun updateSize(_width: Int, _height: Int) {
        width = _width
        height = _height
        sizeUpdated = true
        projectionMatrix.loadProperOrtho(width, height)
        render()
    }

    fun getRoot(): Node3D {
        return rootNode
    }
}