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
        FocusDistortionScene.getInstance()!!.setMaskImageAlpha(progress)
        FocusDistortionScene.getInstance()!!.setMaskImageScale(
            5.0f - 4.0f * progress, 5.0f - 4.0f * progress)

        FocusDistortionScene.getInstance()!!.setRadius(progress * 1.5f + 1.0f)
        FocusDistortionScene.getInstance()!!.setDepth(progress * 1.5f)
    }

}