package co.touchlab.kampstarter

import co.touchlab.kampstarter.ktor.KtorApi
import co.touchlab.kampstarter.models.SampleModel
import com.russhwolf.settings.MockSettings
import kotlinx.coroutines.Dispatchers
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue


class SampleModelTest {

    lateinit var model: SampleModel

    @BeforeTest
    fun setup(){
        model = SampleModel(ktorApiImpl = KtorApiMock())
    }

    @Test
    fun `Get Boolean Setting Success`(){
        val settings = MockSettings(mutableMapOf())
        model.initSettings(settings)
        assertTrue { model.getBooleanSetting() }
    }

    @Test
    fun `Perform Network Request Success`(){
        model.performNetworkRequest {
            assertTrue { it == "json" }
        }
    }
}

class KtorApiMock : KtorApi {

    override suspend fun getJsonFromApi():String = "json"

    override suspend fun setThingJson(value: String):Boolean = value.toLowerCase() != "error"
}