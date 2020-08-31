//
//  ContentView.swift
//  iOSApp
//
//  Created by Aleksey Bazhenov on 30.08.2020.
//  Copyright © 2020 Aleksey Bazhenov. All rights reserved.
//

import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        Text(MyClass().getHello())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
