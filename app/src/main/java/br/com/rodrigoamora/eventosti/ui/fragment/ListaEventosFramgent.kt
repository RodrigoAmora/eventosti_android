package br.com.rodrigoamora.eventosti.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.rodrigoamora.eventosti.R
import br.com.rodrigoamora.eventosti.databinding.FragmentListaEventosBinding
import br.com.rodrigoamora.eventosti.model.Evento
import br.com.rodrigoamora.eventosti.ui.activiy.MainActivity
import br.com.rodrigoamora.eventosti.ui.recyclerview.adapter.ListaEventosAdapter
import br.com.rodrigoamora.eventosti.ui.recyclerview.listener.RecyclerViewPaginateListener
import br.com.rodrigoamora.eventosti.ui.viewmodel.EventoViewModel
import br.com.rodrigoamora.eventosti.util.NetworkUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListaEventosFramgent: BaseFragment() {

    private var _binding: FragmentListaEventosBinding? = null
    private val binding get() = _binding!!

    private lateinit var fabSearchCharacterByName: FloatingActionButton
    private lateinit var recyclerViewCharacters: RecyclerView
//    private lateinit var searchView: SearchView
    private lateinit var swipeRefresh : SwipeRefreshLayout

    private val eventoViewModel: EventoViewModel by viewModel()

    private lateinit var adapter: ListaEventosAdapter
    private lateinit var eventos: List<Evento>
    private val mainActivity: MainActivity by lazy {
        activity as MainActivity
    }

    private var page = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this._binding = FragmentListaEventosBinding.inflate(inflater, container, false)
        val root: View = this.binding.root

        this.fabSearchCharacterByName = binding.fabBuscarEventos
        this.fabSearchCharacterByName.setOnClickListener {
//            if (this.searchView.visibility == View.GONE) {
//                this.searchView.visibility = View.VISIBLE
//                this.searchView.requestFocus()
//            } else {
//                this.searchView.visibility = View.GONE
//            }
        }

        this.recyclerViewCharacters = this.binding.listCharacters

//        this.searchView = this.binding.svSearchCharacterByName
//        this.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let {
//                    getCharacterByName(it.trim())
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return false
//            }
//        })

        this.swipeRefresh = this.binding.swipeRefresh
        this.swipeRefresh.setOnRefreshListener {
            this.buscarEventos()
            this.swipeRefresh.isRefreshing = false
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.configureRecyclerView()
        this.configureAdapter()
        this.buscarEventos()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
    }

    private fun configureRecyclerView() {
        val linearLayout = LinearLayoutManager(this.mainActivity,
            LinearLayoutManager.VERTICAL,
            false)
        linearLayout.scrollToPosition(0)

        val dividerItemDecoration = DividerItemDecoration(this.mainActivity,
            DividerItemDecoration.VERTICAL)

        this.adapter = ListaEventosAdapter(this.mainActivity.applicationContext)

        this.recyclerViewCharacters.adapter = this.adapter
        this.recyclerViewCharacters.addItemDecoration(dividerItemDecoration)
        this.recyclerViewCharacters.setHasFixedSize(true)
        this.recyclerViewCharacters.itemAnimator = DefaultItemAnimator()
        this.recyclerViewCharacters.layoutManager = linearLayout
        this.recyclerViewCharacters.isNestedScrollingEnabled = true
        this.recyclerViewCharacters.scrollToPosition(adapter.itemCount - 1)
        this.recyclerViewCharacters.addOnScrollListener(object : RecyclerViewPaginateListener(linearLayout) {
            override fun onLoadMore(currentPage: Int) {
                if (eventos.size >= 20) {
                    page += 1
                    buscarEventos()
                }
            }
        })
    }

    private fun configureAdapter() {
        this.adapter.whenSelected = this::viewDetails
    }

    private fun populateRecyclerView(eventos: List<Evento>) {
        this.eventos = eventos
        this.adapter.update(eventos)
    }

    private fun replaceRecyclerView(eventos: List<Evento>) {
        this.eventos = eventos
//        this.searchView.visibility = View.GONE
        this.adapter.replaceAll(eventos)
    }

    private fun buscarEventos() {
        this.eventoViewModel.buscarEventos(this.page, 20).observe(this.mainActivity,
            Observer { eventos ->
                eventos.result?.let {
                    populateRecyclerView(it)
                }
                eventos.error?.let { showError(mainActivity, it) }

                if (!NetworkUtil.checkConnection(mainActivity)) {
                    showToast(mainActivity, getString(R.string.error_no_internet))
                }
            }
        )
    }

    @SuppressLint("ResourceType")
    private fun viewDetails(evento: Evento) {
//        val eventoBundle = Bundle()
//        characterBundle.putSerializable("evento", evento)
//        Navigation.findNavController(this.recyclerViewCharacters)
//            .navigate(R.id.action_nav_list_characters_to_nav_character, eventoBundle)
    }

}