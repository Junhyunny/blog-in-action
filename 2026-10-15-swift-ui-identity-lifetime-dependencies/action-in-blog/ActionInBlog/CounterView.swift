//
//  CounterView.swift
//  ActionInBlog
//
//  Created by 강준현 on 10/15/26.
//

import SwiftUI

struct CounterView: View {
    @State var count: Int = 0

    var body: some View {
        VStack {
            Text("Count: \(count)")
            Button("Increase") {
                count = count + 1
            }
        }
    }
}
