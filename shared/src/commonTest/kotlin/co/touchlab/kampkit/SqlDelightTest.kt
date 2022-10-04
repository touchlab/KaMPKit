package co.touchlab.kampkit

import co.touchlab.kermit.Logger
import co.touchlab.kermit.StaticConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class SqlDelightTest {

    private lateinit var dbHelper: DatabaseHelper

    private suspend fun DatabaseHelper.insertBreed(name: String) {
        insertBreeds(listOf(name))
    }

    @BeforeTest
    fun setup() = runTest {
        dbHelper = DatabaseHelper(
            testDbConnection(),
            Logger(StaticConfig()),
            Dispatchers.Default
        )
        dbHelper.deleteAll()
        dbHelper.insertBreed("Beagle")
    }

    @Test
    fun `Select All Items Success`() = runTest {
        val breeds = dbHelper.selectAllItems().first()
        assertNotNull(
            breeds.find { it.name == "Beagle" },
            "Could not retrieve Breed"
        )
    }

    @Test
    fun `Select Item by Id Success`() = runTest {
        val breeds = dbHelper.selectAllItems().first()
        val firstBreed = breeds.first()
        assertNotNull(
            dbHelper.selectById(firstBreed.id),
            "Could not retrieve Breed by Id"
        )
    }

    @Test
    fun `Update Favorite Success`() = runTest {
        val breeds = dbHelper.selectAllItems().first()
        val firstBreed = breeds.first()
        dbHelper.updateFavorite(firstBreed.id, true)
        val newBreed = dbHelper.selectById(firstBreed.id).first().first()
        assertNotNull(
            newBreed,
            "Could not retrieve Breed by Id"
        )
        assertTrue(
            newBreed.favorite,
            "Favorite Did Not Save"
        )
    }

    @Test
    fun `Delete All Success`() = runTest {
        dbHelper.insertBreed("Poodle")
        dbHelper.insertBreed("Schnauzer")
        assertTrue(dbHelper.selectAllItems().first().isNotEmpty())
        dbHelper.deleteAll()

        assertTrue(
            dbHelper.selectAllItems().first().count() == 0,
            "Delete All did not work"
        )
    }
}
