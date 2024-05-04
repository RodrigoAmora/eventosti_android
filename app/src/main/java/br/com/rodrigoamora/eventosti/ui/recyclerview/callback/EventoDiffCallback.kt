package br.com.rodrigoamora.eventosti.ui.recyclerview.callback

import androidx.recyclerview.widget.DiffUtil
import br.com.rodrigoamora.eventosti.model.Evento

class EventoDiffCallback(
    private val mOldList: List<Evento> = listOf(),
    private val mNewList: List<Evento> = listOf()
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = mOldList.size

    override fun getNewListSize(): Int = mNewList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = mOldList[oldItemPosition].id == mNewList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCharacter = mOldList[oldItemPosition]
        val newCharacter = mNewList[newItemPosition]
        return oldCharacter.id == newCharacter.id
    }

}
