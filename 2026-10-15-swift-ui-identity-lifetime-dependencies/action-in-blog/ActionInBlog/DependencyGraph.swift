//
//  DependencyGraph.swift
//  ActionInBlog
//
//  Created by 강준현 on 10/15/26.
//

import Observation
import SwiftUI

@Observable
class UserModel {
    var name = "Alice"
    var age = 25
}

struct UserNameView: View {
    let user: UserModel

    var body: some View {
        let _ = print("UserNameView body re-evaluated")
        Text(user.name)
    }
}

struct UserAgeView: View {
    let user: UserModel

    var body: some View {
        let _ = print("UserAgeView body re-evaluated")
        Text("\(user.age)")
    }
}

struct DependencyGraph: View {
    var userModel = UserModel()

    var body: some View {
        VStack {
            UserNameView(user: userModel)
            UserAgeView(user: userModel)
            Button(action: {
                print("Click Change Name Button")
                userModel.name = userModel.name + "!"
            }) {
                Text("Change Name")
            }
            Button(action: {
                print("Click Increase Age Button")
                userModel.age += 1
            }) {
                Text("Increase Age")
            }
        }
    }
}

#Preview {
    DependencyGraph()
}
