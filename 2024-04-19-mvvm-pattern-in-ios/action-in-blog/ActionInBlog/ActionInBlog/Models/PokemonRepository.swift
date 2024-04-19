import Foundation

struct PokemonRepository {
    
    private let url = "https://pokeapi.co/api/v2/pokemon?limit=10&offset="

    func list(_ offset: Int) async -> PokemonPage?  {
        guard let url = URL(string: "\(url)\(offset)") else {
            return nil
        }
        do {
            let (data, _) = try await URLSession.shared.data(from: url)
            return try JSONDecoder().decode(PokemonPage.self, from: data)
        } catch {
            print(error.localizedDescription)
        }
        return nil
    }
}
