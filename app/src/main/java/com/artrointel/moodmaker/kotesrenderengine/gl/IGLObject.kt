package com.artrointel.moodmaker.kotesrenderengine.gl

interface IGLObject {

    // Bind object to current context
    open fun bind()

    // Create GL objects
    open fun create()

    // Dispose GL objects
    open fun dispose()
}