package co.touchlab.kampstarter.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.touchlab.kampstarter.models.BreedModel
import java.util.*


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
            print(it)
        }

        val currentTimeMS = Date().time
        if(model.isBreedListStale(currentTimeMS))
            model.getBreedsFromNetwork(currentTimeMS)
    }

    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
    }
}
