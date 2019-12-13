package com.touchlab.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.touchlab.shared.NetworkHandler
import com.touchlab.shared.createApplicationScreenMessage
import com.touchlab.shared.ktorExample.KtorApiImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text_view.text = createApplicationScreenMessage()
        NetworkHandler.getKtorExample()
    }
}
