package ic.aiczone.cifootballapps.activity.team

import ic.aiczone.cifootballapps.entities.MatchDetail
import ic.aiczone.cifootballapps.entities.Team


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

interface DetailTeamView {

    fun showLoading()
    fun hideLoading()
    fun showDetail(data: List<Team>)

}