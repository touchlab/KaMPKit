/*
 * KaMPKit OHOS — Kotlin/Native business logic (ohosArm64 root package).
 *
 * Bridge contract (generic, no business naming):
 *   ArkTS → Kotlin : action(type, payloadJson) / httpResponse(body) / httpError(errorJson)
 *   Kotlin → ArkTS : subscribeEvents(callback(eventJson))
 *
 * All business logic lives here. The C++ NAPI layer is a dumb JSON pipe.
 * JSON parsing uses co.touchlab.kampkit.json.JSONObject (ported from KuiklyUI, zero deps).
 */

import co.touchlab.kampkit.json.JSONObject
import co.touchlab.kampkit.json.JSONException
import kotlinx.cinterop.*

// ── shared state ──────────────────────────────────────────────────────────────

@OptIn(ExperimentalForeignApi::class)
private var g_eventCallback: CPointer<CFunction<(CPointer<ByteVar>?) -> Unit>>? = null

private var lastBreedNames: List<String> = emptyList()
private val favorites = mutableMapOf<String, Boolean>()

// ── emit helpers ──────────────────────────────────────────────────────────────

@OptIn(ExperimentalForeignApi::class)
private fun emit(eventJson: String) {
    val cb = g_eventCallback ?: return
    memScoped {
        val ptr: CPointer<ByteVar> = eventJson.cstr.getPointer(this)
        cb.invoke(ptr)
    }
}

// TODO: BreedViewState is serialized to JSON here and deserialized again in ArkTS parseEvent().
//  This is an unnecessary round-trip. A better approach would be a typed shared memory or
//  direct struct mapping via NAPI (napi_create_object / napi_set_named_property) so ArkTS
//  receives a native JS object without any JSON encoding overhead.
private fun emitState(stateJson: String) =
    emit("""{"type":"state","payload":$stateJson}""")

private fun emitContent(isLoading: Boolean) {
    if (lastBreedNames.isEmpty()) {
        emitState("""{"kind":"Empty","isLoading":false}""")
        return
    }
    val breeds = lastBreedNames.mapIndexed { i, name ->
        val fav = favorites[name] ?: false
        """{"id":${i + 1},"name":${JSONObject.quote(name)},"favorite":$fav}"""
    }.joinToString(",")
    emitState("""{"kind":"Content","breeds":[$breeds],"isLoading":$isLoading}""")
}

// ── business logic ────────────────────────────────────────────────────────────

private fun handleInit() {
    emitState("""{"kind":"Initial","isLoading":true}""")
}

private fun handleBreedsLoaded(body: String?) {
    if (body.isNullOrBlank()) {
        emitState("""{"kind":"Error","error":"Empty response","isLoading":false}""")
        return
    }
    try {
        // Raw HTTP body from ArkTS — parse Dog API format once, here, no intermediate serialization
        // Format: {"message":{"breedName":[],...},"status":"success"}
        val message = JSONObject(body).optJSONObject("message")
        if (message == null || message.length() == 0) {
            emitState("""{"kind":"Empty","isLoading":false}""")
            return
        }
        lastBreedNames = message.keySet().sorted()
        emitContent(false)
    } catch (_: JSONException) {
        emitState("""{"kind":"Error","error":"Parse error","isLoading":false}""")
    }
}

private fun handleBreedsError(errorJson: String?) {
    val msg = if (!errorJson.isNullOrBlank()) {
        try { JSONObject(errorJson).optString("error", "Network error") }
        catch (_: JSONException) { "Network error" }
    } else "Network error"
    emitState("""{"kind":"Error","error":${JSONObject.quote(msg.take(200))},"isLoading":false}""")
}

private fun handleRefresh() {
    emitContent(true)
}

private fun handleUpdateFavorite(paramsJson: String?) {
    if (paramsJson.isNullOrBlank()) return
    try {
        val obj = JSONObject(paramsJson)
        val name = obj.optString("name").takeIf { it.isNotEmpty() } ?: return
        // ArkTS already sends the desired new state (!breed.favorite), store it directly
        favorites[name] = obj.optBoolean("favorite", false)
        emitContent(false)
    } catch (_: JSONException) { /* ignore */ }
}

// ── exported functions (kotlin.root.*) ────────────────────────────────────────

/** Raw HTTP body from ArkTS → Kotlin parses Dog API format, manages state, emits BreedViewState. */
@OptIn(ExperimentalForeignApi::class)
fun httpResponse(body: CPointer<ByteVar>?) {
    handleBreedsLoaded(body?.toKString())
}

/** HTTP failure info {"error":"..."} from ArkTS → Kotlin emits Error state. */
@OptIn(ExperimentalForeignApi::class)
fun httpError(json: CPointer<ByteVar>?) {
    handleBreedsError(json?.toKString())
}

/**
 * Lifecycle / user action from ArkTS.
 * type    : action name  ("init" | "refresh" | "updateFavorite")
 * payload : JSON string with action-specific data
 */
@OptIn(ExperimentalForeignApi::class)
fun action(type: CPointer<ByteVar>?, payload: CPointer<ByteVar>?) {
    val p = payload?.toKString()
    when (type?.toKString()) {
        "init"           -> handleInit()
        "refresh"        -> handleRefresh()
        "updateFavorite" -> handleUpdateFavorite(p)
    }
}

/** ArkTS subscribes once; Kotlin pushes all state changes as JSON events. */
@OptIn(ExperimentalForeignApi::class)
fun subscribeEvents(callback: CPointer<CFunction<(CPointer<ByteVar>?) -> Unit>>?) {
    g_eventCallback = callback
}
