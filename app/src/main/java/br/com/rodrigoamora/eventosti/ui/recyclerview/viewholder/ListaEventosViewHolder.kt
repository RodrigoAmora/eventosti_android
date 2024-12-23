package br.com.rodrigoamora.eventosti.ui.recyclerview.viewholder

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigoamora.eventosti.R
import br.com.rodrigoamora.eventosti.model.Evento

class ListaEventosViewHolder(itemView: View,
                             private val context: Context) : RecyclerView.ViewHolder(itemView) {

    lateinit var tvDataEvento: TextView
    lateinit var tvNomeEvento: TextView

    fun setValues(evento: Evento) {
        this.tvDataEvento = itemView.findViewById(R.id.tv_data_evento)
        this.tvDataEvento.text = context.getString(R.string.tv_data_evento, evento.formatarData())

        this.tvNomeEvento = itemView.findViewById(R.id.tv_nome_evento)
        this.tvNomeEvento.text = evento.nome
    }

}