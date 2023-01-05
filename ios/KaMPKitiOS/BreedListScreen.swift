//
//  BreedListView.swift
//  KaMPKitiOS
//
//  Created by Russell Wolf on 7/26/21.
//  Copyright © 2021 Touchlab. All rights reserved.
//

import Combine
import SwiftUI
import shared

private let log = koin.loggerWithTag(tag: "BreedListScreen")

class BreedScreenPresenter: ObservableObject {
    private var viewModel: BreedViewModel?
    private var viewModelObserver: Kotlinx_coroutines_coreJob?

    @Published
    var loading = false

    @Published
    var breeds: [Breed]?

    @Published
    var error: String?

    @Published
    var vmActive = false

    func activate() {
        deactivate()
        viewModel = KotlinDependencies.shared.getBreedViewModel()
        viewModelObserver = viewModel?.subscribe(
            onEach: { [weak self] dogsState in
                switch dogsState {
                case let error as BreedViewState.Error:
                    self?.loading = false
                    self?.breeds = dogsState.breeds
                    self?.error = error.throwable.message
                case is BreedViewState.Success:
                    self?.loading = false
                    self?.breeds = dogsState.breeds
                    self?.error = nil
                case is BreedViewState.Loading:
                    self?.loading = true
                    self?.breeds = dogsState.breeds
                    self?.error = nil
                default:
                    self?.loading = false
                    self?.breeds = dogsState.breeds
                    self?.error = "Unknown state"
                }
        },
            onComplete: {
            print("onComplete")
        },
            onThrow: { throwable in
            print("onThrow: \(throwable)")
        }
        )
        vmActive = true
    }

    func deactivate() {
        viewModel?.clear()
        viewModel = nil
        viewModelObserver = nil
        vmActive = false
    }

    func onBreedFavorite(_ breed: Breed) {
        viewModel?.act(action: BreedViewAction.UpdateBreedFavorite(breed: breed))
    }

    func refresh() {
        viewModel?.act(action: BreedViewAction.RefreshBreeds())
    }
}

struct BreedListScreen: View {
    @StateObject
    var presenter = BreedScreenPresenter()

    var body: some View {
        VStack {
            Button(presenter.vmActive ? "Deactivate VM" : "Activate VM") {
                // demo purposes only: this sort of logic should be in the presenter
                // but really the presenter should not expose the VM or activation state
                if presenter.vmActive {
                    presenter.deactivate()
                } else {
                    presenter.activate()
                }
            }
            BreedListContent(
                loading: presenter.loading,
                breeds: presenter.breeds,
                error: presenter.error,
                onBreedFavorite: { presenter.onBreedFavorite($0) },
                refresh: { presenter.refresh() }
            )
            .onAppear(perform: {
                presenter.activate()
            })
            .onDisappear(perform: {
                presenter.deactivate()
            })
        }
    }
}

struct BreedListContent: View {
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
            if loading { Text("Loading...") }
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
        BreedListContent(
            loading: false,
            breeds: [
                Breed(id: 0, name: "appenzeller", favorite: false),
                Breed(id: 1, name: "australian", favorite: true)
            ],
            error: nil,
            onBreedFavorite: { _ in },
            refresh: {}
        )
    }
}
