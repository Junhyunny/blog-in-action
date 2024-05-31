import SwiftUI

struct ContentView: View {
    var body: some View {
        CheckBox(label: "Apple", value: "APPLE") { state, value in
            print("in CheckBox")
            print(state)
            print(value)
        }
        SupportedCheckBox(label: "Banana", value: "BANANA") { state, value in
            print("in SupportedCheckBox")
            print(state)
            print(value)
        }
    }
}

#Preview {
    ContentView()
}
