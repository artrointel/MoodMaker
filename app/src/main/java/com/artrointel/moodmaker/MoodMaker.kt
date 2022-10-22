package com.artrointel.moodmaker

import android.graphics.BitmapFactory
import com.artrointel.moodmaker.kotesrenderengine.*
import com.artrointel.moodmaker.kotesrenderengine.renderers.FireworkRenderer
import com.artrointel.moodmaker.kotesrenderengine.renderers.StarRenderer
import com.artrointel.moodmaker.kotesrenderengine.utils.Debugger
import java.nio.ByteBuffer
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

    fun runTestWorld() {
        val debugNode = RectNode3D()
        debugNode.transform.translate(500f, 1000f, 0f)
        debugNode.transform.scale(1000f, 2000f, 1.0f)
        world.getRoot().appendChild(debugNode)

        val circleNode = CircleNode3D()

        circleNode.transform.translate(500f, 1500f, 0f)
        circleNode.transform.scale(500f, 500f, 1f)
        world.getRoot().appendChild(circleNode)

        val bmp = BitmapFactory.decodeResource(world.context.resources, R.drawable.background_image)
        val buffer = ByteBuffer.allocate(bmp.byteCount)
        bmp.copyPixelsToBuffer(buffer)
        buffer.rewind()

        val imgNode = ImageNode(buffer, bmp.width, bmp.height)
        imgNode.transform.translate(500f, 1000f, 0f)
        imgNode.transform.scale((bmp.width/4).toFloat(), (bmp.height/4).toFloat(), 1f)
        world.getRoot().appendChild(imgNode)
    }

    fun runTestFirework() {
        val bgNode = Node3D()
        val firework = FireworkRenderer()
        val t = Timer()
        val initTime = Calendar.getInstance().timeInMillis
        t.schedule(object: TimerTask(){
            override fun run() {
                val time = (Calendar.getInstance().timeInMillis - initTime) / 1000f
                firework.setTime(time)
            }
        }, 0, (1000f/60f).toLong())

        bgNode.add(firework)
        bgNode.transform.translate(500f, 1000f, 0f)
        bgNode.transform.scale(1000f,1000f, 1f)

        world.getRoot().appendChild(bgNode)
    }

    fun runTestStars() {
        val bgNode = Node3D()
        val renderer = StarRenderer()
        val t = Timer()
        val initTime = Calendar.getInstance().timeInMillis
        t.schedule(object: TimerTask(){
            override fun run() {
                val time = (Calendar.getInstance().timeInMillis - initTime) / 1000.0f
                Debugger.log("currentTime:$time")
                renderer.setTime(time)
            }
        }, 0, (1000f/60f).toLong())

        bgNode.add(renderer)
        bgNode.transform.translate(500f, 500f, 0f)
        bgNode.transform.scale(500f,500f, 1f)

        world.getRoot().appendChild(bgNode)
    }
}