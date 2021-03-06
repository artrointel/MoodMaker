package com.artrointel.moodmaker.kotesrenderengine.gl.utils

import java.nio.Buffer
import java.nio.FloatBuffer
import java.nio.IntBuffer

internal class GLBuffer {
    private var dataBuffer: Buffer? = null

    fun <T: Any> set(data: T) : GLBuffer {
        when (data) {
            is Int -> dataBuffer = IntBuffer.allocate(1).put(data as Int)
            is Float -> dataBuffer = FloatBuffer.allocate(1).put(data as Float)
            is IntArray -> dataBuffer =
                IntBuffer.allocate(data.size).put(data as IntArray)
            is FloatArray -> dataBuffer =
                FloatBuffer.allocate(data.size).put(data as FloatArray)
        }
        assert(dataBuffer != null, lazyMessage = {"Cannot created buffer!"});
        return this
    }

    fun asIntBuffer() : IntBuffer {
        return dataBuffer!! as IntBuffer
    }

    fun asFloatBuffer() : FloatBuffer {
        return dataBuffer!! as FloatBuffer
    }

    fun size() : Int {
        return dataBuffer!!.capacity()
    }

    fun get() : Buffer {
        dataBuffer!!.rewind()
        return dataBuffer!!
    }
}