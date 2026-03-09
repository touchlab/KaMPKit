/*
 * KaMPKit OHOS NAPI — network-layer bridge (no business logic).
 *
 * Exposes four generic network/event functions:
 *   httpResponse(body)          HTTP success body (ArkTS → Kotlin)
 *   httpError(json)             HTTP failure info (ArkTS → Kotlin)
 *   action(type, payload)       Lifecycle/user action (ArkTS → Kotlin)
 *   subscribeEvents(callback)   Event stream registration (Kotlin → ArkTS)
 *
 * All payloads are plain JSON strings. Business logic lives in Kotlin only.
 */
#include "napi/native_api.h"
#include <string>

#ifdef KAMPKIT_USE_LIBSHARED
#include "libshared_api.h"

// Called from the JS thread (synchronously inside an active NAPI call),
// so g_env is always valid and we can call the JS callback directly.
static napi_env  g_env          = nullptr;
static napi_ref  g_callback_ref = nullptr;

// Kotlin/Native exports subscribeEvents(void* callback); use a named static function
// so we can reinterpret_cast it to void* — lambdas cannot convert to void* directly.
static void OnKotlinEvent(char* eventJson) {
    if (!g_env || !g_callback_ref || !eventJson) return;
    napi_value js_cb = nullptr;
    napi_get_reference_value(g_env, g_callback_ref, &js_cb);
    if (!js_cb) return;
    napi_value arg;
    napi_create_string_utf8(g_env, eventJson, NAPI_AUTO_LENGTH, &arg);
    napi_value global, result;
    napi_get_global(g_env, &global);
    napi_call_function(g_env, global, js_cb, 1, &arg, &result);
}
#endif

static std::string GetStringArg(napi_env env, napi_value val) {
    size_t len = 0;
    napi_get_value_string_utf8(env, val, nullptr, 0, &len);
    std::string s(len + 1, '\0');
    napi_get_value_string_utf8(env, val, s.data(), len + 1, &len);
    s.resize(len);
    return s;
}

// ── httpResponse(body: string) ────────────────────────────────────────────────
static napi_value HttpResponse(napi_env env, napi_callback_info info) {
#ifdef KAMPKIT_USE_LIBSHARED
    size_t argc = 1; napi_value args[1];
    napi_get_cb_info(env, info, &argc, args, nullptr, nullptr);
    if (argc < 1) return nullptr;
    auto body = GetStringArg(env, args[0]);
    auto* sym = libshared_symbols();
    if (sym && sym->kotlin.root.httpResponse)
        sym->kotlin.root.httpResponse(body.data());
#else
    (void)env; (void)info;
#endif
    return nullptr;
}

// ── httpError(json: string) ───────────────────────────────────────────────────
static napi_value HttpError(napi_env env, napi_callback_info info) {
#ifdef KAMPKIT_USE_LIBSHARED
    size_t argc = 1; napi_value args[1];
    napi_get_cb_info(env, info, &argc, args, nullptr, nullptr);
    if (argc < 1) return nullptr;
    auto json = GetStringArg(env, args[0]);
    auto* sym = libshared_symbols();
    if (sym && sym->kotlin.root.httpError)
        sym->kotlin.root.httpError(json.data());
#else
    (void)env; (void)info;
#endif
    return nullptr;
}

// ── action(type: string, payload: string) ─────────────────────────────────────
static napi_value Action(napi_env env, napi_callback_info info) {
#ifdef KAMPKIT_USE_LIBSHARED
    size_t argc = 2; napi_value args[2];
    napi_get_cb_info(env, info, &argc, args, nullptr, nullptr);
    if (argc < 2) return nullptr;
    auto type    = GetStringArg(env, args[0]);
    auto payload = GetStringArg(env, args[1]);
    auto* sym = libshared_symbols();
    if (sym && sym->kotlin.root.action)
        sym->kotlin.root.action(type.data(), payload.data());
#else
    (void)env; (void)info;
#endif
    return nullptr;
}

// ── subscribeEvents(callback: (eventJson: string) => void) ───────────────────
static napi_value SubscribeEvents(napi_env env, napi_callback_info info) {
#ifdef KAMPKIT_USE_LIBSHARED
    size_t argc = 1; napi_value args[1];
    napi_get_cb_info(env, info, &argc, args, nullptr, nullptr);
    if (argc < 1) return nullptr;

    // Release previous reference if re-subscribing
    if (g_callback_ref) { napi_delete_reference(env, g_callback_ref); g_callback_ref = nullptr; }

    g_env = env;
    napi_create_reference(env, args[0], 1, &g_callback_ref);

    auto* sym = libshared_symbols();
    if (sym && sym->kotlin.root.subscribeEvents) {
        sym->kotlin.root.subscribeEvents(reinterpret_cast<void*>(OnKotlinEvent));
    }
#else
    (void)env; (void)info;
#endif
    return nullptr;
}

// ── module init ───────────────────────────────────────────────────────────────
EXTERN_C_START
static napi_value Init(napi_env env, napi_value exports) {
    napi_property_descriptor desc[] = {
        {"httpResponse",   nullptr, HttpResponse,   nullptr, nullptr, nullptr, napi_default, nullptr},
        {"httpError",      nullptr, HttpError,       nullptr, nullptr, nullptr, napi_default, nullptr},
        {"action",         nullptr, Action,          nullptr, nullptr, nullptr, napi_default, nullptr},
        {"subscribeEvents",nullptr, SubscribeEvents, nullptr, nullptr, nullptr, napi_default, nullptr},
    };
    napi_define_properties(env, exports, sizeof(desc) / sizeof(desc[0]), desc);
    return exports;
}
EXTERN_C_END

static napi_module entry_module = {
    .nm_version = 1, .nm_flags = 0, .nm_filename = nullptr,
    .nm_register_func = Init, .nm_modname = "kuikly_entry",
    .nm_priv = nullptr, .reserved = {0},
};

extern "C" __attribute__((constructor)) void RegisterKuikly_EntryModule(void) {
    napi_module_register(&entry_module);
}
