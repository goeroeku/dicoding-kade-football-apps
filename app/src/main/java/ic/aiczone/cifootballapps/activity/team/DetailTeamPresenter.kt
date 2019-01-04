package ic.aiczone.cifootballapps.activity.team

import com.google.gson.Gson
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

class DetailTeamPresenter(private val view: DetailTeamView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getData(dataId: String) {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(apiRepository
                    .doRequest(DBApi().getTeam(dataId)).await(),
                    TeamResponse::class.java
            ).teams

            if (data != null)
                view.showDetail(data)

            view.hideLoading()
        }

    }

}