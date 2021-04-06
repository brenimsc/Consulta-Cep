package com.example.minhaempresa.ui.buscacep.ui.activity.extension.ActivityExtension

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat

//esconde o teclado
fun Activity.escondeTeclado() {
    currentFocus?.let {
        val inputMethodManager = ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}