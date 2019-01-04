package ic.aiczone.cifootballapps.activity.detail

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
import ic.aiczone.cifootballapps.R.color.colorAccent
import ic.aiczone.cifootballapps.R.drawable.ic_favorite_add
import ic.aiczone.cifootballapps.R.drawable.ic_favorite_added
import ic.aiczone.cifootballapps.entities.Event
import ic.aiczone.cifootballapps.entities.MatchDetail
import ic.aiczone.cifootballapps.entities.Team
import ic.aiczone.cifootballapps.utils.ApiRepository
import ic.aiczone.cifootballapps.utils.changeFormatDate
import ic.aiczone.cifootballapps.utils.database
import ic.aiczone.cifootballapps.utils.strToDate
import kotlinx.android.synthetic.main.activity_detail_event.*
import kotlinx.android.synthetic.main.section_away_team.*
import kotlinx.android.synthetic.main.section_df.*
import kotlinx.android.synthetic.main.section_fw.*
import kotlinx.android.synthetic.main.section_gk.*
import kotlinx.android.synthetic.main.section_goals.*
import kotlinx.android.synthetic.main.section_home_team.*
import kotlinx.android.synthetic.main.section_mf.*
import kotlinx.android.synthetic.main.section_score.*
import kotlinx.android.synthetic.main.section_subs.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

class DetailActivity: AppCompatActivity(), DetailView {
    private lateinit var matchDetail: MatchDetail
    private lateinit var presenter: DetailPresenter

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var eventId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)

        val intent = intent
        eventId = intent.getStringExtra("id")
        Log.d("data", eventId)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_detail)

        favoriteState()

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenter(this, request, gson)
        presenter.getEventDetail(eventId)

        swipeRefresh.onRefresh {
            presenter.getEventDetail(eventId)
        }
        swipeRefresh.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun showDetail(data: List<MatchDetail>, home: List<Team>, away: List<Team>) {
        matchDetail = data[0]
        tv_date.text = changeFormatDate(strToDate(matchDetail.eventDate))
        Picasso.get().load(home[0].teamBadge).into(home_img)
        home_club.text = matchDetail.homeTeam
        home_score.text = matchDetail.homeScore
        home_formation.text = matchDetail.homeFormation
        home_goals.text = checkEmpty(matchDetail.homeGoalDetails)
        home_shots.text = matchDetail.homeShots ?: "-"
        home_goalkeeper.text = checkEmpty(matchDetail.homeLineupGoalKeeper)
        home_defense.text = checkEmpty(matchDetail.homeLineupDefense)
        home_midfield.text = checkEmpty(matchDetail.homeLineupMidfield)
        home_forward.text = checkEmpty(matchDetail.homeLineupForward)
        home_subtitutes.text = checkEmpty(matchDetail.homeLineupSubstitutes)

        Picasso.get().load(away[0].teamBadge).into(away_img)
        away_club.text = matchDetail.awayTeam
        away_score.text = matchDetail.awayScore
        away_formation.text = matchDetail.awayFormation
        away_goals.text = checkEmpty(matchDetail.awayGoalsDetails)
        away_shots.text = matchDetail.awayShots ?: "-"
        away_goalkeeper.text = checkEmpty(matchDetail.awayLineupGoalKeeper)
        away_defense.text = checkEmpty(matchDetail.awayLineupDefense)
        away_midfield.text = checkEmpty(matchDetail.awayLineupMidfield)
        away_forward.text = checkEmpty(matchDetail.awayLineupForward)
        away_subtitutes.text = checkEmpty(matchDetail.awayLineupSubstitutes)

        hideLoading()
    }

    private fun checkEmpty(content: String?): String{
        if(content.isNullOrEmpty())
            return getString(R.string.empty)
        else
            return content.replace(";", ";\n").toString()
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(Event.TABLE_EVENT,
                        Event.EVENT_ID to matchDetail.eventId,
                        Event.EVENT_NAME to matchDetail.eventName,
                        Event.EVENT_DATE to matchDetail.eventDate,
                        Event.HOME_TEAM_ID to matchDetail.homeTeamId,
                        Event.HOME_TEAM_NAME to matchDetail.homeTeam,
                        Event.HOME_TEAM_SCORE to matchDetail.homeScore,
                        Event.AWAY_TEAM_ID to matchDetail.awayTeamId,
                        Event.AWAY_TEAM_NAME to matchDetail.awayTeam,
                        Event.AWAY_TEAM_SCORE to matchDetail.awayScore)
            }
            swipeRefresh.snackbar("Added to favorite").show()
        }catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use{
                delete(Event.TABLE_EVENT, "(EVENT_ID = {id})", "id" to eventId)
            }
            swipeRefresh.snackbar("Removed from favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite(){
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_favorite_added)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_favorite_add)
    }

    private fun favoriteState(){
        database.use {
            val result = select(Event.TABLE_EVENT)
                    .whereArgs("(EVENT_ID = {id})", "id" to eventId)
            val favorite = result.parseList(classParser<Event>())
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
        return when(item?.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.favorite_add -> {
                if(isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}