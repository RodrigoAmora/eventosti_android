package br.com.rodrigoamora.eventosti.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.rodrigoamora.eventosti.R
import br.com.rodrigoamora.eventosti.databinding.FragmentListaEventosBinding
import br.com.rodrigoamora.eventosti.extension.hide
import br.com.rodrigoamora.eventosti.extension.show
import br.com.rodrigoamora.eventosti.model.Evento
import br.com.rodrigoamora.eventosti.ui.activiy.MainActivity
import br.com.rodrigoamora.eventosti.ui.recyclerview.adapter.ListaEventosAdapter
import br.com.rodrigoamora.eventosti.ui.recyclerview.listener.RecyclerViewPaginateListener
import br.com.rodrigoamora.eventosti.ui.viewmodel.EventoViewModel
import br.com.rodrigoamora.eventosti.util.NetworkUtil
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.view.isGone

class ListaEventosFramgent: BaseFragment() {

    private var _binding: FragmentListaEventosBinding? = null
    private val binding get() = _binding!!

    private lateinit var fabListarTodosEventos: FloatingActionButton
    private lateinit var fabBuscarEventosPorNome: FloatingActionButton
    private lateinit var recyclerViewEventos: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh : SwipeRefreshLayout
    private lateinit var semResultado: LinearLayout

    private val eventoViewModel: EventoViewModel by viewModel()

    private lateinit var adapter: ListaEventosAdapter
    private lateinit var eventos: List<Evento>

    private var error: Int = -1
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

        this.progressBar = this.binding.progressBar

        this.fabBuscarEventosPorNome = this.binding.fabBuscarEventos
        this.fabBuscarEventosPorNome.setOnClickListener {
            if (this.searchView.isGone) {
                this.searchView.visibility = View.VISIBLE
                this.searchView.requestFocus()
            } else {
                this.searchView.visibility = View.GONE
            }
        }

        this.fabListarTodosEventos = this.binding.fabListarTodosEventos
        this.fabListarTodosEventos.setOnClickListener {
            this.buscarEventos()
        }

        this.recyclerViewEventos = this.binding.listEvents

        this.searchView = this.binding.svEvento
        this.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    buscarEventoPorNome(it.replace(" ", "%20").trim())
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        this.swipeRefresh = this.binding.swipeRefresh
        this.swipeRefresh.setOnRefreshListener {
            this.buscarEventos()
            this.swipeRefresh.isRefreshing = false
        }

        this.semResultado = this.binding.noResult

        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.configureRecyclerView()
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
        this.adapter.whenSelected = this::viewDetails

        this.recyclerViewEventos.adapter = this.adapter
        this.recyclerViewEventos.addItemDecoration(dividerItemDecoration)
        this.recyclerViewEventos.setHasFixedSize(true)
        this.recyclerViewEventos.itemAnimator = DefaultItemAnimator()
        this.recyclerViewEventos.layoutManager = linearLayout
        this.recyclerViewEventos.isNestedScrollingEnabled = true
        this.recyclerViewEventos.scrollToPosition(adapter.itemCount - 1)
        this.recyclerViewEventos.addOnScrollListener(object : RecyclerViewPaginateListener(linearLayout) {
            override fun onLoadMore(currentPage: Int) {
                if (eventos.size >= 20) {
                    page += 1
                    buscarEventos()
                }
            }
        })

        this.recyclerViewEventos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && fabListarTodosEventos.isShown) {
                    fabBuscarEventosPorNome.hide()
                    fabListarTodosEventos.hide()
                }
                if (dy < 0 && !fabListarTodosEventos.isShown) {
                    fabBuscarEventosPorNome.show()
                    fabListarTodosEventos.show()
                }
            }
        })
    }

    private fun populateRecyclerView(eventos: List<Evento>) {
        this.progressBar.hide()
        this.searchView.hide()

        if (eventos.isEmpty()) {
            this.swipeRefresh.visibility = View.GONE
            this.semResultado.visibility = View.VISIBLE
        } else {
            this.swipeRefresh.visibility = View.VISIBLE
            this.semResultado.visibility = View.GONE

            this.eventos = eventos
            this.adapter.update(eventos)
        }
    }

    private fun replaceRecyclerView(eventos: List<Evento>) {
        this.progressBar.hide()
        this.searchView.hide()

        if (eventos.isEmpty()) {
            this.swipeRefresh.visibility = View.GONE
            this.semResultado.visibility = View.VISIBLE
        } else {
            this.swipeRefresh.visibility = View.VISIBLE
            this.semResultado.visibility = View.GONE

            this.eventos = eventos
            this.adapter.replaceAll(eventos)
        }
    }

    private fun buscarEventos() {
        if (NetworkUtil.checkConnection(this.mainActivity)) {
            this.progressBar.show()
            this.error = -1
            this.eventoViewModel.buscarEventos(this.page)
                .observe(this.mainActivity,
                    Observer { eventos ->
                        this.progressBar.hide()
                        this.searchView.setQuery("", false)

                        eventos.result?.let {
                            this.replaceRecyclerView(it)
                        }

                        eventos.error?.let {
                            this.error = it
                        }
                    }
                )
        } else {
            this.eventoViewModel.buscarEventosDoBancoDeDados().value?.let {
                this.progressBar.hide()
                this.populateRecyclerView(it)
            }
            this.showToast(this.mainActivity, getString(R.string.error_no_internet))
        }
    }

    private fun buscarEventoPorNome(nome: String) {
        if (NetworkUtil.checkConnection(this.mainActivity)) {
            this.progressBar.show()
            this.eventoViewModel.buscarEventosPorNome(nome, page)
                .observe(this.mainActivity,
                    Observer { eventos ->
                        this.error = -1

                        this.progressBar.hide()
                        this.searchView.setQuery("", false)

                        eventos.result?.let {
                            this.replaceRecyclerView(it)
                            return@Observer
                        }

                        eventos.error?.let {
                            this.error = it
                        }
                        if (eventos.error != null) {
                            this.showError(this.mainActivity, this.error)
                        }
                    }
                )

            if (this.error > -1) {
                this.showError(this.mainActivity, this.error)
            }
        } else {
            this.showToast(this.mainActivity, getString(R.string.error_no_internet))
        }
    }

    @SuppressLint("ResourceType")
    private fun viewDetails(evento: Evento) {
        val eventoBundle = Bundle()
        eventoBundle.putSerializable("evento", evento)

        Navigation.findNavController(this.recyclerViewEventos)
                  .navigate(R.id.action_nav_home_to_nav_detalhes_evento, eventoBundle)
    }

}
