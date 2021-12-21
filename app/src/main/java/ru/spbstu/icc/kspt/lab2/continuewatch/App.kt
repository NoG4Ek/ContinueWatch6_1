package ru.spbstu.icc.kspt.lab2.continuewatch

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class App: Application() {
    val executorService: ExecutorService = Executors.newFixedThreadPool(1)
}