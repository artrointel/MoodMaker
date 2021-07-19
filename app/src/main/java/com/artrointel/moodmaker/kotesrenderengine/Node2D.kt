package com.artrointel.moodmaker.kotesrenderengine

import com.artrointel.moodmaker.kotesrenderengine.common.Rect
import com.artrointel.moodmaker.kotesrenderengine.renderers.RendererBase

open class Node2D {
    private var geometry: Rect = Rect() // todo use transform matrix
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

    open fun setSize(_width: Float, _height: Float) {
        geometry.width = _width
        geometry.height = _height
    }

    open fun setPosition(_x: Float, _y: Float) {
        geometry.x = _x
        geometry.y = _y
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