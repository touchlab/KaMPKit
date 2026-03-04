/**
 * KaMPKit OHOS NAPI - 严格按 KuiklyBase 架构。
 * initKuikly：Kuikly 标准入口，链接 libshared.so 时调用并返回非 0。
 * 以下为 BreedList 最小可运行 stub（无 libshared 时使用）。
 */
export function initKuikly(): number;
export function initBreedList(): void;
export function subscribeBreedState(callback: (stateJson: string) => void): void;
export function refreshBreeds(): void;
export function updateBreedFavorite(id: number, name: string, favorite: boolean): void;