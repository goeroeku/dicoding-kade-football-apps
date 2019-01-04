package ic.aiczone.cifootballapps.adapters

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ic.aiczone.cifootballapps.R
import ic.aiczone.cifootballapps.R.id.*
import ic.aiczone.cifootballapps.entities.Player
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.sdk27.coroutines.onClick

class PlayerAdapter(private val players: List<Player>,
                    private val listener: (Player) -> Unit) : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PlayerUI().createView(AnkoContext.create(parent.context, parent)))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val igBadge: ImageView = view.find(player_badge)
        private val txName: TextView = view.find(player_name)
        private val txPosition: TextView = view.find(player_position)

        fun bindItem(player: Player, listener: (Player) -> Unit) {
            Picasso.get().load(player.badge).into(igBadge)
            txName.text = player.name
            txPosition.text = player.position

            itemView.onClick {
                listener(player)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

    override fun getItemCount() = players.size

    class PlayerUI : AnkoComponent<ViewGroup> {

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
                    gravity = Gravity.CENTER_VERTICAL

                    imageView {
                        id = R.id.player_badge
                    }.lparams {
                        height = dip(45)
                        width = dip(45)
                        marginEnd = dip(16)
                    }

                    textView {
                        id = R.id.player_name
                        textSize = 10f
                    }.lparams {
                        weight = 0.5F
                        marginEnd = dip(16)
                    }

                    textView {
                        id = R.id.player_position
                        textSize = 10f
                        textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                    }.lparams {
                        weight = 0.5F
                    }

                }
            }
        }

    }


}