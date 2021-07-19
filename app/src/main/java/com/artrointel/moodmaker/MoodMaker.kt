package com.artrointel.moodmaker

import com.artrointel.moodmaker.renderengine.RectNode2D
import com.artrointel.moodmaker.renderengine.RenderWorldBase

// controller of the world
class MoodMaker {

    private constructor() {

    }

    lateinit var world: RenderWorldBase

    companion object {
        @Volatile private var instance: MoodMaker? = null
        @JvmStatic fun getInstance() : MoodMaker? {
            synchronized(this) {
                if(instance == null) {
                    instance = MoodMaker()
                }
                return instance
            }
        }
    }

    fun test() {
        var root = world.getRoot()
        root.appendChild(RectNode2D())
        world.invalidate()
    }
}