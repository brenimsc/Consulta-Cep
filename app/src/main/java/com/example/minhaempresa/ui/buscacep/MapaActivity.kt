package com.example.minhaempresa.ui.buscacep

import android.app.ActionBar
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minhaempresa.ui.buscacep.ui.activity.MapaFragment

class MapaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa);
        supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        supportActionBar?.setCustomView(R.layout.action_bar_map)

        val manager = supportFragmentManager
        val tx = manager.beginTransaction()
        tx.replace(R.id.frame_mapa, MapaFragment())
        tx.commit()
    }

}