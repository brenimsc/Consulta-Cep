package com.example.minhaempresa.ui.buscacep.ui.activity

import android.app.ActionBar
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.minhaempresa.ui.buscacep.FormularioPontoActivity
import com.example.minhaempresa.ui.buscacep.R
import com.example.minhaempresa.ui.buscacep.ui.activity.extension.ActivityExtension.escondeTeclado
import com.example.minhaempresa.ui.buscacep.ui.activity.adapter.ListaCepAdapter
import com.example.minhaempresa.ui.buscacep.ui.activity.adapter.OnItemClickListener
import com.example.minhaempresa.ui.buscacep.ui.activity.adapter.OnItemLongClickListener
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Endereco
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Referencia
import com.example.minhaempresa.ui.buscacep.ui.activity.service.CepWebClient
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_formulario.*
import kotlinx.android.synthetic.main.activity_formulario.buscaButton

class FormularioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.action_bar_layout)

        buscaButton.setOnClickListener {
            this@FormularioActivity.escondeTeclado()
            val (uf, cidade, rua) = buscaInfoTeclado()
            CepWebClient(this).listaEnderecos(uf, cidade, rua, { listaEndereco ->
                if (listaEndereco.size > 0)
                    configuraLista(listaEndereco)
                if (listaEndereco.size ==0 || listaEndereco[0].logradouro.isEmpty()){
                    Toast.makeText(this, "CEP não encontra para o endereço", Toast.LENGTH_SHORT).show()
                    configuraListaVazia()
                }
            })
        }
    }

    private fun buscaInfoTeclado(): Triple<String, String, String> {
        val uf = estadoEdt.text.toString()
        val cidade = cidadeEdt.text.toString()
        val rua = logradouroEdt.text.toString()
        return Triple(uf, cidade, rua)
    }

    private fun configuraLista(listaEndereco: List<Endereco>) {
        val listaEnderecos = listaEndereco
        val recyclerView = listaRecyclerViewFormulario
        val adapter = ListaCepAdapter(listaEnderecos, this)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(endereco: Endereco) {
                val intent = Intent(this@FormularioActivity, FormularioPontoActivity::class.java)
                val enderecoEmJson: String = converteEnderecoEmJson(endereco)
                intent.putExtra("endereco", enderecoEmJson)
                startActivity(intent)
            }

        })

        adapter.setOnItemLongClickListener(object : OnItemLongClickListener {
            override fun onItemLongClick(endereco: Endereco) {
                configuraDialogAbrirMapa(endereco)
            }

            override fun onItemLongClick(referencia: Referencia) {
                TODO("Not yet implemented")
            }

        })


    }

    private fun configuraDialogAbrirMapa(endereco: Endereco) {
        AlertDialog.Builder(this@FormularioActivity)
                .setTitle("Abrir com Google Maps?")
                .setPositiveButton("Sim", { _, _ ->
                    val intentMapa = Intent(Intent.ACTION_VIEW)
                    intentMapa.setData(Uri.parse("geo:0,0?q=" + "${endereco.logradouro}, ${endereco.localidade} ${endereco.uf}"))
                    startActivity(intentMapa)
                })
                .setNegativeButton("Não", null)
                .show()
    }

    private fun converteEnderecoEmJson(endereco: Endereco): String {
        val formatoJson = GsonBuilder().setPrettyPrinting().create()
        val enderecoEmJson: String = formatoJson.toJson(endereco)
        return enderecoEmJson
    }

    private fun configuraListaVazia() {
        val listaEnderecos: List<Endereco> = listOf()
        val recyclerView = listaRecyclerViewFormulario
        val adapter = ListaCepAdapter(listaEnderecos, this)
        recyclerView.adapter = adapter

    }
}