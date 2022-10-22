package com.artrointel.moodmaker.kotesrenderengine.utils

import android.util.Log

class Debugger {
    companion object {
        private const val tag: String = "Debugger"

        fun log(string: String) {
            Log.d(tag, string)
        }

        fun log(objAsTag: Unit, string: String) {
            Log.d(objAsTag.javaClass.name, string)
        }

        fun log(_tag: String, string: String) {
            Log.d(_tag, string)
        }

        fun assertFalse(string: String) {
            assert(false, lazyMessage = {string})
        }

        fun assertNot(assert: Boolean, string: String) {
            assert(!assert, lazyMessage = {string})
        }

        fun assert(assert: Boolean, string: String) {
            assert(assert, lazyMessage = {string})
        }
    }
}