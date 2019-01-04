package ic.aiczone.cifootballapps.activity.event

/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

import android.content.Context
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import com.google.gson.Gson
import ic.aiczone.cifootballapps.R
import ic.aiczone.cifootballapps.activity.detail.DetailActivity
import ic.aiczone.cifootballapps.adapters.EventAdapter
import ic.aiczone.cifootballapps.entities.MatchDetail
import ic.aiczone.cifootballapps.utils.ApiRepository
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class EventFragment : android.support.v4.app.Fragment(), EventView {
    private var match: MutableList<MatchDetail> = mutableListOf()
    private lateinit var presenter: EventPresenter
    private lateinit var review: RecyclerView
    private lateinit var swipe: SwipeRefreshLayout
    private lateinit var adapter: EventAdapter
    private var isPrev: Boolean = true
    private var dataId = "4328"

    companion object {
        fun newInstance(dataId: String, isPrev: Boolean): EventFragment {
            val fragment = EventFragment()
            fragment.isPrev = isPrev
            fragment.dataId = dataId
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val req = ApiRepository()
        val gson = Gson()
        presenter = EventPresenter(this, req, gson)
        adapter = EventAdapter(match) {
            context?.startActivity<DetailActivity>("id" to it.eventId)
        }
        review.adapter = adapter

        swipe.onRefresh {
            getMatchData()
        }

        getMatchData()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return createView(AnkoContext.create(requireContext()))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu?.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(filter: String?): Boolean {
                if (filter?.length!! > 2) {
                    presenter.getData(filter, true)
                } else if (filter.isEmpty()) {
                    getMatchData()
                }
                return false
            }

        })
    }

    override fun showLoading() {
        swipe.isRefreshing = true
    }

    override fun hideLoading() {
        swipe.isRefreshing = false
    }

    override fun showList(data: List<MatchDetail>) {
        hideLoading()
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun createView(ui: AnkoContext<Context>): View = with(ui) {
        verticalLayout {
            lparams {
                width = matchParent
                height = wrapContent
            }
            topPadding = dip(52)
            leftPadding = dip(16)
            rightPadding = dip(16)
            bottomPadding = dip(64)
            id = R.id.eventFrag

            swipe = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                )

                review = recyclerView {
                    lparams {
                        width = matchParent
                        height = wrapContent

                    }
                    layoutManager = LinearLayoutManager(ctx)
                    id = R.id.rv
                    if(isPrev){
                        tag = "rvPrevEventFrag"
                    }else{
                        tag = "rvNextEventFrag"
                    }
                }
            }

        }
    }

    private fun getMatchData() {
        if (!isPrev)
            presenter.getData(dataId)
        else
            presenter.getData(dataId, false, false)
    }

    override fun onResume() {
        super.onResume()
        presenter.getData(dataId)
    }
}