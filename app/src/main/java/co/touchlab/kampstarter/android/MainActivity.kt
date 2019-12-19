package co.touchlab.kampstarter.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.russhwolf.settings.AndroidSettings
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.android.AndroidSqliteDriver
import co.touchlab.kampstarter.DatabaseHelper
import co.touchlab.kampstarter.models.ItemModel


class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    //private lateinit var model: SampleModel
    private lateinit var model: BreedModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = BreedModel {

        }
//        text_view.text = createApplicationScreenMessage()

        model.initSettings()
        Log.i(TAG,model.getBooleanSetting().toString())

        itemModel = ItemModel(){summary ->
            Log.e(TAG, summary.toString())
        }

        val handler = Handler()

        handler.post {
            itemModel.insertSomeData()
        }

        //This should obviously be different, but wanted to see that
        //Flow gets shut down properly
        Handler().postDelayed({
            itemModel.onDestroy()
        }, 2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
    }
}
