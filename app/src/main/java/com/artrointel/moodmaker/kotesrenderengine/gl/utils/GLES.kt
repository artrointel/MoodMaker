package com.artrointel.moodmaker.kotesrenderengine.gl.utils

import android.opengl.GLES30
import com.artrointel.moodmaker.kotesrenderengine.utils.Debugger

class GLES {
    companion object {
        fun glAssertOnError() {
            var err = GLES30.glGetError()
            if(err != GLES30.GL_NO_ERROR)
                Debugger.assert( "GLError:$err")

        }

        fun glCheckError() {
            var err = GLES30.glGetError()
            while(err != GLES30.GL_NO_ERROR) {
                Debugger.log( "GLError:$err")
                err = GLES30.glGetError()
            }
        }
    }
}