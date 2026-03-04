/**
 * OHOS 端完整数据请求：请求 Dog API，与 commonMain 使用同一接口与数据结构。
 * 用于在无 libshared 时实现完整「请求 → 解析 → 展示」流程。
 */
import http from '@ohos.net.http';
import { Breed, BreedViewState, BreedViewStateContent, BreedViewStateError, BreedViewStateInitial } from '../bridge/BreedListBridge';

const DOG_API_URL = 'https://dog.ceo/api/breeds/list/all';

/** API 返回的 message 为 breed 名 -> 子品种数组；用 Record 表示 */
interface DogApiResponse {
  status?: string;
  message?: Record<string, Array<string>>;
}

/** 内存收藏状态：id -> favorite */
const favoritesMap: Map<number, boolean> = new Map();

function parseBreedsFromResponse(message: Record<string, Array<string>>): Breed[] {
  const names = Object.keys(message).sort();
  const list: Breed[] = [];
  for (let i = 0; i < names.length; i++) {
    list.push({
      id: i + 1,
      name: names[i],
      favorite: favoritesMap.get(i + 1) ?? false
    });
  }
  return list;
}

function requestDogApi(): Promise<DogApiResponse> {
  return new Promise((resolve, reject) => {
    const httpRequest = http.createHttp();
    httpRequest.request(DOG_API_URL, {
      method: http.RequestMethod.GET,
      connectTimeout: 30000,
      readTimeout: 30000
    }, (err, data) => {
      httpRequest.destroy();
      if (err) {
        reject(err);
        return;
      }
      if (data.responseCode !== 200) {
        reject(new Error(`HTTP ${data.responseCode}`));
        return;
      }
      try {
        const raw = data.result;
        const result: DogApiResponse = typeof raw === 'string' ? JSON.parse(raw) as DogApiResponse : raw as DogApiResponse;
        resolve(result);
      } catch (e) {
        reject(e);
      }
    });
  });
}

export class DogApiDataSource {
  private stateCallback: (state: BreedViewState) => void = () => {};
  private lastBreeds: Breed[] = [];

  setStateCallback(callback: (state: BreedViewState) => void): void {
    this.stateCallback = callback;
  }

  /** 加载列表：先 Loading，再请求，成功 Content 或失败 Error */
  load(): void {
    const loadingState: BreedViewStateInitial = { kind: 'Initial', isLoading: true };
    this.stateCallback(loadingState);

    requestDogApi()
      .then((res) => {
        const message = res.message;
        if (!message || Object.keys(message).length === 0) {
          this.stateCallback({ kind: 'Empty', isLoading: false });
          return;
        }
        this.lastBreeds = parseBreedsFromResponse(message);
        const content: BreedViewStateContent = { kind: 'Content', breeds: this.lastBreeds, isLoading: false };
        this.stateCallback(content);
      })
      .catch((err: Error) => {
        const errorState: BreedViewStateError = {
          kind: 'Error',
          error: err.message ?? '请求失败',
          isLoading: false
        };
        this.stateCallback(errorState);
      });
  }

  /** 刷新：先 Loading，再重新请求 */
  refresh(): void {
    this.stateCallback({ kind: 'Initial', isLoading: true });
    this.load();
  }

  /** 切换收藏（内存），并立即回传更新后的 Content；生成全新数组与对象以触发界面即时刷新 */
  updateFavorite(id: number, _name: string, favorite: boolean): void {
    favoritesMap.set(id, favorite);
    if (this.lastBreeds.length === 0) return;
    const breeds: Breed[] = [];
    for (const b of this.lastBreeds) {
      breeds.push({
        id: b.id,
        name: b.name,
        favorite: b.id === id ? favorite : b.favorite
      });
    }
    this.lastBreeds = breeds;
    this.stateCallback({ kind: 'Content', breeds, isLoading: false });
  }
}

/** 单例，供 BreedListBridge 在无 native 时使用 */
export const dogApiDataSource = new DogApiDataSource();
