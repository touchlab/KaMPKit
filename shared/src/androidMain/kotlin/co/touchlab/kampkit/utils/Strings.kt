package co.touchlab.kampkit.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format

actual class Strings(private val context: Context) {
    actual fun get(id: StringResource, args: List<Any>): String {
        return when (args.isEmpty()) {
            true -> StringDesc.Resource(id).toString(context)
            false -> id.format(*args.toTypedArray()).toString(context)
        }
    }
}

/**
 * Get a string existing in the shared module.
 */
@Composable
fun getString(id: StringResource, vararg args: Any) = Strings(LocalContext.current).get(id, args.toList())
