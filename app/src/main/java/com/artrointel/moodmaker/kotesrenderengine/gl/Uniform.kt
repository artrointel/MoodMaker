package com.artrointel.moodmaker.kotesrenderengine.gl

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.DataType
import com.artrointel.moodmaker.kotesrenderengine.gl.utils.GLBuffer
import com.artrointel.moodmaker.kotesrenderengine.utils.Debugger

class Uniform(_program: Program, uniformType: DataType, uniformName: String, dataCount: Int = 1 )
    : IGLObject {

    private val program: Program = _program
    private val name: String = uniformName
    private val type: DataType = uniformType
    private val count: Int = dataCount

    private var location: Int = -1
    private lateinit var buffer: GLBuffer

    fun interface UniformUpdater {
        fun update()
    }
    private var uniformUpdater: UniformUpdater? = null

    // int, float, int array, float array
    fun <T: Any> set(data: T) : Uniform {
        type.assertIfTypeMismatch(data)
        buffer = GLBuffer().set(data)
        if(uniformUpdater == null) {
            determineUniformUpdater(data, type.dataLength, count)
        }
        return this
    }

    override fun bind() {
        uniformUpdater!!.update()
    }

    override fun create() {
        location = GLES30.glGetUniformLocation(program.id, name)
    }

    override fun dispose() {

    }

    private fun <T: Any> determineUniformUpdater(data: T, dataLength: Int, count: Int) {
        when(data) {
            is Int -> {
                uniformUpdater = UniformUpdater {
                    GLES30.glUniform1iv(location, 1, buffer.asIntBuffer())
                }
            }

            is Float -> {
                uniformUpdater = UniformUpdater {
                    GLES30.glUniform1fv(location, 1, buffer.asFloatBuffer())

                }
            }

            // For an integer and also integer array
            is IntArray -> {
                uniformUpdater = UniformUpdater {
                    GLES30.glUniform1iv(location, count, buffer.asIntBuffer())
                }
            }

            // For vec* and mat* and array of them
            is FloatArray -> {
                when(dataLength) {
                    // For vec2,3,4
                    1 -> {
                        uniformUpdater = UniformUpdater {
                            GLES30.glUniform1fv(location, count, buffer.asFloatBuffer())
                        }
                    }
                    2 -> {
                        uniformUpdater = UniformUpdater {
                            GLES30.glUniform2fv(location, count, buffer.asFloatBuffer())
                        }
                    }
                    3 -> {
                        uniformUpdater = UniformUpdater {
                            GLES30.glUniform3fv(location, count, buffer.asFloatBuffer())
                        }
                    }
                    4 -> {
                        uniformUpdater = UniformUpdater {
                            GLES30.glUniform4fv(location, count, buffer.asFloatBuffer())
                        }
                    }

                    // For mat3,4
                    9 -> {
                        uniformUpdater = UniformUpdater {
                            GLES30.glUniformMatrix3fv(location, count, false, buffer.asFloatBuffer())
                        }
                    }
                    16 -> {
                        uniformUpdater = UniformUpdater {
                            GLES30.glUniformMatrix4fv(location, count, false, buffer.asFloatBuffer())
                        }
                    }
                }
            }
            else -> {
                Debugger.assert("Cannot read the type of uniform data!")
            }
        }
    }
}