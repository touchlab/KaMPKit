/*
 * KaMPKit OHOS - 严格按 KuiklyBase 架构。
 * BreedList 为最小可运行 stub（与 BreedListBridge 契约一致），真实数据由 libshared.so + initKuikly 提供。
 */
#include <napi/native_api.h>
#include <hilog/log.h>
#include <string>
#include <map>
#include <thread>
#include <mutex>

static napi_threadsafe_function safe_func = nullptr;
static napi_env g_env = nullptr;
static std::mutex g_mutex;
static std::map<int, bool> g_favorites;  // id -> favorite

static const char *STUB_LOADING_JSON = "{\"kind\":\"Initial\",\"isLoading\":true}";
static const char *STUB_CONTENT_JSON = "{\"kind\":\"Content\",\"isLoading\":false,\"breeds\":["
  "{\"id\":1,\"name\":\"affenpinscher\",\"favorite\":false},"
  "{\"id\":2,\"name\":\"african\",\"favorite\":false},"
  "{\"id\":3,\"name\":\"airedale\",\"favorite\":false},"
  "{\"id\":4,\"name\":\"akita\",\"favorite\":false},"
  "{\"id\":5,\"name\":\"appenzeller\",\"favorite\":false},"
  "{\"id\":6,\"name\":\"australian\",\"favorite\":false}"
 "]}";

static void call_state_callback(const char *state_json) {
  if (safe_func == nullptr || g_env == nullptr) return;
  napi_status status = napi_call_threadsafe_function(safe_func, (void*)state_json, napi_tsfn_blocking);
  (void)status;
}

static void emit_initial_then_content() {
  call_state_callback(STUB_LOADING_JSON);
  std::this_thread::sleep_for(std::chrono::milliseconds(400));
  call_state_callback(STUB_CONTENT_JSON);
}

static napi_value InitBreedList(napi_env env, napi_callback_info info) {
  g_env = env;
  std::thread([]() {
    std::this_thread::sleep_for(std::chrono::milliseconds(100));
    emit_initial_then_content();
  }).detach();
  return nullptr;
}

static void state_callback_js(napi_env env, napi_value js_callback, void *context, void *data) {
  const char *state_json = (const char *)data;
  if (state_json == nullptr || js_callback == nullptr) return;
  napi_value str_arg;
  napi_create_string_utf8(env, state_json, NAPI_AUTO_LENGTH, &str_arg);
  napi_value global;
  napi_get_global(env, &global);
  napi_value result;
  napi_call_function(env, global, js_callback, 1, &str_arg, &result);
}

static napi_value SubscribeBreedState(napi_env env, napi_callback_info info) {
  size_t argc = 1;
  napi_value args[1];
  napi_get_cb_info(env, info, &argc, args, nullptr, nullptr);
  if (argc < 1) return nullptr;

  {
    std::lock_guard<std::mutex> lock(g_mutex);
    if (safe_func != nullptr) {
      napi_release_threadsafe_function(safe_func, napi_tsfn_abort);
      safe_func = nullptr;
    }
    napi_value callback = args[0];
    napi_create_threadsafe_function(env, callback, nullptr, nullptr, 256, 1, nullptr, nullptr, nullptr, state_callback_js, &safe_func);
  }
  return nullptr;
}

static napi_value RefreshBreeds(napi_env env, napi_callback_info info) {
  call_state_callback("{\"kind\":\"Content\",\"isLoading\":true,\"breeds\":[]}");
  std::thread([]() {
    std::this_thread::sleep_for(std::chrono::milliseconds(600));
    call_state_callback(STUB_CONTENT_JSON);
  }).detach();
  return nullptr;
}

static napi_value UpdateBreedFavorite(napi_env env, napi_callback_info info) {
  size_t argc = 3;
  napi_value args[3];
  napi_get_cb_info(env, info, &argc, args, nullptr, nullptr);
  if (argc < 3) return nullptr;
  int32_t id = 0;
  size_t len = 0;
  napi_get_value_int32(env, args[0], &id);
  napi_get_value_string_utf8(env, args[1], nullptr, 0, &len);
  std::string name(len + 1, '\0');
  napi_get_value_string_utf8(env, args[1], &name[0], len + 1, &len);
  bool fav = false;
  napi_get_value_bool(env, args[2], &fav);
  {
    std::lock_guard<std::mutex> lock(g_mutex);
    g_favorites[id] = !fav;
  }
  std::thread(emit_initial_then_content).detach();
  return nullptr;
}

void register_breed_list_napi(napi_env env, napi_value exports) {
  napi_property_descriptor desc[] = {
    {"initBreedList", nullptr, InitBreedList, nullptr, nullptr, nullptr, napi_default, nullptr},
    {"subscribeBreedState", nullptr, SubscribeBreedState, nullptr, nullptr, nullptr, napi_default, nullptr},
    {"refreshBreeds", nullptr, RefreshBreeds, nullptr, nullptr, nullptr, napi_default, nullptr},
    {"updateBreedFavorite", nullptr, UpdateBreedFavorite, nullptr, nullptr, nullptr, napi_default, nullptr},
  };
  napi_define_properties(env, exports, sizeof(desc) / sizeof(desc[0]), desc);
}
