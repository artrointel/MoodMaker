package com.artrointel.moodmaker.kotesrenderengine.common

class Transform {
    var orientation: Quaternion = Quaternion()
    set(q) {
        field = Quaternion(q)
        transformUpdated = true
    }
    var position: Vector3 = Vector3()
    set(v) {
        field = Vector3(v)
        transformUpdated = true
    }

    var scale: Vector3 = Vector3.ONE
    set(v) {
        field = Vector3(v)
        transformUpdated = true
    }

    private var transformMatrix: Matrix4 = Matrix4(Matrix4.IDENTITY)
    private var transformUpdated: Boolean = false

    fun getMatrix(): Matrix4 {
        calculate()
        return transformMatrix
    }

    private fun calculate() {
        if (transformUpdated) {
            transformMatrix = Matrix4(Matrix4.IDENTITY)
            // update scale data
            transformMatrix.array[0] =
                1 - 2 * orientation.j * orientation.j - 2 * orientation.k * orientation.k
            transformMatrix.array[1] =
                2 * orientation.i * orientation.j + 2 * orientation.r * orientation.k
            transformMatrix.array[2] =
                2 * orientation.i * orientation.k - 2 * orientation.r * orientation.j
            transformMatrix.array[3] = 0.0f

            transformMatrix.array[4] =
                2 * orientation.i * orientation.j - 2 * orientation.r * orientation.k
            transformMatrix.array[5] =
                1 - 2 * orientation.i * orientation.i - 2 * orientation.k * orientation.k
            transformMatrix.array[6] =
                2 * orientation.j * orientation.k + 2 * orientation.r * orientation.i
            transformMatrix.array[7] = 0.0f

            transformMatrix.array[8] =
                2 * orientation.i * orientation.k + 2 * orientation.r * orientation.j
            transformMatrix.array[9] =
                2 * orientation.j * orientation.k  - 2 * orientation.r * orientation.i
            transformMatrix.array[10] =
                1 - 2 * orientation.i * orientation.i - 2 * orientation.j * orientation.j
            transformMatrix.array[11] = 0.0f

            transformMatrix.array[12] = position.x
            transformMatrix.array[13] = position.y
            transformMatrix.array[14] = position.z
            transformMatrix.array[15] = 1.0f

            val scaleMatrix = Matrix4()
            scaleMatrix.setScale(scale)
            transformMatrix = Matrix4.multiply(transformMatrix, scaleMatrix)
            transformUpdated = false
        }


    }

}