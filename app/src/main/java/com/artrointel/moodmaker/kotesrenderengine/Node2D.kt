package com.artrointel.moodmaker.kotesrenderengine

import com.artrointel.moodmaker.kotesrenderengine.common.Matrix3
import com.artrointel.moodmaker.kotesrenderengine.renderers.ITransformSupport
import com.artrointel.moodmaker.kotesrenderengine.renderers.RendererBase

open class Node2D {
    var transform: Matrix3 = Matrix3()
        private set

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
    internal open fun render() {
        for(child in children) {
            child.render()
        }

        for(renderer in renderers) {
            when(renderer) {
                is ITransformSupport -> {
                    renderer.setTransformMatrix(transform)
                    transform.transformUpdated = false
                }
            }
            renderer.render()
        }
    }
}