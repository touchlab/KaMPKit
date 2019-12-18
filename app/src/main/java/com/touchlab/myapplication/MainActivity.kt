package com.touchlab.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import co.touchlab.kampstarter.db.Items
import co.touchlab.kampstarter.db.KampstarterDb
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.ExperimentalListener
import com.russhwolf.settings.SettingsListener
import com.russhwolf.settings.boolean
import com.squareup.sqldelight.Query
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.touchlab.shared.DatabaseHelper

import com.touchlab.shared.createApplicationScreenMessage
import com.touchlab.shared.ktorExample.KtorApiImpl
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text_view.text = createApplicationScreenMessage()
        getDatabaseRows()
        performNetworkRequest()
        getSettings()
    }

    private fun getDatabaseRows(){
        val dbHelper = DatabaseHelper(AndroidSqliteDriver(KampstarterDb.Schema, this, "KampStarterDb"))
        dbHelper.insertItem(1,"Test")
        dbHelper.insertItem(2,"Test2")
        val queries: Query<Items> = dbHelper.selectAllItems()
        val items:List<Items> = queries.executeAsList()
        Log.i(TAG,items.toString())
    }

    private fun performNetworkRequest() {
        KtorApiImpl.getJsonFromApi{ result ->
            Log.i(TAG,result)
        }
    }

    private fun getSettings(){
        val settings = AndroidSettings.Factory(this).create("KAMPSTARTER_SETTINGS")
        settings.putBoolean("TEMP",true)
        val temp = settings.getBoolean("TEMP")
        Log.i(TAG,temp.toString())
    }
}
