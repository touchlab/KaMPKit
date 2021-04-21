//
//  ContentView.swift
//  MyworkoutTestApp
//
//  Created by Even André Fiskvik on 21/04/2021.
//  Copyright © 2021 Touchlab. All rights reserved.
//

import SwiftUI
import shared
import Combine

struct BreedCell: View {
    var breed: Breed?

    var body: some View {
        HStack {
            Text(breed?.name ?? "")
            Spacer()
            Image(systemName: (breed?.favorite == 0) ? "heart" : "heart.fill")
        }.padding()
    }
}

class MockViewModel: BreedViewModelProtocol
{
    @Published var breeds: [Breed] = []

    init() {
        breeds = [
            Breed(id: 1, name: "Mouse", favorite: 1),
            Breed(id: 2, name: "Cat", favorite: 1),
            Breed(id: 3, name: "Chiwawa", favorite: 0)
        ]
    }
}

class BreedViewModel: BreedViewModelProtocol
{
    @Published var breeds: [Breed] = []

    lazy var adapter: NativeViewModel = NativeViewModel(
        onLoading: { /* Loading spinner is shown automatically on iOS */
            /*if (!(self.refreshControl.isRefreshing)) {
                self.refreshControl.beginRefreshing()
            }*/
        },
        onSuccess: { summary in
            // self?.viewUpdateSuccess(for: summary)
            // self?.refreshControl.endRefreshing()
            self.breeds = summary.allItems
            // self.assignBreeds()
        },
        onError: { error in
            // self?.errorUpdate(for: error)
            // self?.refreshControl.endRefreshing()
        },
        onEmpty: { /* Show "No doggos found!" message */
            // self?.refreshControl.endRefreshing()
        }
    )

    init() {
        adapter.observeBreeds()
    }

    func assignBreeds() {
        // self.breeds = breeds
    }
}

struct ContentView<MyViewModel>: View where MyViewModel: BreedViewModelProtocol  {
    @ObservedObject var viewModel: MyViewModel

    var body: some View {
        VStack {
            ForEach(viewModel.breeds, id: \.self) { breed in
                BreedCell(breed: breed)
            }
        }
    }
}

protocol BreedViewModelProtocol: ObservableObject {
    var breeds: [Breed] { get }
}

/*extension BreedViewModelProtocol {
    var breeds: [Breed] {
        get { [] }
    }
}*/

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView(viewModel: MockViewModel())
        BreedCell(
            breed: Breed(id: 1, name: "Dog", favorite: 1)
        )
    }
}
