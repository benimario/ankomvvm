package net.codephobia.ankomvvm.components

import android.location.LocationListener
import android.net.Uri
import android.widget.Toast
import java.io.File

/**
 * Created by benimario on 14/03/2019.
 */
interface BaseNavigator {

    fun getContent(uri: Uri?) : File?
    fun showToast(messageRes: Int) : Toast
    fun showToast(message: String) : Toast
    fun showProgress(): Unit?
    fun hideProgress(): Unit?
    fun hideSoftKeyboard(): Boolean?
    fun requestLocationUpdates(provider: String, minTime: Long, minDistance: Float,
                               listener: LocationListener)

}