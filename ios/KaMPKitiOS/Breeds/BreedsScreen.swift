//
//  BreedListView.swift
//  KaMPKitiOS
//
//  Created by Russell Wolf on 7/26/21.
//  Copyright © 2021 Touchlab. All rights reserved.
//

import SwiftUI
import shared

struct BreedsScreen: View {
    @StateObject
    var viewModel = BreedsViewModel()

    var body: some View {
        BreedsContent(
            state: viewModel.state,
            onBreedFavorite: { viewModel.onBreedFavorite($0) },
            refresh: { viewModel.refresh() }
        )
        .onAppear(perform: {
            viewModel.subscribeState()
        })
        .onDisappear(perform: {
            viewModel.unsubscribeState()
        })
    }
}

struct BreedsContent: View {
    var state: BreedViewState
    var onBreedFavorite: (Breed) -> Void
    var refresh: () -> Void

    var body: some View {
        ZStack {
            VStack {
                List(state.breeds, id: \.id) { breed in
                    BreedRowView(breed: breed) {
                        onBreedFavorite(breed)
                    }
                }
                if let error = state.error {
                    Text(error)
                        .foregroundColor(.red)
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

struct BreedsScreen_Previews: PreviewProvider {
    static var previews: some View {
        BreedsContent(
            state: BreedViewState(
                breeds: [
                    Breed(id: 0, name: "appenzeller", favorite: false),
                    Breed(id: 1, name: "australian", favorite: true)
                ],
                error: nil,
                isLoading: false,
                isEmpty: false
            ),
            onBreedFavorite: { _ in },
            refresh: {}
        )
    }
}
