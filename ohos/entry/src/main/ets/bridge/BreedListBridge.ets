/*
 * OHOS 桥接：优先使用 NAPI（libshared）数据源；未注入时使用 ArkTS HTTP 数据源，实现完整请求→展示流程。
 */
import { dogApiDataSource } from '../data/DogApiDataSource';

// 与 shared Breed / BreedViewState 同构，便于 NAPI 序列化
export interface Breed {
  id: number;
  name: string;
  favorite: boolean;
}

export interface BreedViewStateInitial { kind: 'Initial'; isLoading: boolean; }
export interface BreedViewStateEmpty { kind: 'Empty'; isLoading: boolean; }
export interface BreedViewStateContent { kind: 'Content'; breeds: Breed[]; isLoading: boolean; }
export interface BreedViewStateError { kind: 'Error'; error: string; isLoading: boolean; }
export type BreedViewState =
  | BreedViewStateInitial
  | BreedViewStateEmpty
  | BreedViewStateContent
  | BreedViewStateError;

/** NAPI 侧需实现的接口（由 commonMain 编译出的 so 通过 NAPI 暴露后注入） */
export interface IBreedListNativeImpl {
  initBreedList(): void;
  subscribeBreedState(callback: (stateJson: string) => void): void;
  refreshBreeds(): void;
  updateBreedFavorite(id: number, name: string, favorite: boolean): void;
}

interface ParsedState {
  kind: string;
  isLoading?: boolean;
  error?: string;
  breeds?: Breed[];
}

function parseState(stateJson: string): BreedViewState {
  try {
    const obj = JSON.parse(stateJson) as ParsedState;
    const kind = obj.kind;
    const isLoading = obj.isLoading ?? false;
    if (kind === 'Initial') {
      const s: BreedViewStateInitial = { kind: 'Initial', isLoading };
      return s;
    }
    if (kind === 'Empty') {
      const s: BreedViewStateEmpty = { kind: 'Empty', isLoading };
      return s;
    }
    if (kind === 'Error') {
      const s: BreedViewStateError = { kind: 'Error', error: obj.error ?? '', isLoading };
      return s;
    }
    if (kind === 'Content' && obj.breeds) {
      const s: BreedViewStateContent = { kind: 'Content', breeds: obj.breeds, isLoading };
      return s;
    }
  } catch (_) {
  }
  const fallback: BreedViewStateError = { kind: 'Error', error: 'Invalid state', isLoading: false };
  return fallback;
}

/** 全局桥接：NAPI 注入时用 commonMain 数据；未注入时用 ArkTS HTTP 数据源，完整请求→展示 */
class BreedListBridge {
  private nativeImpl: IBreedListNativeImpl | null = null;
  private stateCallback: (state: BreedViewState) => void = () => {};

  /** 由 NAPI（libshared.so / libkuikly_entry.so）提供实现后注入；不注入则使用 HTTP 数据源 */
  setNativeImpl(impl: IBreedListNativeImpl | null): void {
    this.nativeImpl = impl;
  }

  /** 设置状态回调，页面据此刷新 UI */
  setStateCallback(callback: (state: BreedViewState) => void): void {
    this.stateCallback = callback;
  }

  /** 初始化：有 native 则订阅 NAPI；否则用 HTTP 数据源发起完整请求→展示 */
  init(): void {
    if (this.nativeImpl) {
      this.nativeImpl.subscribeBreedState((stateJson: string) => {
        this.stateCallback(parseState(stateJson));
      });
      this.nativeImpl.initBreedList();
      return;
    }
    dogApiDataSource.setStateCallback(this.stateCallback);
    dogApiDataSource.load();
  }

  refresh(): void {
    if (this.nativeImpl) {
      this.nativeImpl.refreshBreeds();
    } else {
      dogApiDataSource.refresh();
    }
  }

  updateFavorite(breed: Breed): void {
    if (this.nativeImpl) {
      this.nativeImpl.updateBreedFavorite(breed.id, breed.name, !breed.favorite);
    } else {
      dogApiDataSource.updateFavorite(breed.id, breed.name, !breed.favorite);
    }
  }
}

export const breedListBridge = new BreedListBridge();
