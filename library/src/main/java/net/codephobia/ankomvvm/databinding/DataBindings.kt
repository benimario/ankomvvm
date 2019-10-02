package net.codephobia.ankomvvm.databinding

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange

/**
 * Created by benimario on 15/03/2019.
 */

fun View.bindVisibility(
    lifecycleOwner: LifecycleOwner,
    visibility: MutableLiveData<Boolean>?
) = visibility?.observe(lifecycleOwner, Observer {
    this.visibility = if (it) View.VISIBLE else View.GONE
})

fun <T> View.bindVisibility(
    lifecycleOwner: LifecycleOwner,
    data: LiveData<T>,
    visibilityCallback: (T) -> Boolean
) = data.observe(lifecycleOwner, Observer {
    this.visibility = if (visibilityCallback(it)) View.VISIBLE else View.GONE
})

fun EditText.bindString(
    lifecycleOwner: LifecycleOwner,
    string: MutableLiveData<String>?,
    onTextChanged: ((editText: EditText) -> Unit)? = null
) = string?.let {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString() != string.value) {
                string.value = s.toString()
                setSelection(start + count)
                onTextChanged?.invoke(this@bindString)
            }
        }
    })

    string.observe(lifecycleOwner, Observer { str ->
        if (str != text.toString()) {
            setText(str)
            setSelection(str.length)
        }
    })

    setText(string.value)
}

fun TextView.bindString(
    lifecycleOwner: LifecycleOwner,
    string: MutableLiveData<String>?
) = string?.let {
    string.observe(lifecycleOwner, Observer { str ->
        if (str != text.toString()) {
            text = str.toString()
        }
    })

    text = string.value.toString()
}

fun Button.bindEnabled(
    lifecycleOwner: LifecycleOwner,
    boolean: MutableLiveData<Boolean>?,
    onStatusChange: ((Button, Boolean) -> Unit)? = null
) = boolean?.let {
    boolean.observe(lifecycleOwner, Observer { enabled ->
        isEnabled = enabled
        onStatusChange?.invoke(this, enabled)
    })
}

fun Switch.bindChecked(
    lifecycleOwner: LifecycleOwner,
    boolean: MutableLiveData<Boolean>?,
    onStatusChange: ((Switch, Boolean) -> Unit)? = null
) = boolean?.let {
    boolean.observe(lifecycleOwner, Observer { checked ->
        if (isChecked != checked) {
            isChecked = checked
        }
    })

    onCheckedChange { buttonView, isChecked ->
        if (isChecked != boolean.value) {
            boolean.value = isChecked
            onStatusChange?.invoke(this@bindChecked, isChecked)
        }
    }
}

fun ImageView.bindUrl(
    lifecycleOwner: LifecycleOwner,
    url: MutableLiveData<String?>,
    onUrlChange: ((String?) -> Unit)? = null
) {
    url.observe(lifecycleOwner, Observer {
        it?.let { onUrlChange?.invoke(it) }
    })
}

fun <T> MutableLiveData<MutableList<T>>.add(item: T) {
    this.value?.add(item)
    this.value = this.value
}

fun <T> RecyclerView.bindItem(
    owner: LifecycleOwner,
    data: MutableLiveData<MutableList<T>>,
    callback: (MutableList<T>) -> Unit
) {
    data.observe(owner, Observer { items ->
        if(items.isNullOrEmpty().not()) {
            callback(items)
        }
    })
}

fun <T> RecyclerView.bindPagedList(
    owner: LifecycleOwner,
    adapter: PagedListAdapter<T, RecyclerView.ViewHolder>,
    data: LiveData<PagedList<T>>
) {
    this.adapter = adapter
    data.observe(owner, Observer {
        try {
            adapter.submitList(it)
        } catch (e: Exception) {
            Log.e("AnkoMVVM", "bindPagedList() error", e)
        }
    })
}

fun <T> Spinner.bindStringEntries(
    lifecycleOwner: LifecycleOwner,
    items: MutableLiveData<List<T>>
) {
    items.observe(lifecycleOwner, Observer {
        adapter = ArrayAdapter(
            lifecycleOwner as Context,
            android.R.layout.simple_spinner_item,
            items.value ?: listOf()
        )
    })
}

fun <T> ViewPager.bindItem(
    owner: LifecycleOwner,
    data: MutableLiveData<MutableList<T>>,
    callback: (MutableList<T>) -> Unit
) {
    data.observe(owner, Observer { items ->
        if(items.isNullOrEmpty().not()) {
            callback(items)
        }
    })
}

fun <T> MutableLiveData<MutableList<T>>.addAll(item: List<T>) {
    this.value?.addAll(item)
    this.value = this.value
}
