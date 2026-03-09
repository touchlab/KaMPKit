import { dogApiDataSource, Breed, BreedViewState, BreedViewStateInitial } from '../data/DogApiDataSource';

@Entry
@Component
struct BreedListPage {
  @State viewState: BreedViewState = { kind: 'Initial', isLoading: true } as BreedViewStateInitial;

  aboutToAppear(): void {
    dogApiDataSource.setStateCallback((state: BreedViewState) => {
      this.viewState = state;
    });
    dogApiDataSource.load();
  }

  build() {
    Column() {
      if (this.viewState.isLoading) {
        Text('Loading...')
          .fontSize(14)
          .margin({ top: 12 })
          .align(Alignment.Center);
      }

      if (this.viewState.kind === 'Empty') {
        Text('Sorry, no doggos found')
          .fontSize(16)
          .margin({ top: 48 });
      } else if (this.viewState.kind === 'Error') {
        Text(this.viewState.error)
          .fontSize(16)
          .fontColor(Color.Black)
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
                if (breed.favorite) {
                  Image($r('app.media.heart_fill'))
                    .width(32)
                    .height(32)
                } else {
                  Image($r('app.media.heart'))
                    .width(32)
                    .height(32)
                }
              }
              .padding({ left: 8, right: 12 })
              .width('100%')
            }
            .onClick(() => dogApiDataSource.updateFavorite(breed.id, breed.name, !breed.favorite));
          }, (breed: Breed) => `${breed.id}_${breed.favorite}`);
        }
        .layoutWeight(1)
        .divider({ strokeWidth: 1, color: '#eee' })
        .margin(30)
      }

      Button('Refresh')
        .margin(16)
        .onClick(() => dogApiDataSource.refresh());
    }
    .width('100%')
    .height('100%');
  }
}
