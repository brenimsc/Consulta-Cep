package com.example.minhaempresa.ui.buscacep.ui.activity.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Referencia

class ReferenciaDAO(context: Context) : SQLiteOpenHelper(context, "Referencia", null, 1){

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE Referencia (id INTEGER PRIMARY KEY,  ponto TEXT, rua TEXT, bairro TEXT, cidade TEXT, estado TEXT);"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql = "DROP TABLE IF EXISTS Referencia"
        db?.execSQL(sql)
        onCreate(db)
    }

    fun insere(referencia: Referencia){
        val db = writableDatabase
        val dados = preencheReferenciaBD(referencia)
        db.insert("Referencia", null, dados)
    }

    private fun preencheReferenciaBD(referencia: Referencia): ContentValues {
        val dados = ContentValues().apply {
            put("ponto", referencia.pontoReferencia)
            put("rua", referencia.rua)
            put("bairro", referencia.bairro)
            put("cidade", referencia.cidade)
            put("estado", referencia.estado)
        }
        return dados
    }

    fun buscaPontos(): MutableList<Referencia>{
        val sql = "SELECT * FROM Referencia;"
        val db = readableDatabase
        val cursor = db.rawQuery(sql, null)

        val referencias: MutableList<Referencia> = mutableListOf()
        while (cursor.moveToNext()){
            val ponto = cursor.getString(cursor.getColumnIndex("ponto"))
            val rua = cursor.getString(cursor.getColumnIndex("rua"))
            val bairro = cursor.getString(cursor.getColumnIndex("bairro"))
            val cidade = cursor.getString(cursor.getColumnIndex("cidade"))
            val estado = cursor.getString(cursor.getColumnIndex("estado"))
            val referencia = Referencia(ponto, rua, bairro, cidade, estado)
            referencias.add(referencia)
        }
        cursor.close()
        return referencias
    }

}