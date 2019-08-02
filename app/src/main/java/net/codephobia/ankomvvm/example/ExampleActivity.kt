package net.codephobia.ankomvvm.example

import android.os.Bundle
import net.codephobia.ankomvvm.components.BaseActivity
import org.jetbrains.anko.setContentView

/**
 * Created by benimario on 26/07/2019.
 */
class ExampleActivity : BaseActivity<ExampleActivityViewModel>() {

    override val viewModelType = ExampleActivityViewModel::class

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ExampleActivityUI(getViewModel()).setContentView(this)
    }

}