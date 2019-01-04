package ic.aiczone.cifootballapps.activity.event

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ic.aiczone.cifootballapps.R
import ic.aiczone.cifootballapps.adapters.PagerAdapter
import ic.aiczone.cifootballapps.entities.PrefLeague
import kotlinx.android.synthetic.main.fav_fragment.*

class MatchFragment : Fragment() {

    private var leagueId = "4335"

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fav_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        leagueId = PrefLeague.leagueId

        val adapter = PagerAdapter(childFragmentManager)
        adapter.addFrag(EventFragment.newInstance(leagueId, true), "PREV")
        adapter.addFrag(EventFragment.newInstance(leagueId, false), "NEXT")
        fav_viewpager.adapter = adapter

        fav_tabs.setupWithViewPager(fav_viewpager)
    }

}