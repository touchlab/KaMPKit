package co.touchlab.kampstarter.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.touchlab.kampstarter.android.adapter.MainAdapter
import co.touchlab.kampstarter.models.BreedModel
import co.touchlab.kermit.Kermit
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), KoinComponent {
    companion object {
        val TAG = MainActivity::class.java.simpleName
    }
    private lateinit var adapter: MainAdapter
    private lateinit var model: BreedModel
    private lateinit var scope:MainScope

    private val log:Kermit by inject { parametersOf("MainActivity") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = BreedModel(errorUpdate = { errorMessage ->
            log.e { "Error displayed: $errorMessage" }
            Snackbar.make(breed_list, errorMessage, Snackbar.LENGTH_SHORT).show()
        })
        scope = MainScope(Dispatchers.Main, log)
        adapter = MainAdapter { scope.launch { model.updateBreedFavorite(it) } }

        breed_list.adapter = adapter
        breed_list.layoutManager = LinearLayoutManager(this)

        scope.launch {
            model.selectAllBreeds()
                .flowOn(Dispatchers.Default)
                .collect { itemData ->
                    log.v { "List submitted to adapter" }
                    adapter.submitList(itemData.allItems)
                }
        }
        scope.launch {
            model.getBreedsFromNetwork()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
        scope.onDestroy()
    }
}

    internal class MainScope(private val mainContext: CoroutineContext, private val log:Kermit) : CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = mainContext + job + exceptionHandler

        internal val job = SupervisorJob()
        private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            //printThrowable(throwable)
            showError(throwable)
        }

        //TODO: Some way of exposing this to the caller without trapping a reference and freezing it.
        private fun showError(t: Throwable) {
            log.e(throwable = t) { "Error in MainScope" }
        }

        fun onDestroy(){
            job.cancel()
        }
    }