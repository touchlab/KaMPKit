//
//  ViewController.swift
//  KaMPKitiOS
//
//  Created by Kevin Schildhorn on 12/18/19.
//  Copyright © 2019 Touchlab. All rights reserved.
//

import UIKit
import shared

class BreedsViewController: UIViewController {

    @IBOutlet weak var breedTableView: UITableView!
    var data: [Breed] = []
    
    let log = koin.get(objCClass: Kermit.self, parameter: "ViewController") as! Kermit
    
    lazy var adapter: NativeViewModel = NativeViewModel(
        viewUpdate: { [weak self] summary in
            self?.viewUpdate(for: summary)
        }
    )
    
    // MARK: View Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        breedTableView.dataSource = self

        //We check for stalk data in this method
        adapter.getBreeds()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        adapter.onDestroy()
    }
    
    // MARK: BreedModel Closures
    
private func viewUpdate(for summaryState: DataState<ItemDataSummary>) {
    let summaryStateEnum: DataStateEnum<ItemDataSummary> = DataStateToEnum(summaryState)
    switch(summaryStateEnum) {
        case .Success(let successData):
            log.d(withMessage: {"View updating with \(successData.allItems.count) breeds"})
            data = successData.allItems
            breedTableView.reloadData()
        case .Error(let error):
            errorUpdate(for: error.localizedDescription)
        case .Empty: break
            // Show "No doggos found!" message
        case .Loading: break
        default: break
            // Show a loading spinner
    }
}
    
    private func errorUpdate(for errorMessage: String) {
        log.e(withMessage: {"Displaying error: \(errorMessage)"})
        let alertController = UIAlertController(title: "error", message: errorMessage, preferredStyle: .actionSheet)
        alertController.addAction(UIAlertAction(title: "Dismiss", style: .default))
        present(alertController, animated: true, completion: nil)
    }
    
}

// MARK: - UITableViewDataSource
extension BreedsViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "BreedCell", for: indexPath)
        if let breedCell = cell as? BreedCell {
            let breed = data[indexPath.row]
            breedCell.bind(breed)
            breedCell.delegate = self
        }
        return cell
    }
}

// MARK: - BreedCellDelegate
extension BreedsViewController: BreedCellDelegate {
    func toggleFavorite(_ breed: Breed) {
        adapter.updateBreedFavorite(breed: breed)
    }
}

enum DataStateEnum<T> {
    case Success(_ data: T)
    case Error(_ error: Error)
    case Loading
    case Empty
}

func DataStateToEnum<T>(_ dataState: DataState<T>) -> DataStateEnum<T> {
    switch dataState {
    case is DataStateSuccess<T>:
        let mDataState = (dataState as! DataStateSuccess)
        return DataStateEnum.Success(mDataState.data!)
        
    case is DataStateError:
        let mDataState = (dataState as! DataStateError)
        return DataStateEnum.Error(mDataState.exception as! Error)
    
    case is DataStateLoading:
        return DataStateEnum.Loading
    
    case is DataStateEmpty:
        fallthrough
    default:
        return DataStateEnum.Empty
    }
}

