import SwiftUI

struct ContentView: View {
    internal let inspection = Inspection<Self>()
    @State var count = 0
    @State var isPresented = false
    
    var body: some View {
        VStack {
            Text("\(count)")
                .inspectableAlert(isPresented: $isPresented) {
                    Alert(
                        title: Text("Do you want to increase count?"),
                        primaryButton: .default(Text("Yes")) { count += 1 },
                        secondaryButton: .destructive(Text("Cancel"))
                    )
                }
            Button("Increase") {
                self.isPresented = true
            }
        }
        .padding()
        .onReceive(inspection.notice) { self.inspection.visit(self, $0) }
    }
}

#Preview {
    ContentView()
}
