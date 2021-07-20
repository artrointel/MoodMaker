package com.artrointel.moodmaker.kotesrenderengine.gl

/**
 * IGLObject is a data container regarding the gl rendering, but it should be thread-safely instantiated.
 */
interface IGLObject {

    // Create GL objects
    open fun create()

    // Bind object to current context
    open fun bind()

    // Dispose GL objects
    open fun dispose()
}