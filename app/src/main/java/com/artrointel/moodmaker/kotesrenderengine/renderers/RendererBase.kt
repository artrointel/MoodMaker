package com.artrointel.moodmaker.kotesrenderengine.renderers

import com.artrointel.moodmaker.kotesrenderengine.gl.IGLObject
import kotlin.collections.ArrayList

abstract class RendererBase() {

    private var glObjects: ArrayList<IGLObject> = ArrayList()
    private var needToDraw: Boolean = true
    private var needToPrepare: Boolean = true

    abstract fun onInitializeGLObjects(): Array<IGLObject>

    internal fun initialize() {
        glObjects.clear()
        glObjects.addAll(onInitializeGLObjects())
    }

    fun invalidate(_needToDraw: Boolean = true) {
        needToDraw = _needToDraw
    }

    fun detach(glObject: IGLObject) {
        if(glObjects.contains(glObject)) {
            glObjects.remove(glObject)
        }
    }

    /**
     * called on gl thread once before onRender() call.
     */
    abstract fun onPrepare()

    /**
     * called on gl thread for every draw call
     */
    abstract fun onRender()

    /**
     * called on gl thread for every draw call before onRender()
     */
    abstract fun onGLObjectUpdated()

    /**
     * Called when the renderer object is disposed.
     */
    abstract fun onDispose()

    internal fun prepare() {
        if(needToPrepare) {
            initialize()

            // create gl objects
            for(glObj in glObjects) {
                glObj.create()
            }
            onPrepare()
            needToPrepare = false
        }
    }

    internal fun render() {
        if(!needToDraw) {
            return
        }

        onGLObjectUpdated()
        for(glObj in glObjects) {
            glObj.bind()
        }
        onRender()

        //todo needToDraw = false
    }
}