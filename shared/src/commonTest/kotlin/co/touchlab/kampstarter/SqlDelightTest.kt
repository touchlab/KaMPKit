package co.touchlab.kampstarter

import com.squareup.sqldelight.db.SqlDriver
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull

expect fun testDbConnection(): SqlDriver

expect fun <T> runTest(block: suspend () -> T)

abstract class SqlDelightTest {

    private lateinit var dbHelper: DatabaseHelper

    @BeforeTest
    fun setup(){
        dbHelper = DatabaseHelper(testDbConnection())
    }

    @Test
    fun `Get Boolean Setting Success`() = runTest {
        dbHelper.insertBreed("Beagle")
        val breeds = dbHelper.selectAllItems().executeAsList()
        assertNotNull(breeds.find { it.name == "Beagle"},"Could not retrieve Breed")
    }
}