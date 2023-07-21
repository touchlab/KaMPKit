package co.touchlab.kampkit.utils

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.format

actual class Strings {
    actual fun get(id: StringResource, args: List<Any>) = when (args.isEmpty()) {
        true -> StringDesc.Resource(id).localized()
        false -> id.format(*args.toTypedArray()).localized()
    }
}
