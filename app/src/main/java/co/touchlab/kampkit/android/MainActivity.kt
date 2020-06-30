package co.touchlab.kampkit.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.touchlab.kampkit.android.adapter.MainAdapter
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
    private val log: Kermit by inject { parametersOf("MainActivity") }

    private lateinit var viewModel: BreedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(BreedViewModel::class.java)

        viewModel.breedLiveData.observe(
            this,
            Observer { itemData ->
                log.v { "List submitted to adapter" }
                adapter.submitList(itemData.allItems)
            }
        )
        viewModel.errorLiveData.observe(
            this,
            Observer { errorMessage ->
                log.e { "Error displayed: $errorMessage" }
                Snackbar.make(breed_list, errorMessage, Snackbar.LENGTH_SHORT).show()
            }
        )
        adapter = MainAdapter {
            viewModel.updateBreedFavorite(it)
        }

        breed_list.adapter = adapter
        breed_list.layoutManager = LinearLayoutManager(this)

        viewModel.getBreedsFromNetwork()
    }
}
