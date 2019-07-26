package net.codephobia.ankomvvm.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by benimario on 31/12/2018.
 */

fun Date.addDays(days: Int) = Calendar.getInstance().let {
    it.time = this
    it.add(Calendar.DATE, days)
    it.time
}

fun Date.format(pattern: String, locale: Locale = Locale.getDefault()) =
    SimpleDateFormat(pattern, locale).format(this)