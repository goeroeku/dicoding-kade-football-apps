package ic.aiczone.cifootballapps.activity.event

import com.google.gson.Gson
import ic.aiczone.cifootballapps.entities.MatchDetailResponse
import ic.aiczone.cifootballapps.entities.MatchDetailsResponse
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

class EventPresenter(private val view: EventView,
                     private val api: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getData(data: String) {
        getData(data, false, true)
    }

    fun getData(data: String, cari: Boolean) {
        getData(data, cari, true)
    }

    fun getData(data: String, cari: Boolean, isNext: Boolean) {
        view.showLoading()

        var uri: Any
        if (cari) {
            uri = DBApi().getSearchMatch(data)
        } else {
            uri = if (isNext) DBApi().getNextSchedule(data) else DBApi().getPrevSchedule(data)
        }

        GlobalScope.launch(Dispatchers.Main) {
            var data: Any

            if (cari) {
                data = gson.fromJson(api.doRequest(uri).await(), MatchDetailResponse::class.java).event

            } else {
                data = gson.fromJson(api.doRequest(uri).await(), MatchDetailsResponse::class.java).events
            }

            if (data != null)
                view.showList(data)

            view.hideLoading()
        }
    }

}