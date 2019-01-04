package ic.aiczone.cifootballapps.activity.team

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ic.aiczone.cifootballapps.R
import ic.aiczone.cifootballapps.entities.Team
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.nestedScrollView

class OvFragment : Fragment(), AnkoComponent<Context> {
    private lateinit var team: Team
    private lateinit var desc: TextView


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        desc.text = team.teamDescription
    }

    companion object {
        fun newInstance(team: Team): OvFragment {
            val fragment = OvFragment()
            fragment.team = team

            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(requireContext()))
    }

    override fun createView(ui: AnkoContext<Context>) = with(ui) {

        verticalLayout {
            lparams {
                width = matchParent
                height = wrapContent
            }

            nestedScrollView {
                id = R.id.team_detail
                isVerticalScrollBarEnabled = false

                verticalLayout {
                    lparams {
                        width = matchParent
                        height = wrapContent
                    }
                    padding = dip(10)
                    gravity = Gravity.CENTER_HORIZONTAL

                    desc = textView().lparams(width = matchParent, height = 200) {
                        topMargin = dip(8)
                        bottomMargin = dip(16)
                        leftMargin = dip(8)
                        rightMargin = dip(8)
                    }
                }
            }
        }
    }
}