//
//  BreedListView.swift
//  KaMPKitiOS
//
//  Created by Russell Wolf on 7/26/21.
//  Copyright © 2021 Touchlab. All rights reserved.
//

import SwiftUI
import shared

private let log = koin.loggerWithTag(tag: "BreedListScreen")

struct BreedListScreen: View {

    @State
    var viewModel: BreedViewModel?

    @State
    var breedState: BreedViewState = .Initial.shared

    var body: some View {
        BreedListContent(
            state: breedState,
            onBreedFavorite: { breed in
                Task {
                    try? await viewModel?.updateBreedFavorite(breed: breed)
                }
            },
            refresh: {
                Task {
                    try? await viewModel?.refreshBreeds()
                }
            }
        )
        .task {
            let viewModel = KotlinDependencies.shared.getBreedViewModel()
            await withTaskCancellationHandler(
                operation: {
                    self.viewModel = viewModel
                    Task {
                        try? await viewModel.activate()
                    }
                    for await breedState in viewModel.breedState {
                        self.breedState = breedState
                    }
                },
                onCancel: {
                    viewModel.clear()
                    self.viewModel = nil
                }
            )
        }
    }
}

struct BreedListContent: View {
    var state: BreedViewState
    var onBreedFavorite: (Breed) -> Void
    var refresh: () -> Void

    var body: some View {
        ZStack {
            VStack {
                switch onEnum(of: state) {
                case .content(let content):
                    List(content.breeds, id: \.id) { breed in
                        BreedRowView(breed: breed) {
                            onBreedFavorite(breed)
                        }
                    }
                case .error(let error):
                    Spacer()
                    Text(error.error)
                        .foregroundColor(.red)
                    Spacer()
                case .empty:
                    Spacer()
                    Text("Sorry, no doggos found")
                    Spacer()
                case .initial:
                    Spacer()
                }

                Button("Refresh") {
                    refresh()
                }
            }
            if state.isLoading { Text("Loading...") }
        }
    }
}

struct BreedRowView: View {
    var breed: Breed
    var onTap: () -> Void

    var body: some View {
        Button(action: onTap) {
            HStack {
                Text(breed.name)
                    .padding(4.0)
                Spacer()
                Image(systemName: (!breed.favorite) ? "heart" : "heart.fill")
                    .padding(4.0)
            }
        }
    }
}

struct BreedListScreen_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            BreedListContent(
                state: .Content(breeds: [
                    Breed(id: 0, name: "appenzeller", favorite: false),
                    Breed(id: 1, name: "australian", favorite: true)
                ]),
                onBreedFavorite: { _ in },
                refresh: {}
            )
            BreedListContent(
                state: .Initial.shared,
                onBreedFavorite: { _ in },
                refresh: {}
            )
            BreedListContent(
                state: .Empty(),
                onBreedFavorite: { _ in },
                refresh: {}
            )
            BreedListContent(
                state: .Error(error: "Something went wrong!"),
                onBreedFavorite: { _ in },
                refresh: {}
            )
        }
    }
}
