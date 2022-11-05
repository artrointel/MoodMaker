package com.artrointel.moodmaker.kotesrenderengine

abstract class Scene (
    protected val world: RenderWorldBase
        ) {

    abstract fun initialize()
    abstract fun setTime(time: Float)
}