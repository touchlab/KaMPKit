package co.touchlab.kampstarter.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import co.touchlab.kampstarter.android.adapter.MainAdapter
import co.touchlab.kampstarter.models.BreedModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
    private lateinit var adapter: MainAdapter
    private lateinit var model: BreedModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = BreedModel {
            print(it)
            adapter.submitList(it.allItems)
        }

        adapter = MainAdapter(model::updateBreedFavorite)

        breed_list.adapter = adapter
        breed_list.layoutManager = LinearLayoutManager(this)

        model.getBreedsFromNetwork()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
    }
}
