//
//  KoinApplication.swift
//  KaMPStarteriOS
//
//  Created by Russell Wolf on 6/18/20.
//  Copyright © 2020 Touchlab. All rights reserved.
//

import Foundation
import shared

func startKoin() {
    let koinApplication = KoinKt.doInitKoin { it in
        it.module { it in
            it.single(qualifier: KoinIOSKt.namedUserDefaults) {
                // Note that Koin can't handle Objective-C types, so we cast to Any
                UserDefaults(suiteName: "KAMPSTARTER_SETTINGS") as Any
            }
        }
    }
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin? = nil
var koin: Koin_coreKoin {
    return _koin!
}
