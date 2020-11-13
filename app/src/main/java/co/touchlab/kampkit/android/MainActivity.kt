package co.touchlab.kampkit.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.touchlab.kampkit.android.adapter.MainAdapter
import co.touchlab.kampkit.android.databinding.ActivityMainBinding
import co.touchlab.kermit.Kermit
import com.google.android.material.snackbar.Snackbar
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), KoinComponent {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val log: Kermit by inject { parametersOf("MainActivity") }

    private lateinit var viewModel: BreedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this).get(BreedViewModel::class.java)
        val adapter = MainAdapter { viewModel.updateBreedFavorite(it) }

        viewModel.breedLiveData.observe(this) { itemData ->
            log.v { "List submitted to adapter" }
            adapter.submitList(itemData.allItems)
        }

        viewModel.errorLiveData.observe(this) { errorMessage ->
            log.e { "Error displayed: $errorMessage" }
            Snackbar.make(binding.breedList, errorMessage, Snackbar.LENGTH_SHORT).show()
        }

        binding.breedList.adapter = adapter
        binding.breedList.layoutManager = LinearLayoutManager(this)

        viewModel.getBreedsFromNetwork()
    }
}
