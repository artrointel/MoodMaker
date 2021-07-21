package com.artrointel.moodmaker

import android.animation.ValueAnimator
import android.content.res.Resources
import android.graphics.BitmapFactory
import com.artrointel.moodmaker.kotesrenderengine.*
import com.artrointel.moodmaker.kotesrenderengine.renderers.FireworkRenderer
import java.nio.ByteBuffer
import java.nio.IntBuffer
import java.util.*

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
        //var debugNode = RectNode3D()
        //debugNode.transform.rotate(45f, 0f, 0f, 1f)
        //debugNode.transform.translate(0.5f, 0.0f, 0.0f)
        //world!!.getRoot().appendChild(debugNode)
        //var circleNode = CircleNode3D()
        //circleNode.transform.translate(300f, 300f, 0f)
        //circleNode.transform.scale(300f, 300f, 1f)
        //world!!.getRoot().appendChild(circleNode)
        var bmp = BitmapFactory.decodeResource(world.context.resources, R.drawable.test_image)
        var buffer = ByteBuffer.allocate(bmp.byteCount)
        bmp.copyPixelsToBuffer(buffer)
        buffer.rewind()
        var imgNode = ImageNode(buffer, bmp.width, bmp.height)
        imgNode.transform.translate((bmp.width/4).toFloat(), (bmp.height/4).toFloat(), 0f)
        imgNode.transform.scale((bmp.width/4).toFloat(), (bmp.height/4).toFloat(), 1f)

        world.getRoot().appendChild(imgNode)

    }

    fun runBackground() {
        var bgNode = Node3D()
        var firework = FireworkRenderer()
        var t = Timer()
        var initTime = Calendar.getInstance().timeInMillis
        t.schedule(object: TimerTask(){
            override fun run() {
                var time = ((Calendar.getInstance().timeInMillis - initTime) / 1000).toFloat()
                firework.setTime(time)
            }
        }, 0, (1000f/60f).toLong())

        bgNode.add(firework)
        bgNode.transform.translate(500f, 500f, 0f)
        bgNode.transform.scale(500f,500f, 1f)

        world.getRoot().appendChild(bgNode)
    }
}