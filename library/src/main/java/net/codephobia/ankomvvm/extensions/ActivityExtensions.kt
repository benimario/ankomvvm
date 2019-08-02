package net.codephobia.ankomvvm.extensions

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import net.codephobia.ankomvvm.R
import org.jetbrains.anko.inputMethodManager
import org.jetbrains.anko.windowManager

/**
 * Created by benimario on 2018. 11. 30..
 */

fun Activity.hideKeyboard() = inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)


fun AppCompatActivity.replaceFragment(container: Int, fragment: Fragment,
                                      animEnter: Int? = null, animExit: Int? = null) =
    supportFragmentManager.let {
        val transaction = it.beginTransaction()
        if (animEnter != null && animExit != null)
            transaction.setCustomAnimations(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left)
        transaction.replace(container, fragment).commitNow()
    }