package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity2: AppCompatActivity() {
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

        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        secondsElapsedBeforeStop = sharedPref.getInt(STATE_SECONDS, secondsElapsedBeforeStop)
        secondsElapsed = secondsElapsedBeforeStop

        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        backgroundThread.start()
    }

    override fun onStart() {
        secondsElapsed = secondsElapsedBeforeStop
        textSecondsElapsed.text = getString(R.string.textSeconds, secondsElapsed)
        super.onStart()
    }

    override fun onStop() {
        secondsElapsedBeforeStop = secondsElapsed

        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key),
            Context.MODE_PRIVATE
        )
        with (sharedPref.edit()) {
            putInt(STATE_SECONDS, secondsElapsedBeforeStop)
            apply()
        }
        super.onStop()
    }
}