package ic.aiczone.cifootballapps.activity.favorite

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ic.aiczone.cifootballapps.R
import ic.aiczone.cifootballapps.activity.detail.DetailActivity
import ic.aiczone.cifootballapps.adapters.FavoriteAdapter
import ic.aiczone.cifootballapps.entities.Event
import ic.aiczone.cifootballapps.utils.database
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class FavMatchesFragment : Fragment(), AnkoComponent<Context> {
    private var favorites: MutableList<Event> = mutableListOf()
    private lateinit var adapter: FavoriteAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = FavoriteAdapter(favorites) {
            context?.startActivity<DetailActivity>("id" to "${it.eventId}")
        }
        listEvent.adapter = adapter

        showFavorite()

        swipeRefresh.onRefresh {
            showFavorite()
        }
    }

    private fun showFavorite() {
        favorites.clear()
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(Event.TABLE_EVENT)
            val data = result.parseList(classParser<Event>())
            favorites.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {

        verticalLayout {
            lparams(width = matchParent, height = wrapContent)
            topPadding = dip(52)
            leftPadding = dip(16)
            rightPadding = dip(16)
            bottomPadding = dip(64)

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                )

                listEvent = recyclerView {
                    lparams {
                        width = matchParent
                        height = wrapContent
                    }
                    layoutManager = LinearLayoutManager(ctx)
                    id = R.id.rv
                    tag = "rvFavMatchesFrag"
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        showFavorite()
    }

}
