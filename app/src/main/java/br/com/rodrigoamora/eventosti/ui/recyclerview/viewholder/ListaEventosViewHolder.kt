package br.com.rodrigoamora.eventosti.ui.recyclerview.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigoamora.eventosti.R
import br.com.rodrigoamora.eventosti.model.Evento

class ListaEventosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    lateinit var tvDataIncio: TextView
    lateinit var tvDataFim: TextView
    lateinit var tvNomeEvento: TextView

    fun setValues(evento: Evento) {
        this.tvDataFim = itemView.findViewById(R.id.tv_data_fim_value)
        this.tvDataFim.text = evento.dataFim

        this.tvDataIncio = itemView.findViewById(R.id.tv_data_inicio_value)
        this.tvDataIncio.text = evento.dataInicio

        this.tvNomeEvento = itemView.findViewById(R.id.tv_nome_evento)
        this.tvNomeEvento.text = evento.nome
    }

}