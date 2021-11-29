package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory

class MainActivityExecutorService : AppCompatActivity() {
    var secondsElapsed = 0
    private lateinit var textSecondsElapsed: TextView
    private var executorService: ExecutorService = Executors.newFixedThreadPool(1)
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)

        viewModel = MainViewModel(executorService, secondsElapsed)

        viewModel.mLDSecs.observe(this) {
            if (it != null) {
                runOnUiThread {
                    secondsElapsed = it
                    textSecondsElapsed.text = getString(R.string.textSeconds, it)
                }
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.run {
            secondsElapsed = getInt(SECONDS)
        }

        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onStart() {
        viewModel.executeThreadCounter()
        super.onStart()
    }

    override fun onStop() {
        viewModel.cancelThreadCounter()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(SECONDS, secondsElapsed)
        }
        super.onSaveInstanceState(outState)
    }

    companion object { const val SECONDS = "Seconds" }
}