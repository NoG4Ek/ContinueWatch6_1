package ru.spbstu.icc.kspt.lab2.continuewatch

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future


class MainViewModel(application: Application): AndroidViewModel(application) {
    private val executor: ExecutorService = getApplication<App>().executorService
    val mLDSecs = MutableLiveData<Int>()
    var secs = 0
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