package com.example.minhaempresa.ui.buscacep.ui.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minhaempresa.ui.buscacep.ListaPontosActivity
import com.example.minhaempresa.ui.buscacep.R
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Referencia
import kotlinx.android.synthetic.main.item_ponto_referencia.view.*

class ListaPontosAdapter(private val referencias: MutableList<Referencia>,
                         private val context: Context) : RecyclerView.Adapter<ListaPontosAdapter.ReferenciaViewHolder>() {
    private var onItemLongClickListener: OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferenciaViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.item_ponto_referencia, parent, false)
        return ReferenciaViewHolder(viewCriada)
    }

    override fun onBindViewHolder(holder: ReferenciaViewHolder, position: Int) {
        val referencia = referencias[position]
        holder?.let {
            it.vincula(referencia)
        }

    }

    override fun getItemCount(): Int {
        return referencias.size
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener
    }



    inner class ReferenciaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun vincula(referencia: Referencia){
            val referenciaRecebida = referencia
            val nomeReferencia = itemView.nomeReferencia
            val corpoReferencia = itemView.valorCampo

            val nomePonto = referenciaRecebida.pontoReferencia
            val rua = referenciaRecebida.rua
            val bairro = referenciaRecebida.bairro
            val cidade = referenciaRecebida.cidade
            val estado = referenciaRecebida.estado

            nomeReferencia.text = nomePonto
            corpoReferencia.text = "$rua - $bairro, $cidade - $estado"

            itemView.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    onItemLongClickListener?.onItemLongClick(referencia)
                    return true
                }
            })
        }
    }
}
