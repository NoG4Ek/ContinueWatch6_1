package ru.spbstu.icc.kspt.lab2.continuewatch

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    var secondsElapsedBeforeStop = 0
    lateinit var textSecondsElapsed: TextView

    companion object {
        const val STATE_SECONDS = "secondsElapsed"
    }

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed++)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        with(savedInstanceState) { secondsElapsedBeforeStop = getInt(STATE_SECONDS) }
        secondsElapsed = secondsElapsedBeforeStop
        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onStart() {
        secondsElapsed = secondsElapsedBeforeStop
        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        super.onStart()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        secondsElapsedBeforeStop = secondsElapsed
        outState.run {
            putInt(STATE_SECONDS, secondsElapsedBeforeStop)
        }
        super.onSaveInstanceState(outState)
    }
}
