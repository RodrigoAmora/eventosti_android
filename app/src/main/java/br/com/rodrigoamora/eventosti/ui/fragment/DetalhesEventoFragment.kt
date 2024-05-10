package br.com.rodrigoamora.eventosti.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rodrigoamora.eventosti.databinding.FragmentDetalhesEventoBinding
import br.com.rodrigoamora.eventosti.databinding.FragmentListaEventosBinding

class DetalhesEventoFragment: BaseFragment() {

    private var _binding: FragmentDetalhesEventoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this._binding = FragmentDetalhesEventoBinding.inflate(inflater, container, false)
        val root: View = this.binding.root

        return root
    }

}
