package co.touchlab.kampkit.sqldelight

import com.squareup.sqldelight.Transacter
import com.squareup.sqldelight.TransactionWithoutReturn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

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
