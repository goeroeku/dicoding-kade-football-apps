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

class TeamPresenter(private val view: TeamView,
                    private val api: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getData(data: String) {
        getData(data, false)
    }

    fun getData(data: String, cari: Boolean) {
        view.showLoading()

        val uri = if (cari) DBApi().getSearchTeams(data) else DBApi().getTeams(data)

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(api.doRequest(uri).await(), TeamResponse::class.java).teams

            if (data != null)
                view.showData(data)

            view.hideLoading()
        }

    }

}