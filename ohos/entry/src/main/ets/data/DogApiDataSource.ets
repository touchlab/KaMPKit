/*
 * KaMPKit OHOS — ArkTS data source.
 *
 * HTTP execution: ArkTS (@kit.NetworkKit), like KuiklyUI's KRNetworkModule.ets
 * Business logic:  Kotlin/Native (libshared.so), via generic JSON bridge
 *
 * Bridge contract:
 *   callNative(method, paramsJson)        ArkTS → Kotlin
 *   subscribeNativeEvent(callback)        Kotlin → ArkTS  (state events)
 */
import http from '@ohos.net.http';
import nativeModule from 'libkuikly_entry.so';

export interface Breed {
  id: number;
  name: string;
  favorite: boolean;
}

export interface BreedViewStateInitial { kind: 'Initial'; isLoading: boolean; }
export interface BreedViewStateEmpty   { kind: 'Empty';   isLoading: boolean; }
export interface BreedViewStateContent { kind: 'Content'; breeds: Breed[]; isLoading: boolean; }
export interface BreedViewStateError   { kind: 'Error';   error: string;   isLoading: boolean; }
export type BreedViewState =
  | BreedViewStateInitial
  | BreedViewStateEmpty
  | BreedViewStateContent
  | BreedViewStateError;

const DOG_API_URL = 'https://dog.ceo/api/breeds/list/all';

// TODO: parseEvent() deserializes JSON that Kotlin just serialized in emitState() — unnecessary
//  round-trip. Future improvement: use napi_create_object in C++ to pass a typed JS object
//  directly from Kotlin/Native, eliminating the JSON encoding/decoding overhead entirely.
function parseEvent(eventJson: string): BreedViewState | null {
  try {
    const event = JSON.parse(eventJson) as Record<string, Object>;
    if (event['type'] !== 'state') return null;
    const p = event['payload'] as Record<string, Object>;
    const kind = p['kind'] as string;
    const isLoading = (p['isLoading'] as boolean) ?? false;
    if (kind === 'Initial') return { kind: 'Initial', isLoading } as BreedViewStateInitial;
    if (kind === 'Empty')   return { kind: 'Empty',   isLoading } as BreedViewStateEmpty;
    if (kind === 'Error')   return { kind: 'Error', error: (p['error'] as string) ?? '', isLoading } as BreedViewStateError;
    if (kind === 'Content') return { kind: 'Content', breeds: p['breeds'] as Breed[], isLoading } as BreedViewStateContent;
  } catch (_) {}
  return null;
}

/** Execute HTTP, pass raw body to Kotlin — no intermediate parse/re-serialize. */
function fetchBreeds(onSuccess: (body: string) => void, onError: (msg: string) => void): void {
  const req = http.createHttp();
  req.request(DOG_API_URL, {
    method: http.RequestMethod.GET,
    connectTimeout: 30000,
    readTimeout: 30000,
  }, (err, data) => {
    req.destroy();
    if (err)                       { onError(err.message ?? 'HTTP error'); return; }
    if (data.responseCode !== 200) { onError(`HTTP ${data.responseCode}`); return; }
    const raw = data.result;
    onSuccess(typeof raw === 'string' ? raw : JSON.stringify(raw));
  });
}

export class DogApiDataSource {
  private stateCallback: (state: BreedViewState) => void = () => {};

  setStateCallback(callback: (state: BreedViewState) => void): void {
    this.stateCallback = callback;
    nativeModule.subscribeEvents((eventJson: string) => {
      const state = parseEvent(eventJson);
      if (state) this.stateCallback(state);
    });
  }

  load(): void {
    nativeModule.action('init', '{}');
    fetchBreeds(
      (body) => nativeModule.httpResponse(body),
      (msg)  => nativeModule.httpError(JSON.stringify({ error: msg })),
    );
  }

  refresh(): void {
    nativeModule.action('refresh', '{}');
    fetchBreeds(
      (body) => nativeModule.httpResponse(body),
      (msg)  => nativeModule.httpError(JSON.stringify({ error: msg })),
    );
  }

  updateFavorite(id: number, name: string, favorite: boolean): void {
    nativeModule.action('updateFavorite', JSON.stringify({ id, name, favorite }));
  }
}

export const dogApiDataSource = new DogApiDataSource();
