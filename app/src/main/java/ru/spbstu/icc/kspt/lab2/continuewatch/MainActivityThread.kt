package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivityThread : AppCompatActivity() {
    var secondsElapsed: Int = 0
    var secondsElapsedBeforeStop = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var backgroundThread: Thread

    companion object {
        const val STATE_SECONDS = "secondsElapsed"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        with(savedInstanceState) { secondsElapsedBeforeStop = getInt(STATE_SECONDS) }
        secondsElapsed = secondsElapsedBeforeStop
        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onStart() {
        initBackgroundThread()
        backgroundThread.start()
        super.onStart()
    }

    override fun onStop() {
        backgroundThread.interrupt()
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        secondsElapsedBeforeStop = secondsElapsed
        outState.run {
            putInt(STATE_SECONDS, secondsElapsedBeforeStop)
        }
        super.onSaveInstanceState(outState)
    }

    private fun initBackgroundThread() {
        backgroundThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                try {
                    Thread.sleep(1000)
                    textSecondsElapsed.post {
                        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed++)
                    }
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                }
            }
        }
    }
}