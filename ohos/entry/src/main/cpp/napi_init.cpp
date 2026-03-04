/*
 * KaMPKit OHOS - 严格按 KuiklyBase 架构：NAPI 入口。
 * 导出 initKuikly（Kuikly 标准入口，链接 libshared 时调用）；并导出 BreedList stub 供最小可运行。
 */
#include "napi/native_api.h"
#include "breed_list_napi.h"

#ifdef KAMPKIT_USE_LIBSHARED
#include "libshared_api.h"
#endif

EXTERN_C_START
static napi_value InitKuikly(napi_env env, napi_callback_info info) {
  int result = 0;
#ifdef KAMPKIT_USE_LIBSHARED
  libshared_ExportedSymbols* sym = libshared_symbols();
  if (sym && sym->kotlin.root.initKuikly)
    result = sym->kotlin.root.initKuikly();
#endif
  napi_value nresult;
  napi_create_int32(env, result, &nresult);
  return nresult;
}

static napi_value Init(napi_env env, napi_value exports) {
  napi_property_descriptor initKuiklyDesc = {
    "initKuikly", nullptr, InitKuikly, nullptr, nullptr, nullptr, napi_default, nullptr
  };
  napi_define_properties(env, exports, 1, &initKuiklyDesc);
  register_breed_list_napi(env, exports);
  return exports;
}
EXTERN_C_END

static napi_module entry_module = {
  .nm_version = 1,
  .nm_flags = 0,
  .nm_filename = nullptr,
  .nm_register_func = Init,
  .nm_modname = "kuikly_entry",
  .nm_priv = nullptr,
  .reserved = {0},
};

extern "C" __attribute__((constructor)) void RegisterKuikly_EntryModule(void) {
  napi_module_register(&entry_module);
}
