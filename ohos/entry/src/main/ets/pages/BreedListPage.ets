/*
 * KaMPKit OHOS - 完整数据请求与展示：通过 BreedListBridge 使用 HTTP 数据源（或注入 NAPI）。
 * 流程：Loading → 请求 dog.ceo API → 解析 → Content 列表展示；支持刷新与收藏。
 */

import { breedListBridge, Breed, BreedViewState, BreedViewStateInitial } from '../bridge/BreedListBridge';

@Entry
@Component
struct BreedListPage {
  @State viewState: BreedViewState = { kind: 'Initial', isLoading: true } as BreedViewStateInitial;

  aboutToAppear(): void {
    breedListBridge.setStateCallback((state: BreedViewState) => {
      this.viewState = state;
    });
    breedListBridge.init();
  }

  private refresh(): void {
    breedListBridge.refresh();
  }

  private toggleFavorite(breed: Breed): void {
    breedListBridge.updateFavorite(breed);
  }

  build() {
    Column() {
      if (this.viewState.isLoading) {
        Text('Loading...')
          .fontSize(14)
          .margin({ top: 12 });
      }

      if (this.viewState.kind === 'Empty') {
        Text('Sorry, no doggos found')
          .fontSize(16)
          .margin({ top: 48 });
      } else if (this.viewState.kind === 'Error') {
        Text(this.viewState.error)
          .fontSize(16)
          .fontColor(Color.Red)
          .margin({ top: 48 });
      } else if (this.viewState.kind === 'Content') {
        List() {
          ForEach(this.viewState.breeds, (breed: Breed) => {
            ListItem() {
              Row() {
                Text(breed.name)
                  .fontSize(16)
                  .layoutWeight(1)
                  .padding({ left: 12, right: 8, top: 10, bottom: 10 });
                Text(breed.favorite ? '♥' : '♡')
                  .fontSize(22)
                  .fontColor(breed.favorite ? Color.Red : Color.Gray)
                  .padding({ left: 8, right: 12 })
                  .onClick(() => this.toggleFavorite(breed));
              }
              .width('100%')
            }
            .onClick(() => this.toggleFavorite(breed));
          }, (breed: Breed) => `${breed.id}_${breed.favorite}`);
        }
        .layoutWeight(1)
        .divider({ strokeWidth: 1, color: '#eee' });
      }

      Button('Refresh')
        .margin(16)
        .onClick(() => this.refresh());
    }
    .width('100%')
    .height('100%');
  }
}
