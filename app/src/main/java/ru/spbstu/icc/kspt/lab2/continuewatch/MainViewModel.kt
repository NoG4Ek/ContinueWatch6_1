package ru.spbstu.icc.kspt.lab2.continuewatch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future


class MainViewModel constructor(
    private val executor: ExecutorService,
    secondsElapsed: Int
) : ViewModel() {
    val mLDSecs = MutableLiveData<Int>()
    private var secs = secondsElapsed
    private lateinit var future: Future<*>

    fun executeThreadCounter() {
        mLDSecs.postValue(secs)
        future = executor.submit {
            while(!future.isCancelled) {
                Thread.sleep(1000)
                secs++
                mLDSecs.postValue(secs)
            }
        }
        Thread.currentThread().name = "debugThread"
    }

    fun cancelThreadCounter() {
        future.cancel(true)
    }
}