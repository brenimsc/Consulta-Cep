package com.example.minhaempresa.ui.buscacep

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minhaempresa.ui.buscacep.ui.activity.dao.ReferenciaDAO
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Endereco
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Referencia
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_formulario_ponto.*

class FormularioPontoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val dao = ReferenciaDAO(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_ponto)

        if(intent.hasExtra("endereco")){
            val jsonString = intent.getSerializableExtra("endereco") as String
            val gson = Gson()
            val endereco = gson.fromJson(jsonString, Endereco::class.java)

            ruaEdt.setText(endereco.logradouro)
            bairroEdt.setText(endereco.bairro)
            cidadeEdt.setText(endereco.localidade)
            estadoEdt.setText(endereco.uf)
        }

        buttonSalvaRef.setOnClickListener {
            val pontoReferencia = referenciaEdt.text.toString()
            val rua = ruaEdt.text.toString()
            val bairro = bairroEdt.text.toString()
            val cidade = cidadeEdt.text.toString()
            val estado = estadoEdt.text.toString()
            salvaPonto(pontoReferencia, rua, bairro, cidade, estado, dao)
            finish()
        }
    }

    private fun salvaPonto(pontoReferencia: String, rua: String, bairro: String, cidade: String, estado: String, dao: ReferenciaDAO) {
        val ponto = Referencia(pontoReferencia, rua, bairro, cidade, estado)
        dao.insere(ponto)
        dao.close()
    }
}