//
//  ViewLifetimeExample1.swift
//  ActionInBlog
//
//  Created by 강준현 on 10/15/26.
//

import SwiftUI



struct Example1: View {
    @State private var isVisible = true

    var body: some View {
        VStack {
            if isVisible {
                CounterView()
            }
            Button("Toggle") {
                isVisible.toggle()
            }
        }
    }
}

#Preview {
    Example1()
}
