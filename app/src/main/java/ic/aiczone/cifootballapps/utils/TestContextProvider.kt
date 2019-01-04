package ic.aiczone.cifootballapps.utils

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext


class TestContextProvider : CoroutineContextProvider() {
    override val main: CoroutineContext = Dispatchers.Unconfined
}
