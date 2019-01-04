package ic.aiczone.cifootballapps.activity.favorite

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.*
import ic.aiczone.cifootballapps.R
import ic.aiczone.cifootballapps.R.layout.fav_fragment
import ic.aiczone.cifootballapps.adapters.PagerAdapter
import kotlinx.android.synthetic.main.fav_fragment.*

class FavFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(fav_fragment, container, false)

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        menu!!.clear()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val adapter = PagerAdapter(childFragmentManager)
        adapter.addFrag(FavMatchesFragment(), "MATCHES")
        adapter.addFrag(FavTeamsFragment(), "TEAMS")
        fav_viewpager.adapter = adapter

        fav_tabs.setupWithViewPager(fav_viewpager)
    }

}
