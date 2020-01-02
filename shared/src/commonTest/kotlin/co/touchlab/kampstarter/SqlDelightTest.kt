package co.touchlab.kampstarter

import com.squareup.sqldelight.db.SqlDriver
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

expect fun testDbConnection(): SqlDriver

expect fun <T> runTest(block: suspend () -> T)

abstract class SqlDelightTest {

    private lateinit var dbHelper: DatabaseHelper

    suspend fun DatabaseHelper.insertBreed(name:String){
        insertBreeds(listOf(name))
    }

    @BeforeTest
    fun setup() = runTest{
        dbHelper = DatabaseHelper(testDbConnection())
        dbHelper.deleteAll()
        dbHelper.insertBreed("Beagle")
    }

    @Test
    fun `Select All Items Success`() = runTest {
        val breeds = dbHelper.selectAllItems().executeAsList()
        assertNotNull(breeds.find { it.name == "Beagle"},
            "Could not retrieve Breed")
    }

    @Test
    fun `Select Item by Id Success`() = runTest {
        val breeds = dbHelper.selectAllItems().executeAsList()
        val firstBreed = breeds.first()
        assertNotNull(dbHelper.selectById(firstBreed.id).executeAsOneOrNull(),
            "Could not retrieve Breed by Id")
    }

    @Test
    fun `Update Favorite Success`() = runTest {
        val breeds = dbHelper.selectAllItems().executeAsList()
        val firstBreed = breeds.first()
        dbHelper.updateFavorite(firstBreed.id,true)
        val newBreed = dbHelper.selectById(firstBreed.id).executeAsOneOrNull()
        assertNotNull(newBreed,
            "Could not retrieve Breed by Id")
        assertTrue(newBreed.isFavorited(),
            "Favorite Did Not Save")
    }

    @Test
    fun `Delete All Success`() = runTest {
        dbHelper.insertBreed("Poodle")
        dbHelper.insertBreed("Schnauzer")
        assertTrue(dbHelper.selectAllItems().executeAsList().isNotEmpty())
        dbHelper.deleteAll()
        assertTrue(dbHelper.selectAllItems().executeAsList().count() == 0,
            "Delete All did not work")
    }
}