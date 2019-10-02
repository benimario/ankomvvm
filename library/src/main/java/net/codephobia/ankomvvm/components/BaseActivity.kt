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
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import kotlin.reflect.KClass

/**
 * Created by benimario on 12/03/2019.
 */
@SuppressLint("Registered")
abstract class BaseActivity<V : BaseViewModel> : AppCompatActivity(), AnkoLogger {

    abstract val viewModelType: KClass<V>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUIContextEventObserver(getViewModel())
    }

    protected fun setUIContextEventObserver(viewModel: BaseViewModel) {
        viewModel.let {
            it.uiContextEvent.observe(this, Observer { event ->
                when(event.first) {
                    BaseViewModel.MESSAGE_HIDE_KEYBOARD -> hideKeyboard()
                    BaseViewModel.MESSAGE_FINISH_ACTIVITY -> event.second?.let { map ->
                        map as Map<*, *>
                        val intent = map["intent"] as? Intent
                        val resultCode = map["resultCode"] as Int
                        intent?.let {
                            setResult(resultCode, intent)
                            finish()
                        } ?: run {
                            setResult(resultCode)
                            finish()
                        }
                    } ?: finish()
                    BaseViewModel.MESSAGE_START_ACTIVITY_FOR_RESULT -> event.second?.let { map ->
                        map as Map<*, *>
                        startActivityForResult(map["intent"] as Intent, map["requestCode"] as Int)
                    }
                    BaseViewModel.MESSAGE_START_ACTIVITY -> event.second?.let { intent ->
                        intent as Intent
                        startActivity(intent)
                    }
                    BaseViewModel.MESSAGE_CONFIRM_DIALOG -> event.second?.let { map ->
                        map as Map<*, *>
                        alert(map["message"] as String) {
                            yesButton { (map["callback"] as () -> Unit)() }
                        }.show()
                    }
                    else -> {}
                }
            })
        }
    }

    protected fun getViewModel(): V =
        ViewModelProviders.of(this).get(viewModelType.java)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        getViewModel().onActivityResult(requestCode, resultCode, data)
    }

}