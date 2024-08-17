package br.com.rodrigoamora.eventosti.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.com.rodrigoamora.eventosti.R
import br.com.rodrigoamora.eventosti.databinding.FragmentSobreBinding
import br.com.rodrigoamora.eventosti.ui.activiy.MainActivity
import br.com.rodrigoamora.eventosti.util.PackageInfoUtil

class SobreFragment: Fragment() {

    private var _binding: FragmentSobreBinding? = null
    private val binding get() = _binding!!

    private lateinit var tvVersao: TextView
    private lateinit var tvSiteApp: TextView

    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this._binding = FragmentSobreBinding.inflate(inflater, container, false)
        val root: View = this.binding.root

        this.tvVersao = this.binding.tvVersao
        this.tvSiteApp = this.binding.tvSiteApp

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.initView()
    }

    private fun initView() {
        val versaoApp = PackageInfoUtil.getVersionName(mainActivity)

        this.tvVersao.text = getString(R.string.versao, versaoApp)
        this.tvSiteApp.text = getString(R.string.site)
    }
}