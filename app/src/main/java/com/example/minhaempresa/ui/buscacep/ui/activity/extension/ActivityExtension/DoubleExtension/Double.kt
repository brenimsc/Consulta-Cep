package com.example.minhaempresa.ui.buscacep.ui.activity.extension.ActivityExtension.DoubleExtension

fun Double.formataNumero(): String{
    var numero = this.toString()
    if (numero.contains(".0")){
        numero = numero.substring(0, (numero.length-2))
        return numero
    }
    return numero
}