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

    // TODO Add General Geometry APIs
    // add Transform class that have Quaternion orientation, Vec3 position, and scale.
    // disable direct access of this transform matrix
    // setOrientation Quaternion
    // setPosition
    fun setSize(x: Int, y: Int, z: Int) {
        transform = Matrix4()
        transform.translate(x.toFloat()*0.5f, y.toFloat()*0.5f, z.toFloat()*0.5f)
        transform.scale(x.toFloat()*0.5f, y.toFloat()*0.5f, z.toFloat()*0.5f)
    }

    // TODO remove
    fun resetMatrix() {
        transform = Matrix4()
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