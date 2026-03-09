/**
 * KaMPKit OHOS — network-layer bridge to Kotlin/Native (libshared.so).
 *
 * httpResponse(body)        Raw HTTP response body → Kotlin business logic
 * httpError(json)           {"error":"..."} → Kotlin business logic
 * action(type, payload)     Lifecycle/user action → Kotlin  (type is app-defined, payload is JSON)
 * subscribeEvents(callback) Kotlin pushes JSON events → ArkTS  e.g. {"type":"state","payload":{...}}
 */
export function httpResponse(body: string): void;
export function httpError(errorJson: string): void;
export function action(type: string, payload: string): void;
export function subscribeEvents(callback: (eventJson: string) => void): void;
