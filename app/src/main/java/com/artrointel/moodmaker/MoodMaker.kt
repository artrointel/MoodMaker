package com.artrointel.moodmaker

import com.artrointel.moodmaker.kotesrenderengine.RenderWorldBase

// controller of the world
class MoodMaker {
    private constructor()

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
}