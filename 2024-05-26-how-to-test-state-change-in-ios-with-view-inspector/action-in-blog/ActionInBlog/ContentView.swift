import SwiftUI

class ContentViewModel: ObservableObject {
    @Published var count: Int = 0
}

struct ContentView: View {
    internal let inspection = Inspection<Self>() // 1
    @State var count: Int = 0
    @StateObject var viewModel = ContentViewModel()
    
    var body: some View {
        VStack {
            Text("\(count)")
            Text("\(viewModel.count)")
            Button("Change") {
                count += 1
                viewModel.count -= 1
            }
        }
        .padding()
        .onReceive(inspection.notice) { self.inspection.visit(self, $0) } // 2
    }
}

#Preview {
    ContentView()
}
