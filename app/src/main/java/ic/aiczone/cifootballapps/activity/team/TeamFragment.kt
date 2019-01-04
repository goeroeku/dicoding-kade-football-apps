package ic.aiczone.cifootballapps.activity.data

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import com.google.gson.Gson
import ic.aiczone.cifootballapps.R
import ic.aiczone.cifootballapps.activity.team.DetailTeamActivity
import ic.aiczone.cifootballapps.activity.team.TeamPresenter
import ic.aiczone.cifootballapps.activity.team.TeamView
import ic.aiczone.cifootballapps.adapters.TeamAdapter
import ic.aiczone.cifootballapps.entities.PrefLeague
import ic.aiczone.cifootballapps.entities.Team
import ic.aiczone.cifootballapps.utils.ApiRepository
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class TeamFragment : Fragment(), AnkoComponent<Context>, TeamView {
    private var team: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamPresenter
    private lateinit var adapter: TeamAdapter
    private lateinit var review: RecyclerView
    private lateinit var swipe: SwipeRefreshLayout
    private var dataId = "English%20Premier%20League"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dataId = PrefLeague.leagueName.replace(" ", "%20")

        val req = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, req, gson)
        presenter.getData(dataId)

        adapter = TeamAdapter(team) {
            context?.startActivity<DetailTeamActivity>("id" to it.teamId)
        }
        review.adapter = adapter

        swipe.onRefresh {
            presenter.getData(dataId)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return createView(AnkoContext.create(requireContext()))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchItem = menu?.findItem(R.id.search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.tag = "searchTeam"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(filter: String?): Boolean {
                if (filter?.length!! > 2) {
                    presenter.getData(filter, true)
                } else if (filter.isEmpty()) {
                    presenter.getData(dataId)
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

    override fun showData(data: List<Team>) {
        hideLoading()
        team.clear()
        if (data != null) {
            team.addAll(data)
        }
        adapter.notifyDataSetChanged()
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {

        verticalLayout {
            lparams(width = matchParent, height = wrapContent)

            topPadding = dip(8)
            leftPadding = dip(16)
            rightPadding = dip(16)
            bottomPadding = dip(64)

            swipe = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light
                )

                review = recyclerView {
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(ctx)
                    id = R.id.rv
                    tag = "rvTeamFrag"
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getData(dataId)
    }

}
