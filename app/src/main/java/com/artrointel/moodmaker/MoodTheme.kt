package com.artrointel.moodmaker

import com.artrointel.moodmaker.viewmodel.AnimatableEffect
import com.artrointel.moodmaker.viewmodel.BackgroundCircleImage
import com.artrointel.moodmaker.viewmodel.BackgroundCircularText
import com.artrointel.moodmaker.viewmodel.BackgroundSound

class MoodTheme {
    // brightness
    var screenBrightness = 1.0

    // images
    var backgroundImages = ArrayList<BackgroundCircleImage>()
    // effects
    var animatableEffects = ArrayList<AnimatableEffect>()
    // circular text
    var circularText = BackgroundCircularText()

    // sound
    var sound = BackgroundSound()

    init {

    }
}