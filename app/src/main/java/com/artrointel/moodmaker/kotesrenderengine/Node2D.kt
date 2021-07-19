package com.artrointel.moodmaker.kotesrenderengine

import android.renderscript.Matrix3f
import com.artrointel.moodmaker.kotesrenderengine.renderers.RendererBase

open class Node2D {
    var transFormMatrix = Matrix3f()

    private var renderers: ArrayList<RendererBase> = ArrayList()

    private var parent: Node2D? = null
    private var children: ArrayList<Node2D> = ArrayList()

    fun getParent(): Node2D? {
        return parent
    }

    fun appendChild(node: Node2D) {
        children.add(node)
    }

    fun removeChild(node: Node2D) {
        children.remove(node)
    }

    protected fun add(renderer: RendererBase) {
        renderers.add(renderer)
    }

    protected fun remove(renderer: RendererBase) {
        renderers.remove(renderer)
    }

    // render dfs
    internal fun render() {
        for(child in children) {
            child.render()
        }
        for(renderer in renderers) {
            renderer.render()
        }
    }
}