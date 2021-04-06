package com.example.minhaempresa.ui.buscacep.ui.activity

import android.location.Geocoder
import android.os.Bundle
import com.example.minhaempresa.ui.buscacep.ui.activity.dao.ReferenciaDAO
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaFragment : SupportMapFragment(), OnMapReadyCallback {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val posicaoInicial = pegaCoordenadaDoEndereco("Garca Sp")
        if (posicaoInicial!=null) {
            focaMapaNoEndereco(posicaoInicial, googleMap)
        }

        val referenciaDAO = context?.let { ReferenciaDAO(it) }
        val lista = referenciaDAO?.buscaPontos()
        if (lista != null) {
            for (referencia in lista){
                val coordenada = pegaCoordenadaDoEndereco(referencia.rua + ", "+referencia.cidade)
                if (coordenada!=null){
                    val marcador = MarkerOptions()
                    marcador.position(coordenada)
                    marcador.title(referencia.pontoReferencia)
                    googleMap?.addMarker(marcador)
                }
            }
            referenciaDAO.close()
        }

    }

    private fun focaMapaNoEndereco(posicaoInicial: LatLng?, googleMap: GoogleMap?) {
        val update = CameraUpdateFactory.newLatLngZoom(posicaoInicial, 14F)
        googleMap?.moveCamera(update)
    }

    private fun pegaCoordenadaDoEndereco(endereco: String): LatLng? {
        val geocoder = Geocoder(context)
        val resultados = geocoder.getFromLocationName(endereco, 1)
        if (!resultados.isEmpty()) {
            val posicao = LatLng(resultados.get(0).latitude, resultados.get(0).longitude)
            return posicao
        }
        return null
    }
}