package ru.spbstu.icc.kspt.lab2.continuewatch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private var _secondsElapsed = 0
    val secondsElapsed = MutableLiveData(_secondsElapsed)
    lateinit var job: Job

    fun startTimer() {
       job = viewModelScope.launch {
            while (isActive) {
                delay(1000)
                secondsElapsed.value = ++_secondsElapsed
            }
       }
    }

    fun stopTimer() {
        _secondsElapsed = 0
        job.cancel()
    }
}