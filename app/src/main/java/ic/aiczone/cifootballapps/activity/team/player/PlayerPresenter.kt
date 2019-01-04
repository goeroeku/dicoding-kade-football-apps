package ic.aiczone.cifootballapps.activity.team.player

import com.google.gson.Gson
import ic.aiczone.cifootballapps.entities.PlayersResponse
import ic.aiczone.cifootballapps.utils.ApiRepository
import ic.aiczone.cifootballapps.utils.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

class PlayerPresenter(private val view: PlayerView,
                      private val api: String,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getData() {
        view.showLoading()

        GlobalScope.launch(Dispatchers.Main) {
            val data = gson.fromJson(ApiRepository().doRequest(api).await(),
                    PlayersResponse::class.java).players

            if (data != null)
                view.showData(data)

            view.hideLoading()
        }

    }

}