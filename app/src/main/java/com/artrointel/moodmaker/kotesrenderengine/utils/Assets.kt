package com.artrointel.moodmaker.kotesrenderengine.utils

import android.content.res.AssetManager
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

// todo make singleton
class Assets {
    companion object {
        private const val SHADER_PATH: String = "shaders"

        lateinit var asset: AssetManager

        fun getShaderString(fileName: String): String {
            return readText(SHADER_PATH + File.separator + fileName)
        }

        private fun readText(path: String): String {
            var iStream = asset!!.open(path)
            var reader = BufferedReader(InputStreamReader(iStream!!))
            var sb = StringBuilder()
            var line = reader.readLine()
            while(line != null) {
                sb.append(line)
                sb.appendLine()
                line = reader.readLine()
            }
            reader.close()
            return sb.toString()
        }

    }
}