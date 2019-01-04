package ic.aiczone.cifootballapps.utils

import ic.aiczone.cifootballapps.BuildConfig
import org.junit.Assert.assertEquals
import org.junit.Test

/*@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)*/
class DBApiTest {

    var apiURL = "https://www.thesportsdb.com/api/v1/json/" + BuildConfig.API_KEY

    @Test
    fun testGetTeamDetail() {
        var id = "133604"
        var realita = DBApi().getTeam(id)
        var impian = apiURL.plus("/lookupteam.php?id=").plus(id)
        assertEquals(impian, realita)
    }

    @Test
    fun testGetMatchDetail() {
        var id = "576566"
        var realita = DBApi().getMatchDetail(id)
        var impian = apiURL.plus("/lookupevent.php?id=").plus(id)
        assertEquals(impian, realita)
    }

    @Test
    fun testSearchTeam() {
        var data = "Arsenal"
        var realita = DBApi().getSearchTeams(data)
        var impian = apiURL.plus("/searchteams.php?t=").plus(data)
        assertEquals(impian, realita)
    }

    @Test
    fun testSearchEvent() {
        var data = "Arsenal"
        var realita = DBApi().getSearchMatch(data)
        var impian = apiURL.plus("/searchevents.php?e=").plus(data)
        assertEquals(impian, realita)
    }
}