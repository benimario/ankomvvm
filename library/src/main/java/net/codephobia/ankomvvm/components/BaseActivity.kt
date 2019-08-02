package net.codephobia.ankomvvm.components

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.codephobia.ankomvvm.extensions.hideKeyboard
import net.codephobia.ankomvvm.lifecycle.BaseViewModel
import org.jetbrains.anko.AnkoLogger
import kotlin.reflect.KClass

/**
 * Created by benimario on 12/03/2019.
 */
@SuppressLint("Registered")
abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity(), AnkoLogger {

    abstract val viewModelType: KClass<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel().let {
            it.uiContextEvent.observe(this, Observer { event ->
                when(event.first) {
                    BaseViewModel.MESSAGE_HIDE_KEYBOARD -> hideKeyboard()
                    BaseViewModel.MESSAGE_FINISH_ACTIVITY -> finish()
                    BaseViewModel.MESSAGE_START_ACTIVITY_FOR_RESULT -> event.second?.let { map ->
                        map as Map<*, *>
                        startActivityForResult(map["intent"] as Intent, map["requestCode"] as Int)
                    }
                    else -> {}
                }
            })
        }
    }

    protected fun getViewModel(): V =
        ViewModelProviders.of(this).get(viewModelType.java)


}