//
//  MyworkoutTestAppApp.swift
//  MyworkoutTestApp
//
//  Created by Even André Fiskvik on 21/04/2021.
//  Copyright © 2021 Touchlab. All rights reserved.
//

import SwiftUI
import shared

@main
struct MyworkoutTestAppApp: App {
    // Lazy so it doesn't try to initialize before startKoin() is called
    lazy var log = koin.get(objCClass: Kermit.self, parameter: "AppDelegate") as! Kermit

    var body: some Scene {
        WindowGroup {
            ContentView(viewModel: BreedViewModel())
        }
    }

    init() {
        startKoin()
    }
}
