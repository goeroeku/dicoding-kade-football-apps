package ic.aiczone.cifootballapps.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

    private val mList: MutableList<Fragment> = mutableListOf()
    private val mTitleList: MutableList<String> = mutableListOf()

    override fun getCount() = mList.size

    override fun getItem(pos: Int) = mList[pos]

    override fun getPageTitle(pos: Int) = mTitleList[pos]

    fun addFrag(frag: Fragment, title: String) {
        mList.add(frag)
        mTitleList.add(title)
    }
}