package br.com.rodrigoamora.eventosti.ui.recyclerview.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewPaginateListener(
    private var linearLayoutManager: LinearLayoutManager?
): RecyclerView.OnScrollListener() {

    var currentPage : Int = 1
    var loading = true
    var previousTotal : Int = 0
    var visibleThreshold : Int = 5

    var firstVisibleItem : Int? = null
    var visibleItemCount : Int? = null
    var totalItemCount : Int? = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        this.visibleItemCount = recyclerView.childCount
        this.totalItemCount = this.linearLayoutManager?.itemCount
        this.firstVisibleItem = this.linearLayoutManager?.findFirstVisibleItemPosition()

        if (this.loading) {
            if (this.totalItemCount!! > this.previousTotal) {
                this.loading = false
                this.previousTotal = this.totalItemCount!!
            }
        }

        if (!this.loading && (this.totalItemCount!! - this.visibleItemCount!!) <=
            (this.firstVisibleItem!! + this.visibleThreshold)) {
            this.currentPage++
            onLoadMore(this.currentPage)
            this.loading = true
        }
    }

    abstract fun onLoadMore(currentPage: Int)

}
