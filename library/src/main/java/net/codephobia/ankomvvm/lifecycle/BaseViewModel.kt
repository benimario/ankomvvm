package net.codephobia.ankomvvm.lifecycle

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.location.LocationListener
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.locationManager
import org.jetbrains.anko.toast
import java.io.File

/**
 * Created by benimario on 14/03/2019.
 */
open class BaseViewModel(app: Application) : AndroidViewModel(app), AnkoLogger {

    val app: Application get() = getApplication()

    val uiContextEvent = MutableLiveData<Pair<Int, Any?>>(-1 to null)

    fun toast(message: String) = getApplication<Application>().toast(message)

    fun toast(messageRes: Int) = getApplication<Application>().toast(messageRes)

    fun getContent(uri: Uri?): File? = uri?.let {
        app.contentResolver.openInputStream(uri)?.use { input ->
            val file = File.createTempFile("IMG_${System.currentTimeMillis()}", ".jpg", app.cacheDir)
            file.outputStream().use { output ->
                input.copyTo(output)
                return file
            }
        }
        return null
    }

    fun hideKeyboard() {
        uiContextEvent.value = MESSAGE_HIDE_KEYBOARD to null
    }

    fun finishActivity() {
        uiContextEvent.value = MESSAGE_FINISH_ACTIVITY to null
    }

    inline fun <reified T : Activity> startActivity(intentProperties: (Intent.() -> Unit) = {}) {
        uiContextEvent.value = MESSAGE_START_ACTIVITY to
            Intent(app, T::class.java).apply {
                intentProperties()
            }
    }

    inline fun <reified T : Activity> startActivityAndFinish(intentProperties: (Intent.() -> Unit) = {}) {
        startActivity<T>(intentProperties)
        uiContextEvent.value = MESSAGE_FINISH_ACTIVITY to null
    }

    fun startActivityForResult(intent: Intent, requestCode: Int) {
        uiContextEvent.value = MESSAGE_START_ACTIVITY_FOR_RESULT to
            mapOf("intent" to intent, "requestCode" to requestCode)
    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    @SuppressLint("MissingPermission")
    @Throws(SecurityException::class)
    fun requestLocationUpdates(
        provider: String, minTime: Long, minDistance: Float,
        listener: LocationListener
    ) {
        app.locationManager.requestLocationUpdates(provider, minTime, minDistance, listener)
    }


    companion object {
        const val MESSAGE_HIDE_KEYBOARD = 0
        const val MESSAGE_FINISH_ACTIVITY = 1
        const val MESSAGE_START_ACTIVITY = 2
        const val MESSAGE_START_ACTIVITY_FOR_RESULT = 3
    }

}