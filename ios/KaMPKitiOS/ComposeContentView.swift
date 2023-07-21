//
//  ComposeContentView.swift
//  KaMPKitiOS
//
//  Created by Petru on 7/21/23.
//  Copyright © 2023 Touchlab. All rights reserved.
//

import Foundation
import shared
import SwiftUI

struct ComposeContentView: UIViewControllerRepresentable {
    func updateUIViewController(_ uiViewController: UIViewControllerType, context: Context) {
        // no-op
    }

    func makeUIViewController(context: Context) -> some UIViewController {
        MainViewControllerKt.MainViewController(
            logger: koin.loggerWithTag(tag: "ViewController"),
            viewModel: KotlinDependencies.shared.getBreedViewModel().viewModel
        )
    }
}
