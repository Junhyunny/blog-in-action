import SwiftUI

struct ContentView: View {
    
    @State var flag: Bool = false

    var body: some View {
        VStack {
            VStack {
                Text("current flag state - \(flag)")
                Button(action: { flag.toggle() }, label: {
                    Text("change state")
                })
            }
            Divider().padding(.vertical, 20)
            VStack {
                Text("This case is @ObservedObject")
                ObservedClickCount()
            }
            Divider().padding(.vertical, 20)
            VStack {
                Text("This case is @StateObject")
                StateClickCount()
            }
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
