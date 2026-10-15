import SwiftUI

struct ContentView: View {

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

#Preview {
    ContentView()
}
