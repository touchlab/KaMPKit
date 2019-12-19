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
    private var itemModel: ItemModel?

    override func viewDidLoad() {
        super.viewDidLoad()
        
        model = SampleModel()
        
        model?.performNetworkRequest() {result in
            print("The result \(result)")
        }
        
        model?.doInitSettings()
        if let boolsetting = model?.getBooleanSetting() {
            NSLog(boolsetting ? "true" : "false")
        }
        
        itemModel = ItemModel(){summary in
            print("Summary: \(summary)")
        }
        
        itemModel?.insertSomeData()
        
        //Just testing Flow. This is not how you should use the model classes
        DispatchQueue.main.asyncAfter(deadline: .now() + 2, execute: {
            self.itemModel?.onDestroy()
            self.itemModel = nil
        })
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

