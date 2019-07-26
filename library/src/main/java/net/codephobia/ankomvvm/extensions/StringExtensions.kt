package net.codephobia.ankomvvm.extensions

import android.util.Patterns

/**
 * Created by benimario on 2018. 10. 16..
 */

fun String.isValidEmail() = this.isNotEmpty()
    && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.ifValidEmail(validCallback: (String) -> Unit) = {
    if (isValidEmail()) {
        validCallback(this)
    }
}

inline fun String.ifValidEmail(validCallback: (String) -> Unit,
                        errorCallback: (String) -> Unit) =
    if (isValidEmail()) {
        validCallback(this)
    } else {
        errorCallback(this)
    }