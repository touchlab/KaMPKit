//
//  ContentView.swift
//  KaMPStarteriOS
//
//  Created by Kevin Schildhorn on 12/12/19.
//  Copyright © 2019 Kevin Schildhorn. All rights reserved.
//

import SwiftUI
import SharedCode

struct ContentView: View {
    var body: some View {
        var temp = CommonKt.createApplicationScreenMessage()
        return Text(temp)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
