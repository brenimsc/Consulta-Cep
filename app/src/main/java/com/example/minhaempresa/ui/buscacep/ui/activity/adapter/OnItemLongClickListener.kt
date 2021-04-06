package com.example.minhaempresa.ui.buscacep.ui.activity.adapter

import com.example.minhaempresa.ui.buscacep.ui.activity.model.Endereco
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Referencia

interface OnItemLongClickListener {
    fun onItemLongClick(endereco: Endereco)
    fun onItemLongClick(referencia: Referencia)
}