package net.codephobia.ankomvvm.components

import android.annotation.SuppressLint
import android.location.LocationListener
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import net.codephobia.ankomvvm.extensions.hideKeyboard
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.locationManager
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.toast
import java.io.File
import kotlin.reflect.KClass

/**
 * Created by benimario on 12/03/2019.
 */
@SuppressLint("Registered")
abstract class BaseFragment<T : BaseNavigator, V : BaseViewModel<T>> : BaseNavigator, Fragment(), AnkoLogger {

    abstract val defaultNavigator: T?
    abstract val viewModelType: KClass<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel().apply {
            navigator = defaultNavigator
        }
    }

    protected fun getViewModel(): V =
        ViewModelProviders.of(this).get(viewModelType.java)

    override fun getContent(uri: Uri?): File? = uri?.let {
        ctx.contentResolver.openInputStream(uri)?.use { input ->
            val file = File.createTempFile("IMG_${System.currentTimeMillis()}", ".jpg", ctx.cacheDir)
            file.outputStream().use { output ->
                input.copyTo(output)
                return file
            }
        }
        return null
    }

    override fun showToast(messageRes: Int) = ctx.toast(messageRes)

    override fun showToast(message: String) = ctx.toast(message)

    override fun hideSoftKeyboard() = hideKeyboard()

    @Throws(SecurityException::class)
    override fun requestLocationUpdates(provider: String, minTime: Long, minDistance: Float,
                                        listener: LocationListener) {
        activity?.locationManager?.requestLocationUpdates(provider, minTime, minDistance, listener)
    }

}