import SwiftUI

struct StateClickCount: View {
    
    @StateObject var viewModel = ViewModel()
    
    var body: some View {
        HStack {
            Text("\(viewModel.count)")
            Button(action: {viewModel.increase()}, label: {
                Text("+")
            })
        }
    }
}

extension StateClickCount {
    class ViewModel: ObservableObject {
        
        @Published var count: Int = 0
        
        func increase() {
            count += 1
        }
    }
}
