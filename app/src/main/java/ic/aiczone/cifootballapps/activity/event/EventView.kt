package ic.aiczone.cifootballapps.activity.event

import ic.aiczone.cifootballapps.entities.MatchDetail


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

interface EventView {

    fun showLoading()
    fun hideLoading()
    fun showList(data: List<MatchDetail>)

}