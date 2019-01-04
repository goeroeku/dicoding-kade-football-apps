package ic.aiczone.cifootballapps.activity.detail

import com.google.gson.Gson
import ic.aiczone.cifootballapps.entities.MatchDetail
import ic.aiczone.cifootballapps.entities.MatchDetailsResponse
import ic.aiczone.cifootballapps.entities.Team
import ic.aiczone.cifootballapps.entities.TeamResponse
import ic.aiczone.cifootballapps.utils.ApiRepository
import ic.aiczone.cifootballapps.utils.DBApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/*@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)*/
class DetailPresenterTest {

    @Mock
    private lateinit var detailView: DetailView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var eventDetail: DetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        eventDetail = DetailPresenter(detailView, apiRepository, gson)
    }

    @Test
    fun testDetail() {
        val matchDetail: MutableList<MatchDetail> = mutableListOf()
        val teamHome: MutableList<Team> = mutableListOf()
        val teamAway: MutableList<Team> = mutableListOf()
        val resMatch = MatchDetailsResponse(matchDetail)
        val resHome = TeamResponse(teamHome)
        val resAway = TeamResponse(teamAway)
        val eventId = "576566"

        GlobalScope.launch {
            Mockito.`when`(
                    gson.fromJson(apiRepository
                            .doRequest(DBApi().getMatchDetail(eventId)).await(),
                            MatchDetailsResponse::class.java)
            ).thenReturn(resMatch)

            Mockito.`when`(
                    gson.fromJson(apiRepository
                            .doRequest(DBApi().getTeam(resMatch.events[0].homeTeamId)).await(),
                            TeamResponse::class.java)
            ).thenReturn(resHome)

            Mockito.`when`(
                    gson.fromJson(apiRepository
                            .doRequest(DBApi().getTeam(resMatch.events[0].homeTeamId)).await(),
                            TeamResponse::class.java)
            ).thenReturn(resAway)

            eventDetail.getEventDetail(eventId)

            Mockito.verify(detailView).showLoading()
            Mockito.verify(detailView).showDetail(matchDetail, teamHome, teamAway)
            Mockito.verify(detailView).hideLoading()

        }
    }

}