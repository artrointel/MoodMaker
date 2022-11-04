package com.artrointel.moodmaker.kotesrenderengine.common

import kotlin.math.sqrt

class Vector3 (
    var x: Float = 0.0f,
    var y: Float = 0.0f,
    var z: Float = 0.0f) {

    companion object {
        val ZERO: Vector3 = Vector3(0.0f, 0.0f, 0.0f)
        val ONE: Vector3 = Vector3(1.0f, 1.0f, 1.0f)
    }
    constructor(v: Vector3) : this(
        v.x, v.y, v.z)

    fun invert() {
        x = -x
        y = -y
        z = -z
    }

    fun scalarProduct(v: Vector3): Float {
        return x * v.x + y * v.y + z * v.z
    }

    fun vectorProduct(v: Vector3): Vector3 {
        return Vector3(
            y * v.z - z * v.y,
            z * v.x - x * v.z,
            x * v.y - y * v.x)
    }

    fun multiply(s: Float) {
        x *= s
        y *= s
        z *= s
    }

    fun normalize() {
        val scalar = magnitude()
        if (scalar > 0) {
            val inverseScalar = 1 / scalar
            x *= inverseScalar
            y *= inverseScalar
            z *= inverseScalar
        }
    }

    fun magnitude(): Float {
        return sqrt(squareMagnitude())
    }

    fun squareMagnitude(): Float {
        return x*x + y*y + z*z
    }
}