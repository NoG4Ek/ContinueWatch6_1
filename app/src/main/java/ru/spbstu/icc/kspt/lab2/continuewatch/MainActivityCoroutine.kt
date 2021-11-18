package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.spbstu.icc.kspt.lab2.continuewatch.databinding.ActivityMainBinding

class MainActivityCoroutine : AppCompatActivity() {
    var secondsElapsedBeforeStop = 0
    var secondsElapsed = 0
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val STATE_SECONDS = "secondsElapsed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (isActive) {
                    delay(1000)
                    binding.textSecondsElapsed.text = getString(R.string.textSeconds, ++secondsElapsed)
                }
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        with(savedInstanceState) { secondsElapsedBeforeStop = getInt(STATE_SECONDS) }
        secondsElapsed = secondsElapsedBeforeStop
        binding.textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        secondsElapsedBeforeStop = secondsElapsed
        outState.run {
            putInt(STATE_SECONDS, secondsElapsedBeforeStop)
        }
        super.onSaveInstanceState(outState)
    }
}