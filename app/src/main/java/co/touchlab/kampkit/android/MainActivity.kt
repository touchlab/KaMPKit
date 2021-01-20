package co.touchlab.kampkit.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import co.touchlab.kampkit.android.adapter.MainAdapter
import co.touchlab.kampkit.android.databinding.ActivityMainBinding
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.DataState
import co.touchlab.kermit.Kermit
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), KoinComponent {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val log: Kermit by inject { parametersOf("MainActivity") }

    private val viewModel: BreedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = MainAdapter { viewModel.updateBreedFavorite(it) }

        collectDataStateFlow(
            {
                /* Display loading view */
            },
            { breedList ->
                /* Display success view */
                log.v { "List submitted to adapter" }
                adapter.submitList(breedList)
            },
            { exception ->
                /* Display error view */
                log.e { "Error displayed: $exception" }
                Snackbar.make(
                    binding.breedList,
                    exception.toString(),
                    Snackbar.LENGTH_SHORT
                ).show()
            },
            {
                /* Display empty response view */
            }
        )

        binding.breedList.adapter = adapter
        binding.breedList.layoutManager = LinearLayoutManager(this)

        viewModel.getBreeds()
    }

    private fun collectDataStateFlow(
        onLoading: () -> Unit,
        onSuccess: (List<Breed>) -> Unit,
        onError: (String) -> Unit,
        onEmpty: () -> Unit,
    ) {
        lifecycleScope.launch {
            viewModel.breedStateFlow.collect {
                dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        onSuccess(dataState.data.allItems)
                    }
                    is DataState.Error -> {
                        onError(dataState.exception)
                    }
                    DataState.Empty -> {
                        onEmpty()
                    }
                    DataState.Loading -> {
                        onLoading()
                    }
                }
            }
        }
    }
}
