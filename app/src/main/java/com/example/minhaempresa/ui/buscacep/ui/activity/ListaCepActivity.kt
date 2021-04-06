package com.example.minhaempresa.ui.buscacep.ui.activity

import android.app.ActionBar
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.minhaempresa.ui.buscacep.FormularioPontoActivity
import com.example.minhaempresa.ui.buscacep.ListaPontosActivity
import com.example.minhaempresa.ui.buscacep.MapaActivity
import com.example.minhaempresa.ui.buscacep.R
import com.example.minhaempresa.ui.buscacep.ui.activity.extension.ActivityExtension.escondeTeclado
import com.example.minhaempresa.ui.buscacep.ui.activity.adapter.ListaCepAdapter
import com.example.minhaempresa.ui.buscacep.ui.activity.adapter.OnItemClickListener
import com.example.minhaempresa.ui.buscacep.ui.activity.adapter.OnItemLongClickListener
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Cep
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Endereco
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Referencia
import com.example.minhaempresa.ui.buscacep.ui.activity.service.CepWebClient
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_lista_cep.*
import kotlinx.android.synthetic.main.item_cep.*

class ListaCepActivity : AppCompatActivity() {

    private val dialog by lazy {
        ProgressDialog.show(this, "Aguarde", "Abrindo mapa...", true, true)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_cep)
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.action_bar_layout)
        val recyclerView = listaCepRecyclerView

        buscaButton.setOnClickListener {
            this.escondeTeclado()
            val cepString = cepEdt.text.toString()
            if (!cepString.isEmpty()) {
                val cep = Cep(cepString)
                CepWebClient(this).busca(cep, { endereco: Endereco ->
                    configuraLista(endereco, recyclerView)
                })
            } else{
                cepEdt.error = "Preencha o campo"
                Toast.makeText(this, "Digite algum CEP", Toast.LENGTH_SHORT).show()
            }
        }

        duvidaCepFab.setOnClickListener({
            val abreNaoSeiCep = Intent(this, FormularioActivity::class.java)
            startActivity(abreNaoSeiCep)
        })


    }

    override fun onResume() {
        super.onResume()
        dialog.dismiss()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_cep, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuLista) {
            val vaiParaListaFavoritos = Intent(this, ListaPontosActivity::class.java)
            startActivity(vaiParaListaFavoritos)
        }
        if(item.itemId == R.id.menuMapa){
            dialog.show()
            val vaiParaMapa = Intent(this, MapaActivity::class.java)
            startActivity(vaiParaMapa)
        }
        return super.onOptionsItemSelected(item)
    }



    private fun configuraLista(endereco: Endereco, recyclerView : RecyclerView){
        val listaEnderecos = listOf(endereco)
        val adapter = ListaCepAdapter(listaEnderecos, this)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : OnItemClickListener {
            override fun onItemClick(endereco: Endereco) {
                if (endereco.erro!= null || endereco.cep.isEmpty()) {
                    Toast.makeText(this@ListaCepActivity, "Não há como salvar essa referencia", Toast.LENGTH_SHORT).show()
                }
                else {
                val intent = Intent(this@ListaCepActivity, FormularioPontoActivity::class.java)
                val enderecoEmJson: String = converteEnderecoEmJson(endereco)
                intent.putExtra("endereco", enderecoEmJson)
                startActivity(intent)
                }
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
        AlertDialog.Builder(this@ListaCepActivity)
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


}