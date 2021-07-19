package com.artrointel.moodmaker.kotesrenderengine.common

// todo remove this and use matrix instead
class Rect {
    var x: Float = 0f
    var y: Float = 0f
    var width: Float = 0f
    var height: Float = 0f

    constructor()

    constructor(_x: Number, _y: Number, _width: Number, _height: Number) {
        x = _x as Float
        y = _y as Float
        width = _width as Float
        height = _height as Float
    }

    fun clone() : Rect {
        return Rect(x, y, width, height)
    }
}