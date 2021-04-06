package com.example.minhaempresa.ui.buscacep.ui.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minhaempresa.ui.buscacep.R
import com.example.minhaempresa.ui.buscacep.ui.activity.extension.ActivityExtension.StringExtension.verificaTamanho
import com.example.minhaempresa.ui.buscacep.ui.activity.model.Endereco
import kotlinx.android.synthetic.main.item_cep.view.*

class ListaCepAdapter(private val enderecos: List<Endereco>,
                      private val context: Context): RecyclerView.Adapter<ListaCepAdapter.EnderecoViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null
    private var onItemLongClickListener: OnItemLongClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnderecoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cep, parent, false)
        return EnderecoViewHolder(view)
    }


    override fun onBindViewHolder(holder: EnderecoViewHolder, position: Int) {
        val endereco = enderecos[position]
        holder?.let {
            it.vincula(endereco)
            }
    }

    override fun getItemCount(): Int {
        return enderecos.size
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener){
        this.onItemClickListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener
    }


    fun getItem(position: Int) : Endereco{
        return enderecos[position]
    }




    inner class EnderecoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun vincula(endereco: Endereco) {
            val enderecoRecebido = endereco
            val rua = itemView.rua
            val bairro = itemView.bairroValor
            val cidade = itemView.cidadeValor
            val estado = itemView.estadoValor
            val cep = itemView.cepValor
            val ddd = itemView.dddValor

            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    onItemClickListener?.onItemClick(enderecoRecebido)
                }
            })

            itemView.setOnLongClickListener(object : View.OnLongClickListener {
                override fun onLongClick(v: View?): Boolean {
                    onItemLongClickListener?.onItemLongClick(enderecoRecebido)
                    return true
                }
            })

            if (endereco != null) {
                estado.text = enderecoRecebido.uf
                cep.text = enderecoRecebido.cep
                ddd.text = enderecoRecebido.ddd
                if (endereco.erro == null) {  //caso endereco for vazio e fizer isso aqui vai quebrar
                    rua.text = enderecoRecebido.logradouro.verificaTamanho()
                    bairro.text = enderecoRecebido.bairro.verificaTamanho()
                    cidade.text = enderecoRecebido.localidade.verificaTamanho()
                } else { //verificação para funcionar o processo de bindar um endereco sem nada quando não encontra
                    rua.text = enderecoRecebido.logradouro
                    bairro.text = enderecoRecebido.bairro
                    cidade.text = enderecoRecebido.localidade
                }
            }
        }


}
}
