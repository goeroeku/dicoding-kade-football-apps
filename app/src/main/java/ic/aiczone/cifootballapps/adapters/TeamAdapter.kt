package ic.aiczone.cifootballapps.adapters

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ic.aiczone.cifootballapps.R
import ic.aiczone.cifootballapps.R.id.team_badge
import ic.aiczone.cifootballapps.R.id.team_name
import ic.aiczone.cifootballapps.entities.Team
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk27.coroutines.onClick

class TeamAdapter(private val team: List<Team>,
                  private val listener: (Team) -> Unit) : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val teamBadge: ImageView = view.find(team_badge)
        private val teamName: TextView = view.find(team_name)

        fun bindItem(teams: Team, listener: (Team) -> Unit) {
            Picasso.get().load(teams.teamBadge).into(teamBadge)
            teamName.text = teams.teamName

            itemView.onClick {
                listener(teams)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(team[position], listener)
    }

    override fun getItemCount() = team.size

    class TeamUI : AnkoComponent<ViewGroup> {

        override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
            cardView {
                lparams(width = matchParent, height = wrapContent) {
                    gravity = Gravity.CENTER
                    margin = dip(4)
                    radius = 4f
                }

                linearLayout {
                    lparams(width = matchParent, height = wrapContent)
                    padding = dip(16)

                    imageView {
                        id = R.id.team_badge
                    }.lparams {
                        height = dip(50)
                        width = dip(50)
                    }

                    textView {
                        id = R.id.team_name
                        textSize = 16f
                    }.lparams {
                        margin = dip(15)
                    }

                }
            }
        }

    }


}