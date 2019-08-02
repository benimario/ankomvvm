package net.codephobia.ankomvvm.example

import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * Created by benimario on 26/07/2019.
 */
class ExampleActivityUI(val viewModel: ExampleActivityViewModel) : AnkoComponent<ExampleActivity> {

    override fun createView(ui: AnkoContext<ExampleActivity>) = ui.linearLayout {
        textView("asdf")
        button("button") {
            onClick { viewModel.hideKeyboard() }
        }
    }

}