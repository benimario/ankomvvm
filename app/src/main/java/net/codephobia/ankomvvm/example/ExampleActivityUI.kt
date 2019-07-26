package net.codephobia.ankomvvm.example

import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.textView

/**
 * Created by benimario on 26/07/2019.
 */
class ExampleActivityUI : AnkoComponent<ExampleActivity> {

    override fun createView(ui: AnkoContext<ExampleActivity>) = ui.linearLayout {
        textView("asdf")
    }

}