package ic.aiczone.cifootballapps.activity.team

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import ic.aiczone.cifootballapps.R
import ic.aiczone.cifootballapps.R.drawable.ic_favorite_add
import ic.aiczone.cifootballapps.R.drawable.ic_favorite_added
import ic.aiczone.cifootballapps.activity.data.PlayerFragment
import ic.aiczone.cifootballapps.adapters.PagerAdapter
import ic.aiczone.cifootballapps.entities.Team
import ic.aiczone.cifootballapps.utils.ApiRepository
import ic.aiczone.cifootballapps.utils.database
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {
    private lateinit var teams: Team
    private lateinit var presenter: DetailTeamPresenter

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var dataId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        val intent = intent
        dataId = intent.getStringExtra("id")
        Log.d("data", dataId)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_detail_team)

        favoriteState()

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailTeamPresenter(this, request, gson)

        presenter.getData(dataId)

/*        swipeRefreshTeam.onRefresh {
            presenter.getData(dataId)
        }

        swipeRefreshTeam.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)*/

    }

    private fun setDetail() {
        val adapter = PagerAdapter(supportFragmentManager)
        adapter.addFrag(OvFragment.newInstance(teams), "OVERVIEW")
        adapter.addFrag(PlayerFragment.newInstance(teams), "PLAYERS")
        team_viewpager.adapter = adapter

        detail_tabs.setupWithViewPager(team_viewpager)
    }

    override fun showLoading() {
        //swipeRefreshTeam.isRefreshing = true
    }

    override fun hideLoading() {
        //swipeRefreshTeam.isRefreshing = false
    }

    override fun showDetail(data: List<Team>) {
        teams = data[0]
        Picasso.get().load(data[0].teamBadge).into(team_badge)
        team_name.text = data[0].teamName
        team_year.text = data[0].teamFormedYear
        team_stadium.text = data[0].teamStadium

        setDetail()
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(Team.TABLE_TEAM,
                        Team.TEAM_ID to teams.teamId,
                        Team.TEAM_NAME to teams.teamName,
                        Team.TEAM_BADGE to teams.teamBadge,
                        Team.TEAM_YEAR to teams.teamFormedYear,
                        Team.TEAM_STADIUM to teams.teamStadium,
                        Team.TEAM_DESCRIPTION to teams.teamDescription
                )
            }
            main_layout_detai_team.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            main_layout_detai_team.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(Team.TABLE_TEAM, "(TEAM_ID = {id})", "id" to dataId)
            }
            main_layout_detai_team.snackbar("Removed from favorite").show()
        } catch (e: SQLiteConstraintException) {
            main_layout_detai_team.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_favorite_added)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_favorite_add)
    }

    private fun favoriteState() {
        database.use {
            val result = select(Team.TABLE_TEAM)
                    .whereArgs("(TEAM_ID = {id})", "id" to dataId)
            val favorite = result.parseList(classParser<Team>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail, menu)
        menuItem = menu
        setFavorite()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.favorite_add -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}