import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack {
            Text("Header")
                .textStyle(Header())
            Text("Title")
                .textStyle(Title())
            Text("Content")
                .textStyle(Paragraph())
            Button("Submit") {}
                .buttonStyle(Primary())
            Button("Cancel") {}
                .buttonStyle(Cancel())
        }
        .padding()
    }
}

#Preview {
    ContentView()
}
