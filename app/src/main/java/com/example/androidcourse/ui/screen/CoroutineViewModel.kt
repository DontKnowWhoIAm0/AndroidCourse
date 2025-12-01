package com.example.androidcourse.ui.screen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidcourse.R
import com.example.androidcourse.data.CoroutineSettings
import com.example.androidcourse.data.DispatcherType
import com.example.androidcourse.data.ResetException
import com.example.androidcourse.data.SnackbarException
import com.example.androidcourse.data.ToastException
import kotlinx.coroutines.*
import kotlin.random.Random

class CoroutineViewModel : ViewModel()  {

    private lateinit var appContext: Context

    fun setContext(context: Context) {
        appContext = context.applicationContext
    }

    var settings by mutableStateOf(CoroutineSettings())
        private set

    var isRunning = mutableStateOf(false)
        private set

    var completed = mutableIntStateOf(0)
        private set

    var failed = mutableIntStateOf(0)
        private set

    var cancelled = mutableIntStateOf(0)
        private set

    private var stoppedInBackground = 0
    private var stoppedSettings: CoroutineSettings? = null

    var toastMessage = mutableStateOf<String?>(null)
        private set

    var showSnackbar = mutableStateOf(false)

    var runInBackground by mutableStateOf(true)
        private set

    var currentSettings: CoroutineSettings? = null

    private var parentJob: Job? = null

    fun onCountChange(value: Int) {
        val fixed = (value.coerceIn(10, 100) / 5) * 5
        settings = settings.copy(count = fixed)
    }

    fun onDispatcherChange(type: DispatcherType) {
        settings = settings.copy(dispatcher = type)
    }

    fun onSequentialToggle(value: Boolean) {
        settings = settings.copy(
            sequential = value,
            parallel = !value
        )
    }

    fun onParallelToggle(value: Boolean) {
        settings = settings.copy(
            sequential = !value,
            parallel = value
        )
    }

    fun onDelayedToggle(value: Boolean) {
        settings = settings.copy(delayedStart = value)
    }

    fun onRunInBackgroundToggle(value: Boolean) {
        runInBackground = value
    }

    fun onStartClicked(settingsToUse: CoroutineSettings? = null) {
        if (isRunning.value) return

        completed.value = 0
        failed.value = 0
        isRunning.value = true

        currentSettings = settingsToUse ?: settings
        val dispatcher = when (currentSettings!!.dispatcher) {
            DispatcherType.DEFAULT -> Dispatchers.Default
            DispatcherType.IO -> Dispatchers.IO
            DispatcherType.MAIN -> Dispatchers.Main
            DispatcherType.UNCONFINED -> Dispatchers.Unconfined
        }

        parentJob = viewModelScope.launch {

            if (currentSettings!!.delayedStart) delay(3000)

            try {
                if (currentSettings!!.sequential) {
                    repeat(currentSettings!!.count) {
                        try {
                            launchOne(dispatcher)
                        } catch (e: ToastException) {
                            withContext(Dispatchers.Main) {
                                failed.value++
                                toastMessage.value = appContext.getString(R.string.toast_error)
                            }
                        } catch (e: SnackbarException) {
                            withContext(Dispatchers.Main) {
                                failed.value++
                                showSnackbar.value = true
                            }
                        } catch (e: ResetException) {
                            withContext(Dispatchers.Main) {
                                failed.value++
                            }
                            resetToDefaults()
                        }
                    }
                } else {
                    val jobs = List(currentSettings!!.count) {
                        launch(dispatcher) {
                            try {
                                doWork()
                            } catch (e: ToastException) {
                                withContext(Dispatchers.Main) {
                                    failed.value++
                                    toastMessage.value = appContext.getString(R.string.toast_error)
                                }
                            } catch (e: SnackbarException) {
                                withContext(Dispatchers.Main) {
                                    failed.value++
                                    showSnackbar.value = true
                                }
                            } catch (e: ResetException) {
                                withContext(Dispatchers.Main) {
                                    failed.value++
                                }
                                resetToDefaults()
                            }
                        }
                    }
                    jobs.joinAll()
                }
            } finally {
                withContext(Dispatchers.Main) {
                    isRunning.value = false
                    currentSettings = null
                }
            }
        }
    }

    fun onCancelClicked() {
        parentJob?.cancel()

        viewModelScope.launch(Dispatchers.Main) {
            parentJob?.join()
            cancelled.value = (currentSettings?.count ?: 0) - failed.value - completed.value
            toastMessage.value = appContext.getString(R.string.cancelled, cancelled.value)
            isRunning.value = false
        }
    }

    private suspend fun launchOne(dispatcher: CoroutineDispatcher) {
        withContext(dispatcher) {
            doWork()
        }
    }

    private suspend fun doWork() {
        val delayTime = Random.nextLong(1_000L, 10_001L)
        delay(delayTime)

        if (delayTime >= 7000 && Random.nextFloat() < 0.3f) {
            when (Random.nextInt(3)) {
                0 -> throw ToastException()
                1 -> throw SnackbarException()
                2 -> throw ResetException()
            }
        }

        withContext(Dispatchers.Main) {
            completed.value++
        }
    }

    private suspend fun resetToDefaults() {
        withContext(Dispatchers.Main) {
            settings = CoroutineSettings()
            toastMessage.value = appContext.getString(R.string.settings_reset)
        }
    }

    fun stopCoroutinesOnBackground() {
        if (!isRunning.value) return
        val remaining = (currentSettings?.count ?: 0) - completed.value - failed.value
        if (remaining <= 0) return

        stoppedInBackground = remaining
        stoppedSettings = currentSettings?.copy(count = remaining)

        parentJob?.cancel()

        viewModelScope.launch(Dispatchers.Main) {
            parentJob?.join()
            cancelled.value += stoppedInBackground
            isRunning.value = false
        }
    }

    fun restartStoppedCoroutines() {
        val settingsToRestart = stoppedSettings ?: return

        stoppedInBackground = 0
        stoppedSettings = null

        onStartClicked(settingsToRestart)
    }

}