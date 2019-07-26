package net.codephobia.ankomvvm.extensions

import android.widget.TextView
import androidx.core.text.HtmlCompat

/**
 * Created by benimario on 2018. 11. 30..
 */

fun TextView.html(resource: Int) {
    text = HtmlCompat.fromHtml(context.getString(resource), HtmlCompat.FROM_HTML_MODE_LEGACY)
}