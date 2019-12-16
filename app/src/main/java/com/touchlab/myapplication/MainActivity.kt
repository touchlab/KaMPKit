package com.touchlab.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.touchlab.shared.createApplicationScreenMessage
import com.touchlab.shared.ktorExample.KtorApiImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text_view.text = createApplicationScreenMessage()
        performNetworkRequest()
    }

    private fun performNetworkRequest() {
        KtorApiImpl.getJsonFromApi{ result ->
            Log.i("TAG",result)
        }
    }
}
