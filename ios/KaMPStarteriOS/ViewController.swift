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
    
    private var model: BreedModel?

    override func viewDidLoad() {
        super.viewDidLoad()
        
        model = BreedModel(){summary in
            print("Summary: \(summary)")
            self.data = summary.allItems
            self.breedTableView.reloadData()
        }

        model!.getBreedsFromNetwork()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        self.model?.onDestroy()
        self.model = nil
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return data.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "BreedCell", for: indexPath)
        if let breedCell = cell as? BreedCell {
            let breed = data[indexPath.row]
            breedCell.bind(breed: breed)
        }
        return cell
    }
}

class BreedCell: UITableViewCell {
    
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var favoriteButton: UIButton!
    var breed:Breed?
    
    func bind(breed:Breed){
        self.breed = breed
        nameLabel.text = breed.name
        if(breed.favorite == 0) {
            favoriteButton.setImage(UIImage(named: "heart"), for: UIControl.State.normal)
        }else {
            favoriteButton.setImage(UIImage(named: "heart.fill"), for: UIControl.State.normal)
        }
    }
    
    @IBAction func favoriteButtonPressed(_ sender: Any) {
        if let breedActual = breed {
            let isFavorite = !breedActual.isFavorited()
            BreedModel{_ in }.updateBreedFavorite(breedId: breedActual.id,favorite: isFavorite)
        }
    }
}

extension Breed {
    func isFavorited() -> Bool {
        return favorite != 0
    }
}
