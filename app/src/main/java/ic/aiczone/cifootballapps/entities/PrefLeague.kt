package ic.aiczone.cifootballapps.entities

import com.chibatching.kotpref.KotprefModel

object PrefLeague : KotprefModel() {
    var leagueId: String by stringPref(default = "4328")
    var leagueName: String by stringPref(default = "English%20Premier%20League")
}