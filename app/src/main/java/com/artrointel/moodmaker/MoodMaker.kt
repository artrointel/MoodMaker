package com.artrointel.moodmaker

import com.artrointel.moodmaker.kotesrenderengine.CircleNode2D
import com.artrointel.moodmaker.kotesrenderengine.RectNode2D
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

    fun load(moodTheme: MoodTheme) {

    }

    fun runTestWorld() {
        var debugNode = RectNode2D()
        debugNode.transform.rotate(45.0f)
        world!!.getRoot().appendChild(debugNode)
        var circleNode = CircleNode2D()
        circleNode.transform.scale(0.5f, 0.5f)
        world!!.getRoot().appendChild(circleNode)
    }
}