//
//  BreedListView.swift
//  KaMPKitiOS
//
//  Created by Russell Wolf on 7/26/21.
//  Copyright © 2021 Touchlab. All rights reserved.
//

import SwiftUI
import shared

private let log = koin.get(objCClass: Kermit.self, parameter: "ViewController") as! Kermit

class ObservableBreedModel: ObservableObject {
    private var viewModel: NativeViewModel? = nil
    
    @Published
    var loading = false
    
    @Published
    var breeds: [Breed]? = nil
    
    @Published
    var error: String? = nil
    
    func activate() {
        viewModel = NativeViewModel { [weak self] dataState in
            self?.loading = dataState.loading
            self?.breeds = dataState.data?.allItems
            self?.error = dataState.exception
            
            if let breeds = dataState.data?.allItems {
                log.d(withMessage: {"View updating with \(breeds.count) breeds"})
            }
            if let errorMessage = dataState.exception {
                log.e(withMessage: {"Displaying error: \(errorMessage)"})
            }
        }
    }
    
    func deactivate() {
        viewModel?.onDestroy()
        viewModel = nil
    }
    
    func onBreedFavorite(_ breed: Breed) {
        viewModel?.updateBreedFavorite(breed: breed)
    }
    
    func refresh() {
        viewModel?.refreshBreeds(forced: true)
    }
}

struct BreedListScreen: View {
    @ObservedObject
    var observableModel = ObservableBreedModel()
    
    var body: some View {
        BreedListContent(
            loading: observableModel.loading,
            breeds: observableModel.breeds,
            error: observableModel.error,
            onBreedFavorite: { observableModel.onBreedFavorite($0) },
            refresh: { observableModel.refresh() }
            
        )
        .onAppear(perform: {
            observableModel.activate()
        })
        .onDisappear(perform: {
            observableModel.deactivate()
        })
    }
}

struct BreedListContent : View{
    var loading: Bool
    var breeds: [Breed]?
    var error: String?
    var onBreedFavorite: (Breed) -> Void
    var refresh: () -> Void
    
    var body: some View {
        ZStack {
            VStack {
                if let breeds = breeds {
                    List(breeds, id: \.id) { breed in
                        BreedRowView(breed: breed) {
                            onBreedFavorite(breed)
                        }
                    }
                }
                if let error = error {
                    Text(error)
                        .foregroundColor(.red)
                }
                Button("Refresh") {
                    refresh()
                }
            }
            if (loading) {
                Text("Loading...")
            }
        }
    }
}

struct BreedRowView: View {
    var breed: Breed
    var onTap: () -> Void
    
    var body: some View {
        Button(action: onTap) {
            HStack() {
                Text(breed.name)
                    .padding(4.0)
                Spacer()
                Image(systemName: (breed.favorite == 0) ? "heart" : "heart.fill")
                    .padding(4.0)
            }
        }
    }
}

struct BreedListScreen_Preview: PreviewProvider {
    static var previews: some View {
        BreedListContent(
            loading: false,
            breeds: [
                Breed(id: 0, name: "appenzeller", favorite: 0),
                Breed(id: 1, name: "australian", favorite: 1)
            ],
            error: nil,
            onBreedFavorite: { _ in },
            refresh: {}
        )
    }
}
