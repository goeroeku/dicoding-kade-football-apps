package ic.aiczone.cifootballapps.activity.event

import com.google.gson.Gson
import ic.aiczone.cifootballapps.entities.MatchDetail
import ic.aiczone.cifootballapps.entities.MatchDetailResponse
import ic.aiczone.cifootballapps.entities.MatchDetailsResponse
import ic.aiczone.cifootballapps.utils.ApiRepository
import ic.aiczone.cifootballapps.utils.CoroutineContextProvider
import ic.aiczone.cifootballapps.utils.DBApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/*@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)*/
class EventPresenterTest {

    @Mock
    private lateinit var eventView: EventView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var eventPresenter: EventPresenter

    private lateinit var api: String
    private var leagueId = "4328"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        eventPresenter = EventPresenter(eventView, ApiRepository(), gson, CoroutineContextProvider())
    }

    @Test
    fun getListPrev() {

        val event: MutableList<MatchDetail> = mutableListOf()
        val response = MatchDetailsResponse(event)

        GlobalScope.launch {
            `when`(
                    gson.fromJson(ApiRepository().doRequest(api).await(), MatchDetailsResponse::class.java)
            ).thenReturn(response)

            eventPresenter.getData(leagueId,false,false)

            Mockito.verify(eventView).showLoading()
            Mockito.verify(eventView).showList(response.events)
            Mockito.verify(eventView).hideLoading()

        }
    }

    @Test
    fun getListNext() {

        val event: MutableList<MatchDetail> = mutableListOf()
        val response = MatchDetailsResponse(event)

        GlobalScope.launch {
            `when`(
                    gson.fromJson(ApiRepository().doRequest(api).await(), MatchDetailsResponse::class.java)
            ).thenReturn(response)

            eventPresenter.getData(leagueId)

            Mockito.verify(eventView).showLoading()
            Mockito.verify(eventView).showList(response.events)
            Mockito.verify(eventView).hideLoading()

        }
    }

    @Test
    fun search() {

        val event: MutableList<MatchDetail> = mutableListOf()
        val response = MatchDetailResponse(event)

        GlobalScope.launch {
            `when`(
                    gson.fromJson(ApiRepository().doRequest(api).await(), MatchDetailResponse::class.java)
            ).thenReturn(response)

            eventPresenter.getData(leagueId)

            Mockito.verify(eventView).showLoading()
            Mockito.verify(eventView).showList(response.event)
            Mockito.verify(eventView).hideLoading()

        }
    }

}