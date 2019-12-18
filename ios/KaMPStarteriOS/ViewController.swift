//
//  ViewController.swift
//  KaMPStarteriOS
//
//  Created by Kevin Schildhorn on 12/18/19.
//  Copyright © 2019 Touchlab. All rights reserved.
//

import UIKit
import shared

class ViewController: UIViewController {

    private var model: SampleModel?

    override func viewDidLoad() {
        super.viewDidLoad()
        
        model = SampleModel()
        
        model?.performNetworkRequest() {result in
            print("The result \(result)")
        }
        
        model?.doInitSettings(platformSettings: PlatformiOSKt.defaultSettings())
        if let boolsetting = model?.getBooleanSetting() {
            NSLog(boolsetting ? "true" : "false")
        }
        
        getDatabaseRows()
    }

    private func getDatabaseRows(){
        let driver = PlatformiOSKt.defaultDriver()
        let dbHelper = DatabaseHelper(sqlDriver:driver)
        dbHelper.insertItem(id: 1,value: "Test")
        dbHelper.insertItem(id: 2,value: "Test2")
        let queries = dbHelper.selectAllItems()
        let items = queries.executeAsList()
        print(items)
    }
}

