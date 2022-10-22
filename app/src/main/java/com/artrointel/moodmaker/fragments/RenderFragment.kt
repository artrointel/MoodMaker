package com.artrointel.moodmaker.fragments

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ConfigurationInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.artrointel.moodmaker.FocusDistortionScene
import com.artrointel.moodmaker.glsurfaceview.*
import com.artrointel.moodmaker.kotesrenderengine.RenderWorldBase

class RenderFragment : Fragment() {
    private lateinit var kotEsSurfaceView: KotEsSurfaceView
    private lateinit var world: RenderWorldBase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val am = context?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val cfg = am.deviceConfigurationInfo as ConfigurationInfo

        // Check GL ES version support
        if (cfg.glEsVersion?.get(0) != '3')
            throw Exception("The device does not support GL 3.x")

        kotEsSurfaceView = KotEsSurfaceView(requireContext())
        kotEsSurfaceView.setEGLContextClientVersion(3)

        world = RenderWorldImpl(requireContext(), kotEsSurfaceView)
        kotEsSurfaceView.setRenderer(KotEsSurfaceViewRenderer(world))
        // todo
        // kotEsSurfaceView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY

        FocusDistortionScene.getInstance()!!.world = world
        FocusDistortionScene.getInstance()!!.initWorld()
        return kotEsSurfaceView
    }

    fun setProgress(progress: Float) {
        FocusDistortionScene.getInstance()!!.setFocusImageAlpha(progress)
        FocusDistortionScene.getInstance()!!.setFocusImageScale(
            10.0f - 9.0f * progress, 10.0f - 9.0f * progress)
    }
}