package com.example.productlist.utils

import android.widget.TextView
import androidx.core.widget.doAfterTextChanged

inline fun TextView.onTextChanged(
    crossinline action: (String) -> Unit,
) {
    doAfterTextChanged {
        if (hasFocus()) {
            action(it.toString())
        }
    }
}