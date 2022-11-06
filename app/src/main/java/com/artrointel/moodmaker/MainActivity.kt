package com.artrointel.moodmaker

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.artrointel.moodmaker.fragments.RenderFragment

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var valueAnimator: ValueAnimator? = null
    var toggle = false
    var currentValue: Float = 0.0f
    lateinit var renderFragment: RenderFragment
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.toggle)
        renderFragment = supportFragmentManager.findFragmentById(R.id.render_fragment) as RenderFragment
        renderFragment.setProgress(0.0f)

        button.setOnTouchListener { _: View, event: MotionEvent ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    valueAnimator?.cancel()
                    valueAnimator = if (toggle) {
                        ValueAnimator.ofFloat(0.0f, 1.0f)
                    } else {
                        ValueAnimator.ofFloat(1.0f, 0.0f)
                    }
                    toggle = !toggle
                    valueAnimator!!.apply {
                        duration = 2000
                        addUpdateListener {
                            currentValue = it.animatedValue as Float
                            renderFragment.setProgress(currentValue)
                            renderFragment.invalidate()
                        }
                    }
                    valueAnimator!!.start()
                }
            }
            true
        }
        initSpinner()
    }

    private fun initSpinner() {
        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(this, R.array.transition_array, android.R.layout.simple_spinner_item)
            .also {adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        renderFragment.setTransitionScene(parent!!.selectedItemPosition)
        renderFragment.setProgress(0.0f)
        renderFragment.invalidate()
        toggle = true
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // do nothing
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