package net.codephobia.ankomvvm.lifecycle

import androidx.lifecycle.ViewModel
import net.codephobia.ankomvvm.components.BaseNavigator
import org.jetbrains.anko.AnkoLogger
import java.lang.ref.WeakReference

/**
 * Created by benimario on 14/03/2019.
 */
open class BaseViewModel<T : BaseNavigator> : ViewModel(), AnkoLogger {

    private var navigatorRef: WeakReference<T>? = null

    var navigator: T?
        get() = navigatorRef?.get()
        set(value) {
            value?.let { navigatorRef = WeakReference(value) }
        }

}