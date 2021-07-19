package com.artrointel.moodmaker.glsurfaceview

import android.content.Context
import com.artrointel.moodmaker.kotesrenderengine.RenderWorldBase

// TODO Make IRenderEngine and communicate with platform codes from Impl

class RenderWorldImpl (context: Context, view: SView)
    : RenderWorldBase(context) {

    private val sView: SView = view

    override fun invalidate() {
        sView.requestRender()
    }
}