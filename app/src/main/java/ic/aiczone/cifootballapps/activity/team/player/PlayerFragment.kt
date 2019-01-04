package ic.aiczone.cifootballapps.activity.data

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import ic.aiczone.cifootballapps.R
import ic.aiczone.cifootballapps.activity.team.player.DetailPlayerActivity
import ic.aiczone.cifootballapps.activity.team.player.PlayerPresenter
import ic.aiczone.cifootballapps.activity.team.player.PlayerView
import ic.aiczone.cifootballapps.adapters.PlayerAdapter
import ic.aiczone.cifootballapps.entities.Player
import ic.aiczone.cifootballapps.entities.Team
import ic.aiczone.cifootballapps.utils.DBApi
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayerFragment : Fragment(), AnkoComponent<Context>, PlayerView {
    private lateinit var team: Team
    private var player: MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter: PlayerAdapter
    private lateinit var review: RecyclerView
    private lateinit var swipe: SwipeRefreshLayout
    private var dataId = "133604" //Arsenal

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        dataId = team.teamId.toString()

        val dbApi = DBApi()
        val api = dbApi.getPlayers(dataId)
        val gson = Gson()
        presenter = PlayerPresenter(this, api, gson)
        adapter = PlayerAdapter(player) {
            context?.startActivity<DetailPlayerActivity>("id" to it.id)
        }
        review.adapter = adapter

        swipe.onRefresh {
            presenter.getData()
        }

        presenter.getData()
    }

    companion object {
        fun newInstance(team: Team): PlayerFragment {
            val fragment = PlayerFragment()
            fragment.team = team

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }


    override fun showLoading() {
        swipe.isRefreshing = true
    }

    override fun hideLoading() {
        swipe.isRefreshing = false
    }

    override fun showData(data: List<Player>) {
        hideLoading()
        player.clear()
        player.addAll(data)
        Log.d("showData", data.size.toString())
        adapter.notifyDataSetChanged()
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        verticalLayout {
            lparams(width = matchParent, height = wrapContent)

            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)
            bottomPadding = dip(16)

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
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        presenter.getData()
    }

}
