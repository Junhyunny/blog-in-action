//
//  UserListView.swift
//  ActionInBlog
//
//  Created by 강준현 on 10/15/26.
//

import SwiftUI

struct User: Identifiable {
    let id: UUID
    let name: String
}

struct UserListView: View {
    let users: [User]

    var body: some View {
        ForEach(users) { user in
            Text(user.name)
        }
    }
}

struct ExplicitIdentityView: View {
    @State var users: [User] = [
        User(id: UUID(), name: "Alice"),
        User(id: UUID(), name: "Bob"),
        User(id: UUID(), name: "Charlie"),
    ]

    var body: some View {
        VStack {
            UserListView(users: users)
            Button(action: {
                users.shuffle()
            }) {
                Text("shuffle")
            }
        }
    }
}
