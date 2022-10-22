package com.artrointel.moodmaker

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.artrointel.moodmaker.fragments.RenderFragment

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainActivity : AppCompatActivity() {
    lateinit var valueAnimator: ValueAnimator
    var toggle = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var button = findViewById<Button>(R.id.toggle)
        var renderFragment = supportFragmentManager.findFragmentById(R.id.render_fragment) as RenderFragment
        button.setOnTouchListener { _: View, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    toggle = !toggle
                    if(toggle) {
                        valueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f)
                    } else {
                        valueAnimator = ValueAnimator.ofFloat(1.0f, 0.0f)
                    }
                    valueAnimator.apply {
                        duration = 800
                        addUpdateListener {
                            val value: Float = it.animatedValue as Float
                            renderFragment.setProgress(value)
                        }
                    }
                    valueAnimator.start()
                }
            }
            true
        }

    }

    override fun onResume() {
        super.onResume()

        hideNavigationBar()
    }

    private fun hideNavigationBar() {
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }
}