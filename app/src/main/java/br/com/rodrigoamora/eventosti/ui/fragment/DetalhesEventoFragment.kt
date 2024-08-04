package br.com.rodrigoamora.eventosti.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import br.com.rodrigoamora.eventosti.BuildConfig
import br.com.rodrigoamora.eventosti.R
import br.com.rodrigoamora.eventosti.databinding.FragmentDetalhesEventoBinding
import br.com.rodrigoamora.eventosti.model.Evento
import br.com.rodrigoamora.eventosti.ui.activiy.MainActivity
import br.com.rodrigoamora.eventosti.util.ShareUtil

class DetalhesEventoFragment: BaseFragment() {

    private var _binding: FragmentDetalhesEventoBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvNomeEvento: TextView
    private lateinit var tvDataEvento: TextView
    private lateinit var tvDescricaoEvento: TextView
    private lateinit var tvSiteEvento: TextView
    private lateinit var tvTipoEvento: TextView
    private lateinit var ivShare: ImageView

    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this._binding = FragmentDetalhesEventoBinding.inflate(inflater, container, false)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initViews()
    }

    private fun initViews() {
        val evento = arguments?.getSerializable("evento") as Evento
        val dataEvento = evento.dataFim+" - "+evento.dataFim

        this.tvNomeEvento = this.binding.tvNomeEvento
        this.tvNomeEvento.text = evento.nome

        this.tvDataEvento = this.binding.tvDate
        this.tvDataEvento.text = dataEvento

        this.tvDescricaoEvento = this.binding.tvDescricao
        if (evento.descricao.isNullOrEmpty()) {
            this.tvDescricaoEvento.visibility = View.GONE
        } else {
            this.tvDescricaoEvento.text = evento.descricao
        }

        this.tvSiteEvento = this.binding.tvSite
        this.tvSiteEvento.text = evento.site

        this.tvTipoEvento = this.binding.tvTipoEvento
        this.tvTipoEvento.text = getString(R.string.tv_tipo_evento, evento.tipoEvento)

        this.ivShare = this.binding.ivShare
        this.ivShare.setOnClickListener {
            val texto = BuildConfig.BASE_URL_API+"/verEvento?id="+evento.id
            ShareUtil.directShare(this.mainActivity, evento.nome, texto)
        }
    }
}
