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
    private let refreshControl = UIRefreshControl()

    lazy var adapter: NativeViewModel = NativeViewModel(
        onLoading: { /* Loading spinner is shown automatically on iOS */
            [weak self] in
            guard let self = self else { return }
            if (!(self.refreshControl.isRefreshing)) {
                self.refreshControl.beginRefreshing()
            }
        },
        onSuccess: {
            [weak self] summary in self?.viewUpdateSuccess(for: summary)
            self?.refreshControl.endRefreshing()
        },
        onError: { [weak self] error in self?.errorUpdate(for: error)
            self?.refreshControl.endRefreshing()
        },
        onEmpty: { /* Show "No doggos found!" message */
            [weak self] in self?.refreshControl.endRefreshing()
        }
    )
    
    // MARK: View Lifecycle

    @objc
    func getBreedsForced() {
        adapter.refreshBreeds(forced: true)
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        breedTableView.dataSource = self
        // Add Refresh Control to Table View
        breedTableView.refreshControl = refreshControl
        // Configure Refresh Control
        refreshControl.addTarget(self, action: #selector(self.getBreedsForced), for: .valueChanged)
        refreshControl.beginRefreshing()
        adapter.refreshBreeds(forced: false)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        adapter.onDestroy()
    }
    
    // MARK: BreedModel Closures
    
    private func viewUpdateSuccess(for summary: ItemDataSummary) {
        log.d(withMessage: {"View updating with \(summary.allItems.count) breeds"})
        data = summary.allItems
        breedTableView.reloadData()
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


