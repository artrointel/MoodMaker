package com.artrointel.moodmaker.fragments

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ConfigurationInfo
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artrointel.moodmaker.glsurfaceview.*
import com.artrointel.moodmaker.kotesrenderengine.RenderWorldBase
import com.artrointel.moodmaker.kotesrenderengine.Scene
import com.artrointel.moodmaker.scene.*

class RenderFragment : Fragment() {
    private lateinit var kotEsSurfaceView: KotEsSurfaceView
    private lateinit var world: RenderWorldBase
    private var scene: Scene? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val am = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cfg = am.deviceConfigurationInfo as ConfigurationInfo

        // Check GL ES version support
        if (cfg.glEsVersion?.get(0) != '3')
            throw Exception("The device does not support GL 3.x")

        kotEsSurfaceView = KotEsSurfaceView(requireContext())
        kotEsSurfaceView.setEGLContextClientVersion(3)

        world = RenderWorldImpl(requireContext(), kotEsSurfaceView)
        kotEsSurfaceView.setRenderer(KotEsSurfaceViewRenderer(world))
        kotEsSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        return kotEsSurfaceView
    }

    fun setTransitionScene(index: Int) {
        when (index) {
            0 -> scene = FocusDistortionTransition(world)
            1 -> scene = WarpTransition(world)
            2 -> scene = PixelateTransition(world)
            3 -> scene = RippleTransition(world)
        }
        scene!!.initialize()
    }

    fun invalidate() {
        kotEsSurfaceView.requestRender()
    }

    fun setProgress(progress: Float) {
        scene?.setTime(progress)
    }

}