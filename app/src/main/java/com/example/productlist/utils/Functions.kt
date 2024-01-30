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

fun Double.isNotZero(): Boolean {
    return this != 0.0
}
fun Int.isNotZero(): Boolean {
    return this != 0
}



/*
    Estas dos funciones siguientes es para convertir los strings de las entradas de texto a
    los datos requeridos para el producto.
    De no hacerlo así, al borrar alguno de los valores enteros o decimales completos salta una
    excepción java.lang.NumberFormatException: empty String
 */
fun parseDouble(strNumber: String?): Double {
    return if (!strNumber.isNullOrEmpty()) {
        try {
            strNumber.toDouble()
        } catch (e: java.lang.Exception) {
            (-1).toDouble()
        }
    } else 0.0
}

fun parseInt(strNumber: String?): Int {
    return if (!strNumber.isNullOrEmpty()) {
        try {
            strNumber.toInt()
        } catch (e: java.lang.Exception) {
            (-1)
        }
    } else 0
}

