package com.touchlab.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.touchlab.kampstarter.db.Items
import co.touchlab.kampstarter.db.KampstarterDb
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.android.AndroidSqliteDriver
import co.touchlab.kmp.DatabaseHelper

import co.touchlab.kmp.SampleModel

class MainActivity : AppCompatActivity() {

    private lateinit var model:SampleModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = SampleModel()

        model.performNetworkRequest {result ->
            Log.i("MainActivity", result)
        }
//        text_view.text = createApplicationScreenMessage()
        getDatabaseRows()

    }

    private fun getDatabaseRows(){
        val dbHelper = DatabaseHelper(
            AndroidSqliteDriver(
                KampstarterDb.Schema,
                this,
                "KampStarterDb"
            )
        )
        dbHelper.insertItem(1,"Test")
        dbHelper.insertItem(2,"Test2")
        val queries: Query<Items> = dbHelper.selectAllItems()
        val items:List<Items> = queries.executeAsList()
        Log.i("DB",items.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        model.onDestroy()
    }
}
