//
//  ContentView.swift
//  KaMPStarteriOS
//
//  Created by Kevin Schildhorn on 12/12/19.
//  Copyright © 2019 Kevin Schildhorn. All rights reserved.
//

import SwiftUI
import shared

struct ContentView: View {
    
    var body: some View {
        
        let screenMessage = "Removed this"
        getDatabaseRows()
        return Text(screenMessage)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

private func getDatabaseRows(){
    do {
        let driver = try ActualKt.defaultDriver()
        let dbHelper = DatabaseHelper(sqlDriver:driver)
        dbHelper.insertItem(id: 1,value: "Test")
        dbHelper.insertItem(id: 2,value: "Test2")
        let queries = dbHelper.selectAllItems()
        let items = queries.executeAsList()
        print(items)
    }catch {
        print("TEST")
    }
}
