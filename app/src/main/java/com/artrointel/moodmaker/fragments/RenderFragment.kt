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
import com.artrointel.moodmaker.MoodMaker
import com.artrointel.moodmaker.glsurfaceview.RenderWorldImpl
import com.artrointel.moodmaker.glsurfaceview.SRenderer
import com.artrointel.moodmaker.glsurfaceview.SView
import com.artrointel.moodmaker.renderengine.RenderWorldBase

class RenderFragment : Fragment() {
    private lateinit var sView: SView
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

        sView = SView(requireContext())

        sView.setEGLContextClientVersion(3)
        world = RenderWorldImpl(requireContext(), sView)
        sView.setRenderer(SRenderer(world))
        // sView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        MoodMaker.getInstance()!!.world = world

        // todo test
        MoodMaker.getInstance()!!.test()

        return sView
    }
}