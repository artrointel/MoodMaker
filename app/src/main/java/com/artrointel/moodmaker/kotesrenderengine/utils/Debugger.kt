package com.artrointel.moodmaker.kotesrenderengine.utils

import android.util.Log

class Debugger {
    companion object {
        private const val tag: String = "Debugger"
        fun log(string: String) {
            Log.d(tag, string)
        }

        fun log(tag: String, string: String) {
            Log.d(tag, string)
        }

        fun assert(string: String) {
            assert(false, lazyMessage = {string})
        }

        fun assertIf(assert: Boolean, string: String) {
            assert(!assert, lazyMessage = {string})
        }

        fun assertIfNot(assert: Boolean, string: String) {
            assert(assert, lazyMessage = {string})
        }
    }
}