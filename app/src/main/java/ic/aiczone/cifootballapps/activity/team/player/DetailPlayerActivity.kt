package ic.aiczone.cifootballapps.activity.team.player

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import ic.aiczone.cifootballapps.R
import ic.aiczone.cifootballapps.R.color.colorAccent
import ic.aiczone.cifootballapps.entities.Player
import ic.aiczone.cifootballapps.utils.ApiRepository
import kotlinx.android.synthetic.main.activity_detail_player.*
import org.jetbrains.anko.support.v4.onRefresh


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

class DetailPlayerActivity : AppCompatActivity(), DetailPlayerView {
    private lateinit var player: Player
    private lateinit var presenter: DetailPlayerPresenter

    private lateinit var dataId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)



        val intent = intent
        dataId = intent.getStringExtra("id")
        Log.d("data", dataId)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_detail_player)

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPlayerPresenter(this, request, gson)
        presenter.getData(dataId)

        swipeRefreshPlayer.onRefresh {
            presenter.getData(dataId)
        }
        swipeRefreshPlayer.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

    }


    override fun showLoading() {
        swipeRefreshPlayer.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefreshPlayer.isRefreshing = false
    }

    override fun showDetail(data: List<Player>) {
        player = data[0]
        Picasso.get().load(data[0].image).into(ig_badge)
        tx_name.text = data[0].name
        tx_weight.text = data[0].weight
        tx_height.text = data[0].height
        tx_position.text = data[0].position
        tx_description.text = data[0].description

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}