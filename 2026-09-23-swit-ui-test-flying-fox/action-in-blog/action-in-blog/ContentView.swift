import SwiftUI

struct Pokemon: Codable, Identifiable, Hashable {
    var name: String
    var url: String
    var id: String { self.name }
}

struct PokemonResponse: Codable {
    var results: [Pokemon]
}

struct ContentView: View {
    @Environment(\.appConfig) private var appConfig
    @State private var pokemons: [Pokemon] = []

    func fetchPokemons() async -> [Pokemon] {
        do {
            let (data, _) = try await URLSession.shared.data(
                from: URL(string: "\(appConfig.url)/api/v2/pokemon")!
            )
            let response = try JSONDecoder().decode(
                PokemonResponse.self,
                from: data
            )
            return response.results
        } catch {
            return []
        }
    }

    var body: some View {
        List(pokemons) { pokemon in
            Text(pokemon.name)
        }
        .task {
            pokemons = await fetchPokemons()
        }
    }
}

#Preview {
    ContentView()
}
