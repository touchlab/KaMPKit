//
//  BreedCell.swift
//  KaMPKitiOS
//
//  Created by Ben Whitley on 6/17/20.
//  Copyright © 2020 Touchlab. All rights reserved.
//

import UIKit
import shared

protocol BreedCellDelegate: class {
    func toggleFavorite(_ breed: Breed)
}

class BreedCell: UITableViewCell {
    
    @IBOutlet weak var nameLabel: UILabel!
    @IBOutlet weak var favoriteButton: UIButton!
    
    var breed: Breed?
    weak var delegate: BreedCellDelegate?
    
    func bind(_ breed: Breed) {
        self.breed = breed
        nameLabel.text = breed.name
        
        let imageName = (breed.favorite == 0) ? "heart" : "heart.fill"
        favoriteButton.setImage(UIImage(systemName: imageName), for: .normal)
    }
    
    @IBAction func favoriteButtonPressed(_ sender: Any) {
        if let breed = breed {
            delegate?.toggleFavorite(breed)
        }
    }
}
