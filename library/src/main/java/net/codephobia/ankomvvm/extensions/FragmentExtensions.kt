package net.codephobia.ankomvvm.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import org.jetbrains.anko.inputMethodManager

/**
 * Created by benimario on 2018. 11. 30..
 */

fun Fragment.hideKeyboard() = context
    ?.inputMethodManager
    ?.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)


fun Fragment.replaceFragment(container: Int, fragment: Fragment,
                             animEnter: Int? = null, animExit: Int? = null) =
    (context as? FragmentActivity)?.supportFragmentManager?.let {
        val transaction = it.beginTransaction()
        if (animEnter != null && animExit != null)
            transaction.setCustomAnimations(animEnter, animExit)
        transaction.replace(container, fragment).commitNow()
    }