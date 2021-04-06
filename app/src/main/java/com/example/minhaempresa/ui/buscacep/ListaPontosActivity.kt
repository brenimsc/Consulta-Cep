package com.example.minhaempresa.ui.buscacep

import android.app.ActionBar
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.minhaempresa.ui.buscacep.ui.activity.adapter.ListaPontosAdapter
import com.example.minhaempresa.ui.buscacep.ui.activity.adapter.OnItemLongClickListener
import com.example.minhaempresa.ui.buscacep.ui.activity.dao.ReferenciaDAO
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Endereco
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Referencia
import kotlinx.android.synthetic.main.activity_lista_pontos.*

class ListaPontosActivity : AppCompatActivity() {

    private val dao = ReferenciaDAO(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pontos)
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.action_bar_layout)
        configuraLista()
    }


    private fun configuraLista(){
        val listaReferencias = dao.buscaPontos()
        dao.close()
        val adapter = ListaPontosAdapter(listaReferencias,this)
        val recyclerView = listaPontosRecyclerView
        recyclerView.adapter = adapter

        adapter.setOnItemLongClickListener(object : OnItemLongClickListener {
            override fun onItemLongClick(endereco: Endereco) {
                TODO("Not yet implemented")
            }

            override fun onItemLongClick(referencia: Referencia) {
                configuraDialogAbrirMapa(referencia)
            }
        })
    }

    private fun configuraDialogAbrirMapa(referencia: Referencia) {
        AlertDialog.Builder(this@ListaPontosActivity)
                .setTitle("Abrir com Google Maps?")
                .setPositiveButton("Sim", { _, _ ->
                    val intentMapa = Intent(Intent.ACTION_VIEW)
                    intentMapa.setData(Uri.parse("geo:0,0?q=" + "${referencia.rua}, ${referencia.cidade} ${referencia.estado}"))
                    startActivity(intentMapa)
                })
                .setNegativeButton("Não", null)
                .show()
    }
}