package ic.aiczone.cifootballapps

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.chibatching.kotpref.Kotpref
import ic.aiczone.cifootballapps.R.id.*
import ic.aiczone.cifootballapps.activity.data.TeamFragment
import ic.aiczone.cifootballapps.activity.event.MatchFragment
import ic.aiczone.cifootballapps.activity.favorite.FavFragment
import ic.aiczone.cifootballapps.entities.PrefLeague
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var savedInstanceState: Bundle? = null
    private var leagueId = "4328"
    private var leagueName = "English%20Premier%20League"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nav_button.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                nav_match -> {
                    openFragment(MatchFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                nav_team -> {
                    openFragment(TeamFragment())
                    return@setOnNavigationItemSelectedListener true
                }
                nav_favorite -> {
                    openFragment(FavFragment())
                    return@setOnNavigationItemSelectedListener true

                }
            }
            false
        }
        nav_button.selectedItemId = nav_match

        // init preference
        Kotpref.init(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            eng_premier -> {
                leagueId = "4328"
                leagueName = getString(R.string.eng_premier)
            }
            ger_bundes -> {
                leagueId = "4331"
                leagueName = getString(R.string.ger_bundes)
            }
            ita_serie -> {
                leagueId = "4332"
                leagueName = getString(R.string.ita_serie)
            }
            fre_league -> {
                leagueId = "4334"
                leagueName = getString(R.string.fre_league)
            }
            spa_leage -> {
                leagueId = "4335"
                leagueName = getString(R.string.spa_league)
            }
        }
        PrefLeague.leagueId = leagueId
        PrefLeague.leagueName = leagueName

        if (nav_button.selectedItemId == nav_match) {
            openFragment(MatchFragment())
        } else if (nav_button.selectedItemId == nav_team) {
            openFragment(TeamFragment())
        } else {
            openFragment(FavFragment())
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openFragment(fragment: Fragment) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, fragment, fragment.javaClass.simpleName)
                    .commit()
        }
    }
}