package br.com.rodrigoamora.eventosti.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.rodrigoamora.eventosti.R
import br.com.rodrigoamora.eventosti.model.Evento
import br.com.rodrigoamora.eventosti.ui.recyclerview.callback.EventoDiffCallback
import br.com.rodrigoamora.eventosti.ui.recyclerview.viewholder.ListaEventosViewHolder

class ListaEventosAdapter(
    private val context: Context,
    private val eventos: MutableList<Evento> = mutableListOf(),
    var whenSelected: (evento: Evento) -> Unit = {}
) : RecyclerView.Adapter<ListaEventosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaEventosViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.adapter_lista_eventos, parent, false)
        return ListaEventosViewHolder(view)
    }

    override fun getItemCount(): Int = this.eventos.size

    override fun onBindViewHolder(holder: ListaEventosViewHolder, position: Int) {
        val evento = this.eventos[position]

        holder.setValues(evento)
        holder.itemView.setOnClickListener {
            whenSelected(evento)
        }
    }

    fun update(eventos: List<Evento>) {
        val diffCallback = EventoDiffCallback(this.eventos, eventos)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.eventos.clear()
        this.eventos.addAll(eventos)

        diffResult.dispatchUpdatesTo(this)
    }

    fun replaceAll(eventos: List<Evento>) {
        this.eventos.clear()
        this.eventos.addAll(eventos)
        notifyDataSetChanged()
    }

}
