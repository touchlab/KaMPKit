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
        let screenMessage = CommonKt.createApplicationScreenMessage()
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
        //dbHelper.doInitDatabase()
        dbHelper.setRow(id: 1,value: "Test")
        dbHelper.setRow(id: 2,value: "Test2")
        let queries = dbHelper.getTableQueries()
        let items = queries.executeAsList()
        print(items)
    }catch {
        
        print("TEST")
    }
}
