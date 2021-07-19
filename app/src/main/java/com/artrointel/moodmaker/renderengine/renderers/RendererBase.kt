package com.artrointel.moodmaker.renderengine.renderers

import android.opengl.GLES30
import com.artrointel.moodmaker.renderengine.gl.IGLObject
import java.util.*
import kotlin.collections.ArrayList

abstract class RendererBase() {

    private var glObjects: ArrayList<IGLObject> = ArrayList<IGLObject>()
    private var needToDraw: Boolean = true
    private var needToPrepare: Boolean = true

    fun invalidate(_needToDraw: Boolean = true) {
        needToDraw = _needToDraw
    }

    fun attachGlObjects(vararg _glObjects: IGLObject) {
        for(glObject in _glObjects) {
            if(!glObjects.contains(glObject)) {
                glObjects.add(glObject)
            }
        }
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
     * called on gl thread for every drawing call
     */
    abstract fun onRender()

    /**
     * Called when the renderer object is disposed.
     */
    abstract fun onDispose()

    internal fun render() {

        if(needToPrepare) {
            // create gl objects
            for(glObj in glObjects) {
                glObj.create()
            }
            onPrepare()
            needToPrepare = false
        }

        if(!needToDraw) {
            return
        }

        for(glObj in glObjects) {
            glObj.bind()
        }
        onRender()

        //todo needToDraw = false
    }
}