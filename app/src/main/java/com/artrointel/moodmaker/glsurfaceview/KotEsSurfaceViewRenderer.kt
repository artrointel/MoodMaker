package com.artrointel.moodmaker.glsurfaceview

import android.opengl.GLSurfaceView
import android.util.Log
import com.artrointel.moodmaker.kotesrenderengine.RenderWorldBase
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class KotEsSurfaceViewRenderer(world: RenderWorldBase) : GLSurfaceView.Renderer {
    companion object {
        const val tag = "KotEsSurfaceViewRenderer"
    }
    private val world: RenderWorldBase = world
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d(tag, "onSurfaceCreated")
        world.initialize()
    }

    override fun onDrawFrame(gl: GL10?) {
        // Log.d(tag, "onDrawFrame")
        world.render()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d(tag, "onSurfaceChanged: $width, $height")
        world.updateSize(width, height)
    }
}