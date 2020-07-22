//
//  AppDelegate.swift
//  KaMPKitiOS
//
//  Created by Kevin Schildhorn on 12/18/19.
//  Copyright © 2019 Touchlab. All rights reserved.
//

import UIKit
import shared

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    var window: UIWindow?
    
    // lazy initialize since AppDelegate is instantiated before we run init on KoinIOS
    lazy var log = KoinIOS().get(objCClass: Kermit.self, parameter: "AppDelegate") as! Kermit
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        
        // Initialize Koin depedency injection
        let userDefaults = UserDefaults(suiteName: "KAMPSTARTER_SETTINGS")!
        KoinIOS().initialize(userDefaults: userDefaults)
        
        // Override point for customization after application launch.
        log.v(withMessage: {"App Started"})
        return true
    }
}

