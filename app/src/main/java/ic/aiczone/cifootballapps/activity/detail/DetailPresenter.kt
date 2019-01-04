package ic.aiczone.cifootballapps.activity.detail

import com.google.gson.Gson
import ic.aiczone.cifootballapps.entities.MatchDetailsResponse
import ic.aiczone.cifootballapps.entities.TeamResponse
import ic.aiczone.cifootballapps.utils.ApiRepository
import ic.aiczone.cifootballapps.utils.CoroutineContextProvider
import ic.aiczone.cifootballapps.utils.DBApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

class DetailPresenter(private val view: DetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getEventDetail(eventId: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                    .doRequest(DBApi().getMatchDetail(eventId)).await(),
                    MatchDetailsResponse::class.java
            )

            val homeBadge = gson.fromJson(apiRepository
                    .doRequest(DBApi().getTeam(data.events[0].homeTeamId)).await(),
                    TeamResponse::class.java
            )

            val awayBadge = gson.fromJson(apiRepository
                    .doRequest(DBApi().getTeam(data.events[0].awayTeamId)).await(),
                    TeamResponse::class.java
            )

            if (data != null)
                view.showDetail(data.events, homeBadge.teams, awayBadge.teams)

            view.hideLoading()
        }

    }

}