package com.artrointel.moodmaker.kotesrenderengine.common

import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Quaternion (
    var r: Float = 1.0f,
    var i: Float = 0.0f,
    var j: Float = 0.0f,
    var k: Float = 0.0f) {

    constructor(q: Quaternion) : this(
        q.r, q.i, q.j, q.k)

    fun setByAngleAxis(angle: Float, axis: Vector3) {
        val halfRadian = (PI * angle / 360.0f).toFloat()
        axis.normalize()

        val s = sin(halfRadian)
        r = cos(halfRadian)
        i = axis.x * s
        j = axis.y * s
        k = axis.z * s
    }

    fun multiply(q: Quaternion) {
        val s = Quaternion(this)
        r = s.r * q.r - s.i * q.i - s.j * q.j - s.k * q.k
        i = s.r * q.i + s.i * q.r + s.j * q.k - s.k * q.j
        j = s.r * q.j + s.j * q.r + s.k * q.i - s.i * q.k
        k = s.r * q.k + s.k * q.r + s.i * q.j - s.j * q.i
    }

    fun normalize() {
        var d = r * r + i * i + j * j + k * k
        if (d.equals(0)) {
            return;
        }
        val invD = 1 / sqrt(d)

        r *= invD
        i *= invD
        j *= invD
        k *= invD
    }
}