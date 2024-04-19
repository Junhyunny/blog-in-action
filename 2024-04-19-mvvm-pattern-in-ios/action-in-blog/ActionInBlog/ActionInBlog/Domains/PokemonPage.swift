
import Foundation

struct PokemonPage: Decodable {
    var count: Int = 0
    var next: String? = ""
    var previous: String? = ""
    var results: [Pokemon] = []
}
