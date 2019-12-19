package co.touchlab.kampstarter.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.touchlab.kampstarter.db.KampstarterDb
import com.russhwolf.settings.AndroidSettings
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.android.AndroidSqliteDriver
import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.db.Breed

import co.touchlab.kampstarter.models.SampleModel

class MainActivity : AppCompatActivity() {


    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var model: SampleModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = SampleModel()

        model.performNetworkRequest {result ->
            Log.i("MainActivity", result)
        }
//        text_view.text = createApplicationScreenMessage()
        getDatabaseRows()

        model.initSettings(AndroidSettings.Factory(this).create("KAMPSTARTER_SETTINGS"))
        Log.i(TAG,model.getBooleanSetting().toString())
    }

    private fun getDatabaseRows(){
        val dbHelper = DatabaseHelper(
            AndroidSqliteDriver(
                KampstarterDb.Schema,
                this,
                "KampStarterDb"
            )
        )
        dbHelper.insertItem(1,"Test")
        dbHelper.insertItem(2,"Test2")
        val queries: Query<Breed> = dbHelper.selectAllItems()
        val items:List<Breed> = queries.executeAsList()
        Log.i(TAG,items.toString())
    }
    
    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
    }
}
