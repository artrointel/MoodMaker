package com.artrointel.moodmaker.kotesrenderengine

import com.artrointel.moodmaker.kotesrenderengine.common.Matrix4
import com.artrointel.moodmaker.kotesrenderengine.renderers.ScreenSizeChangedListener

class KotESContext private constructor() {
    companion object {
        private var contextMap = HashMap<Long, KotESContext>()

        internal fun createContext() {
            contextMap[Thread.currentThread().id] = KotESContext()
        }

        internal fun getCurrent(): KotESContext {
            return contextMap[Thread.currentThread().id]!!
        }
    }

    var projectionMatrix: Matrix4 = Matrix4()
        private set
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    internal var screenSizeChangedListener = ArrayList<ScreenSizeChangedListener>()

    internal fun updateScreenSize(_screenWidth: Int, _screenHeight: Int) {
        screenWidth = _screenWidth
        screenHeight = _screenHeight
        projectionMatrix.loadProperOrtho(screenWidth, screenHeight)
        for(listener in screenSizeChangedListener) {
            listener.onScreenSizeUpdated(screenWidth, screenHeight)
        }
    }
}