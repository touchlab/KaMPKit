//
//  AppDelegate.swift
//  KaMPKitiOS
//
//  Created by Kevin Schildhorn on 12/18/19.
//  Copyright © 2019 Touchlab. All rights reserved.
//

import SwiftUI
import shared

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions
        launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {

        startKoin()

        let viewController = UIHostingController(rootView: BreedListScreen())

        self.window = UIWindow(frame: UIScreen.main.bounds)
        self.window?.rootViewController = viewController
        self.window?.makeKeyAndVisible()

        LoggerKt.d.log {
            "App Started"
        }
        
        LoggerKt.d.log(messageString: "A log")
        return true
    }
}
