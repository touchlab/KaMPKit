//
//  ViewController.swift
//  KaMPStarteriOS
//
//  Created by Kevin Schildhorn on 12/18/19.
//  Copyright © 2019 Touchlab. All rights reserved.
//

import UIKit
import shared

class ViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {

    @IBOutlet weak var breedTableView: UITableView!
    var data:[Breed] = []
    
    let log = KoinIOS().get(objCClass: Kermit.self, parameter: "ViewController") as! Kermit
    
    lazy var adapter: NativeCoroutineAdapter = NativeCoroutineAdapter(
        viewUpdate: { [weak self] summary in
            self?.viewUpdate(for: summary)
        }, errorUpdate: { [weak self] errorMessage in
            self?.errorUpdate(for: errorMessage)
        }
    )
    
    // MARK: View Lifecycle

    override func viewDidLoad() {
        super.viewDidLoad()
        breedTableView.delegate = self
        breedTableView.dataSource = self
        
        //We check for stalk data in this method
        adapter.getBreedsFromNetwork()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        adapter.onDestroy()
    }
    
    // MARK: BreedModel Closures
    
    private func viewUpdate(for summary: ItemDataSummary) {
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
    
    // MARK: TableView
        
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "BreedCell", for: indexPath)
        if let breedCell = cell as? BreedCell {
            let breed = data[indexPath.row]
            breedCell.bind(breed: breed, adapter: adapter)
        }
        return cell
    }
}

class BreedCell: UITableViewCell {
    
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var favoriteButton: UIButton!
    var breed:Breed?
    var adapter:NativeCoroutineAdapter?
    
    func bind(breed:Breed, adapter: NativeCoroutineAdapter){
        self.breed = breed
        self.adapter = adapter
        nameLabel.text = breed.name
        if(breed.favorite == 0) {
            favoriteButton.setImage(UIImage(systemName: "heart"), for: UIControl.State.normal)
        }else {
            favoriteButton.setImage(UIImage(systemName: "heart.fill"), for: UIControl.State.normal)
        }
    }
    
    @IBAction func favoriteButtonPressed(_ sender: Any) {
        if let breedActual = breed {
            adapter!.updateBreedFavorite(breed: breedActual)
        }
    }
}

extension Breed {
    func isFavorited() -> Bool {
        return favorite != 0
    }
}
