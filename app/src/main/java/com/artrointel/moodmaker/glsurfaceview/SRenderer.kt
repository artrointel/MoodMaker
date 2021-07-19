package com.artrointel.moodmaker.glsurfaceview

import android.opengl.GLSurfaceView
import android.util.Log
import com.artrointel.moodmaker.kotesrenderengine.RenderWorldBase
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SRenderer(_world: RenderWorldBase) : GLSurfaceView.Renderer {
    private val world: RenderWorldBase = _world!!
    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        Log.d("SRenderer", "onSurfaceCreated")
    }

    override fun onDrawFrame(gl: GL10?) {
        Log.d("SRenderer", "onDrawFrame")
        world.render()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        Log.d("SRenderer", "onSurfaceChanged: " + width + "," + height)
        world.updateSize(width, height)
    }
}