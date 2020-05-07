package co.touchlab.kampstarter.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.touchlab.kampstarter.android.adapter.MainAdapter
import co.touchlab.kampstarter.models.BreedModel
import co.touchlab.kermit.Kermit
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), KoinComponent {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
    private lateinit var adapter: MainAdapter
    private lateinit var model: BreedModel
    private val log:Kermit by inject { parametersOf("MainActivity") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = BreedModel(viewUpdate = { itemData ->
            log.v { "List submitted to adapter" }
            adapter.submitList(itemData.allItems)
        },errorUpdate = { errorMessage ->
            log.e { "Error displayed: $errorMessage" }
            Snackbar.make(breed_list, errorMessage, Snackbar.LENGTH_SHORT).show()
        })

        adapter = MainAdapter { model.updateBreedFavorite(it) }

        breed_list.adapter = adapter
        breed_list.layoutManager = LinearLayoutManager(this)

        model.getBreedsFromNetwork()
    }

    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
    }
}
