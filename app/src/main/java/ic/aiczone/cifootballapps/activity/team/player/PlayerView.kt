package ic.aiczone.cifootballapps.activity.team.player

import ic.aiczone.cifootballapps.entities.MatchDetail
import ic.aiczone.cifootballapps.entities.Player
import ic.aiczone.cifootballapps.entities.Team


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

interface PlayerView {

    fun showLoading()
    fun hideLoading()
    fun showData(data: List<Player>)

}