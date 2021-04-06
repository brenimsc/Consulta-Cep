package com.example.minhaempresa.ui.buscacep.ui.activity.retrofit

import com.example.minhaempresa.ui.buscacep.ui.activity.service.CepService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CepRetrofit {

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val urlBase = "https://viacep.com.br/ws/"

        private val retrofit = Retrofit.Builder()
                .baseUrl(urlBase)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()


        fun getCepService() = retrofit.create(CepService::class.java)

}




