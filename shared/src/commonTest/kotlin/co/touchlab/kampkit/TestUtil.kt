package co.touchlab.kampkit

import app.cash.turbine.ReceiveTurbine
import co.touchlab.kampkit.models.BreedViewState
import com.squareup.sqldelight.db.SqlDriver

internal expect fun testDbConnection(): SqlDriver

// There's a race condition where intermediate states can get missed if the next state comes too fast.
// This function addresses that by awaiting an item that may or may not be preceded by the specified other items
suspend fun ReceiveTurbine<BreedViewState>.awaitItemPrecededBy(vararg items: BreedViewState): BreedViewState {
    var nextItem = awaitItem()
    for (item in items) {
        if (item == nextItem) {
            nextItem = awaitItem()
        }
    }
    return nextItem
}
