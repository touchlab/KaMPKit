package co.touchlab.kampkit.sqldelight

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.TransactionWithoutReturn
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.withContext

suspend fun Transacter.transactionWithContext(
    coroutineContext: CoroutineContext,
    noEnclosing: Boolean = false,
    body: TransactionWithoutReturn.() -> Unit
) {
    withContext(coroutineContext) {
        this@transactionWithContext.transaction(noEnclosing) {
            body()
        }
    }
}
