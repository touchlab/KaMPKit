package co.touchlab.kampstarter.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import co.touchlab.kampstarter.currentTimeMillis
import co.touchlab.kampstarter.models.BreedModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
    private lateinit var adapter:MainAdapter
    private lateinit var model: BreedModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = BreedModel {
            print(it)
            adapter.submitList(it.allItems)
        }
        model.requestBreedsFromDatabaseAsFlow()

        adapter = MainAdapter(model)
        breed_list.adapter = adapter
        breed_list.layoutManager = LinearLayoutManager(this)

        model.getBreedsFromNetwork()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
    }
}
