//
//  ViewLifetimeExample1.swift
//  ActionInBlog
//
//  Created by 강준현 on 10/15/26.
//

import SwiftUI

struct Example2: View {
    @State private var counterID = 0

    var body: some View {
        VStack {
            CounterView()
                .id(counterID)

            Button("Change Identity") {
                counterID += 1
            }
        }
    }
}

#Preview {
    Example2()
}
