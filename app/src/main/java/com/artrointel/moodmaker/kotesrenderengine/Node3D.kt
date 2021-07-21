package com.artrointel.moodmaker.kotesrenderengine

import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4
import com.artrointel.moodmaker.kotesrenderengine.renderers.IRendererProjectionListener
import com.artrointel.moodmaker.kotesrenderengine.renderers.IRendererTransformListener
import com.artrointel.moodmaker.kotesrenderengine.renderers.RendererBase

open class Node3D {
    var transform: Matrix4 = Matrix4()
        private set

    private var renderers: ArrayList<RendererBase> = ArrayList()

    private var parent: Node3D? = null
    private var children: ArrayList<Node3D> = ArrayList()

    fun getParent(): Node3D? {
        return parent
    }

    fun appendChild(node: Node3D) {
        children.add(node)
    }

    fun removeChild(node: Node3D) {
        children.remove(node)
    }

    fun add(renderer: RendererBase) {
        renderers.add(renderer)
    }

    fun remove(renderer: RendererBase) {
        renderers.remove(renderer)
    }

    // render dfs
    internal open fun render() {
        for(child in children) {
            child.render()
        }

        for(renderer in renderers) {
            renderer.prepare()

            if(renderer is IRendererTransformListener) {
                if(transform.transformUpdated) {
                    renderer.onTransformUpdated(transform)
                }
            }
            if(renderer is IRendererProjectionListener) {
                var projMat = RenderWorldBase.get().projectionMatrix
                if(projMat.transformUpdated) {
                    renderer.onProjectionUpdated(projMat)
                }
            }
            renderer.render()
        }

        transform.transformUpdated = false
    }
}