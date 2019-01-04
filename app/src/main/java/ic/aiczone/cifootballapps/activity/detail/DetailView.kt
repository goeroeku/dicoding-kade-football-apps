package ic.aiczone.cifootballapps.activity.detail

import ic.aiczone.cifootballapps.entities.MatchDetail
import ic.aiczone.cifootballapps.entities.Team


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

interface DetailView {

    fun showLoading()
    fun hideLoading()
    fun showDetail(data: List<MatchDetail>, home: List<Team>, away: List<Team>)

}