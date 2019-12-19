package co.touchlab.kampstarter.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.touchlab.kampstarter.db.Items
import co.touchlab.kampstarter.db.KampstarterDb
import com.russhwolf.settings.AndroidSettings
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.android.AndroidSqliteDriver
import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.ktor.KtorApiImpl

import co.touchlab.kampstarter.models.SampleModel

class MainActivity : AppCompatActivity() {


    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var model: SampleModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = SampleModel(KtorApiImpl,
            AndroidSqliteDriver(
                KampstarterDb.Schema,
                this,
                "KampStarterDb"
            ),
            AndroidSettings.Factory(this).create("KAMPSTARTER_SETTINGS"))

        model.performNetworkRequest {result ->
            Log.i("MainActivity", result)
        }
//        text_view.text = createApplicationScreenMessage()
        model.getDatabaseRows()

        Log.i(TAG,model.getBooleanSetting().toString())
    }
    
    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
    }
}
