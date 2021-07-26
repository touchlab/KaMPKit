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
        return ZStack {
            VStack {
                if let breeds = observableModel.breeds {
                    List(breeds, id: \.id) { breed in
                        BreedRowView(breed: breed) {
                            observableModel.onBreedFavorite(breed)
                        }
                    }
                }
                if let error = observableModel.error {
                    Text(error)
                        .foregroundColor(.red)
                }
                Button("Refresh") {
                    observableModel.refresh()
                }
            }
            if (observableModel.loading) {
                Text("Loading...")
            }
        }
        .onAppear(perform: {
            observableModel.activate()
        })
        .onDisappear(perform: {
            observableModel.deactivate()
        })
    }
}

struct BreedRowView: View {
    var breed: Breed
    var onTap: () -> Void
    
    var body: some View {
        HStack() {
            Text(breed.name)
                .padding(4.0)
            Spacer()
            Image(systemName: (breed.favorite == 0) ? "heart" : "heart.fill")
                .padding(4.0)
        }.onTapGesture {
            onTap()
        }
    }
}
