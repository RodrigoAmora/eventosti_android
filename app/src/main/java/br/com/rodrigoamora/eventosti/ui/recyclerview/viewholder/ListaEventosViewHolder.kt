package br.com.rodrigoamora.eventosti.ui.recyclerview.viewholder

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigoamora.eventosti.R
import br.com.rodrigoamora.eventosti.model.Evento

class ListaEventosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var tvNameCharacter: TextView

    fun setValues(context: Context, evento: Evento) {
        tvNameCharacter = itemView.findViewById(R.id.tv_nome_evento)
        tvNameCharacter.text = evento.nome
    }

}