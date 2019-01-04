package ic.aiczone.cifootballapps.utils

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.URL


/**
 * Created by aic on 21/09/18.
 * Email goeroeku@gmail.com.
 */

class ApiRepository {
    //fun doRequest(url: String) = URL(url).readText()
    fun doRequest(url: String): Deferred<String> = GlobalScope.async {
        URL(url).readText()
    }
}