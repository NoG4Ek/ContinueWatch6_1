package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import java.util.concurrent.Future

@HiltViewModel
class MainViewModel @Inject constructor(
    private val executor: ExecutorService,
    private val textSecondsElapsed: WeakReference<TextView>,
    secondsElapsed: Int
) : ViewModel() {
    private var secs = secondsElapsed
    private lateinit var future: Future<*>

    fun executeThreadCounter(@ApplicationContext context: Context) {
        future = executor.submit {
            while(!future.isCancelled) {
                Thread.sleep(1000)
                textSecondsElapsed.get()?.post {
                    textSecondsElapsed.get()?.text = context.applicationContext.getString(R.string.textSeconds, secs++)
                }
            }
        }
        Thread.currentThread().name = "debugThread"
    }

    fun cancelThreadCounter(): Int {
        future.cancel(true)
        return secs
    }
}