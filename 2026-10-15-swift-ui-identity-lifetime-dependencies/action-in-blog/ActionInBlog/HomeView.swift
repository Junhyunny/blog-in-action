//
//  HomeView.swift
//  ActionInBlog
//
//  Created by 강준현 on 10/15/26.
//

import SwiftUI

struct HomeView: View {
    var body: some View {
        Text("Home View")
    }
}

struct LoginView: View {
    var body: some View {
        Text("Login View")
    }
}

struct StructuralIdentityView: View {

    @State var isLoggedIn: Bool = false

    var body: some View {
        VStack {
            if isLoggedIn {
                HomeView()
            } else {
                LoginView()
            }
            Button(action: {
                isLoggedIn.toggle()
            }) {
                Text("toggle")
            }
        }
    }
}
