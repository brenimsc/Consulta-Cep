package com.example.minhaempresa.ui.buscacep.ui.activity.service

import android.database.Observable
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Endereco
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path

interface CepService {
    @GET("{cep}/json")
    fun listaCep(@Path("cep") cep : String) : Call<Endereco>

    @GET("{uf}/{cidade}/{endereco}/json")
    fun listaEnderecos(@Path("uf") uf: String,
                       @Path("cidade") cidade: String,
                       @Path("endereco") endereco: String) : Call <List<Endereco>>
}
