
import SwiftUI

struct ContentView: View {
    
    @State var offset = 0
    @StateObject var viewModel = ContentViewModel()
    
    private func prevPage() {
        let prevOffset = self.offset - 10
        self.offset = prevOffset < 0 ? offset : prevOffset
        viewModel.fetch(offset)
    }
    
    private func nextPage() {
        let nextOffset = self.offset + 10
        self.offset = nextOffset > viewModel.totalCount ? offset : nextOffset
        viewModel.fetch(offset)
    }
    
    var body: some View {
        VStack(spacing: 0) {
            HStack {
                Text("Pokemon List").font(.title2)
            }
            .frame(height: 20)
            Divider().padding(.vertical ,20)
            ScrollView {
                ForEach(viewModel.pokemons, id: \.name) { pokemon in
                    Text(pokemon.name)
                        .font(.body)
                        .frame(maxWidth: /*@START_MENU_TOKEN@*/.infinity/*@END_MENU_TOKEN@*/, minHeight: 50)
                        .border(.gray)
                }
            }
            Divider().padding(.vertical ,20)
            HStack {
                Button(action: prevPage, label: {
                    Image(systemName: "arrow.backward.square").font(.title)
                })
                Button(action: nextPage, label: {
                    Image(systemName: "arrow.forward.square").font(.title)
                })
            }
            .frame(height: 20)
        }
        .padding(.all, 20)
        .onAppear() {
            viewModel.fetch(offset)
        }
    }
}

#Preview {
    ContentView()
}
