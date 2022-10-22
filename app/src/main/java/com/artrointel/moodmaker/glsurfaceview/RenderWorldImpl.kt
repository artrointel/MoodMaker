package com.artrointel.moodmaker.glsurfaceview

import android.content.Context
import com.artrointel.moodmaker.kotesrenderengine.RenderWorldBase

// TODO Make IRenderEngine and communicate with platform codes from Impl

class RenderWorldImpl (context: Context, view: KotEsSurfaceView)
    : RenderWorldBase(context) {

    private val kotEsSurfaceView: KotEsSurfaceView = view

    override fun invalidate() {
        kotEsSurfaceView.requestRender()
    }
}