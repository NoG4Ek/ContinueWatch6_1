package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.spbstu.icc.kspt.lab2.continuewatch.databinding.ActivityMainBinding

class MainActivityCoroutine : AppCompatActivity() {
    var secondsElapsedBeforeStop = 0
    var secondsElapsedAfterStop = 0
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    companion object {
        const val STATE_SECONDS = "secondsElapsed"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MainViewModel()
        viewModel.secondsElapsed.observe(this) { value ->
            secondsElapsedAfterStop = value
            binding.textSecondsElapsed.text = "Seconds elapsed ${secondsElapsedBeforeStop + secondsElapsedAfterStop}"
        }
    }

    override fun onStart() {
        viewModel.startTimer()
        super.onStart()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        with(savedInstanceState) { secondsElapsedBeforeStop = getInt(STATE_SECONDS) }
        binding.textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsedBeforeStop)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onStop() {
        viewModel.stopTimer()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        secondsElapsedBeforeStop += secondsElapsedAfterStop
        outState.run {
            putInt(STATE_SECONDS, secondsElapsedBeforeStop)
        }
        super.onSaveInstanceState(outState)
    }
}