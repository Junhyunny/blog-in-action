import Foundation

class ContentViewModel: ObservableObject {
    
    private let pokenmonRepository = PokemonRepository()
    
    @Published var totalCount: Int = Int.max
    @Published var pokemons: [Pokemon] = []
    
    func fetch(_ offset: Int) {
        Task {
            guard let response = await self.pokenmonRepository.list(offset) else {
                return
            }
            DispatchQueue.main.async {
                self.totalCount = response.count
                self.pokemons = response.results
            }
        }
    }
}
