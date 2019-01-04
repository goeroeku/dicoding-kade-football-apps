package ic.aiczone.cifootballapps.utils

import ic.aiczone.cifootballapps.BuildConfig


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

class DBApi {
    fun getPrevSchedule(data: String) = urlBuild("eventspastleague.php", "?id=$data")
    fun getNextSchedule(data: String) = urlBuild("eventsnextleague.php", "?id=$data")
    fun getMatchDetail(data: String) = urlBuild("lookupevent.php", "?id=$data")
    fun getSearchMatch(data: String) = urlBuild("searchevents.php", "?e=$data")
    fun getTeams(data: String) = urlBuild("search_all_teams.php", "?l=$data")
    fun getTeam(data: String?) = urlBuild("lookupteam.php", "?id=$data")
    fun getSearchTeams(data: String) = urlBuild("searchteams.php", "?t=$data")
    fun getPlayers(data: String) = urlBuild("lookup_all_players.php", "?id=$data")
    fun getPlayer(data: String) = urlBuild("lookupplayer.php", "?id=$data")

    /**
     * jika generate url menggunakan Uri.parse, akan susah ketika melakukan unit testing
     * harus menggunakan sintak berikut pada class test-nya
     * # @RunWith(RobolectricTestRunner::class)
     * # @Config(constants = BuildConfig::class)
     */
/*    private fun urlBuild(path: String?) = Uri.parse(BuildConfig.API_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.API_KEY)
            .appendPath(path)
            .appendQueryParameter("data", id)
            .build().toString()*/

    private fun urlBuild(path: String, qry: String?) = BuildConfig.API_URL
            .plus("api/v1/json/")
            .plus(BuildConfig.API_KEY + "/")
            .plus(path)
            .plus(qry)
}