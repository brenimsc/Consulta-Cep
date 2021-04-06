package com.example.minhaempresa.ui.buscacep.ui.activity.service

import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Cep
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Endereco
import com.example.minhaempresa.ui.buscacep.ui.activity.retrofit.CepRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CepWebClient(private val context: Context) {
    private val service = CepRetrofit().getCepService()

    fun busca(cep: Cep, lista :(endereco :Endereco) -> Unit){
        val call = service.listaCep(cep.cep)
        buscaCepApi(call, lista)
    }

    private fun buscaCepApi(call: Call<Endereco>, lista: (endereco: Endereco) -> Unit) {
        call.enqueue(object : Callback<Endereco> {
            val dialog = ProgressDialog.show(context, "Aguarde", "Pesquisando...", true, true)
            override fun onResponse(call: Call<Endereco>?, response: Response<Endereco>?) {
                response?.let {
                    if (it.isSuccessful) {
                        it.body()?.let {
                            if (it.erro == null) {
                                val enderecos: Endereco = it
                                dialog.dismiss()
                                lista(enderecos)
                            } else {
                                val enderecos: Endereco = it
                                dialog.dismiss()
                                lista(enderecos)
                                mostraErroCep()
                            }
                        }
                    } else {
                        val endereco = response.body()
                        endereco?.let { it1: Endereco -> lista(it1) }
                        mostraErroFormato() //caso seja um code diferente de 200
                        dialog.dismiss()
                    }
                }
            }

            override fun onFailure(call: Call<Endereco>?, t: Throwable) {
                val enderecos = Endereco("", "", "", "", "", ""
                        , Double.MIN_VALUE, "", "", Double.MIN_VALUE, "")
                lista(enderecos)
                mostraErro()
                dialog.dismiss()
            }

        })
    }


    fun listaEnderecos(uf: String, cidade: String, endereco : String, lista: (listaEndereco: List<Endereco>) -> Unit){
        val call = service.listaEnderecos(uf, cidade, endereco)
        buscaEnderecosApi(call, lista)
    }

    private fun buscaEnderecosApi(call: Call<List<Endereco>>, lista: (listaEndereco: List<Endereco>) -> Unit) {
        call.enqueue(object : Callback<List<Endereco>> {
            val dialog = ProgressDialog.show(context, "Aguarde", "Pesquisando...", true, true)
            override fun onResponse(call: Call<List<Endereco>>, response: Response<List<Endereco>>) {
                response?.let {
                    if (it.isSuccessful) {
                        it.body()?.let {
                            val listaEnderecos: List<Endereco> = it
                            lista(listaEnderecos)
                            dialog.dismiss()
                        }
                    } else {
                        dialog.dismiss()
                        mostraErroCep()
                    }
                }
            }

            override fun onFailure(call: Call<List<Endereco>>, t: Throwable) {
                mostraErroEnd()
                dialog.dismiss()
            }
        })
    }

    private fun mostraErro(){
        Toast.makeText(context, "Não foi possivel realizar a consulta", Toast.LENGTH_LONG).show()
    }
    private fun mostraErroFormato(){
        Toast.makeText(context, "Formato de CEP inválido", Toast.LENGTH_LONG).show()
    }

    private fun mostraErroCep(){
        Toast.makeText(context, "Não foi encontrado este endereço", Toast.LENGTH_LONG).show()
    }

    private fun mostraErroEnd(){
        Toast.makeText(context, "Endereço invalido", Toast.LENGTH_LONG).show()
    }
}

