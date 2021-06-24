package co.touchlab.kampkit.android

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import co.touchlab.kampkit.android.adapter.MainAdapter
import co.touchlab.kampkit.android.databinding.ActivityMainBinding
import co.touchlab.kampkit.db.Breed
import co.touchlab.kampkit.models.DataState
import co.touchlab.kermit.Kermit
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), KoinComponent {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val log: Kermit by inject { parametersOf("MainActivity") }
    private val viewModel: BreedViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = MainAdapter { viewModel.updateBreedFavorite(it) }
        val swipeRefresh: SwipeRefreshLayout = binding.swipeRefresh
        val recyclerView: RecyclerView = binding.breedList

        swipeRefresh.setOnRefreshListener {
            viewModel.refreshBreeds(true)
        }

        collectDataStateFlow(
            {
                /* Display loading view */
                swipeRefresh.isRefreshing = true
            },
            { breedList ->
                /* Display success view */
                swipeRefresh.isRefreshing = false
                recyclerView.visibility = View.VISIBLE
                log.v { "View updating with ${breedList.size} breeds" }
                adapter.submitList(breedList)
            },
            { exception ->
                /* Display error view */
                swipeRefresh.isRefreshing = false
                recyclerView.visibility = View.GONE
                log.e { "Displaying error: $exception" }
                Snackbar.make(
                    binding.breedList,
                    exception,
                    Snackbar.LENGTH_SHORT
                ).show()
            },
            {
                /* Display empty response view */
                swipeRefresh.isRefreshing = false
            }
        )

        binding.breedList.adapter = adapter
        binding.breedList.layoutManager = LinearLayoutManager(this)

        viewModel.refreshBreeds()
    }

    private fun collectDataStateFlow(
        onLoading: () -> Unit,
        onSuccess: (List<Breed>) -> Unit,
        onError: (String) -> Unit,
        onEmpty: () -> Unit,
    ) {
        lifecycleScope.launch {
            viewModel.breedStateFlow.collect { dataState ->
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
