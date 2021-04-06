package com.example.minhaempresa.ui.buscacep.ui.activity.extension.ActivityExtension.StringExtension

fun String.verificaTamanho(): String{
    var palavra = this
    if (palavra.length > 29){
        palavra =  "${palavra.substring(0,29)}..."
    }
    return palavra
}