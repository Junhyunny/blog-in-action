struct Coordinate: Equatable {
    let x: Int
    let y: Int
}

let a = Coordinate(x: 1, y: 2)
let b = Coordinate(x: 1, y: 2)
let c = Coordinate(x: 3, y: 4)

print(a == b)  // true
print(a == c)  // false

// ---

struct User: Equatable {
    let id: Int
    let name: String

    static func == (lhs: User, rhs: User) -> Bool {
        lhs.id == rhs.id
    }
}

let user1 = User(id: 1, name: "Kim")
let user2 = User(id: 1, name: "Lee")

print(user1 == user2)  // true

// ---

class Todo: Equatable {
    let id: Int
    let title: String

    init(id: Int, title: String) {
        self.id = id
        self.title = title
    }

    static func == (lhs: Todo, rhs: Todo) -> Bool {
        lhs.id == rhs.id
    }
}

let todo1 = Todo(id: 1, title: "buying milk")
let todo2 = Todo(id: 1, title: "homework")

print(todo1 == todo2)  // true

// ---

let todo3 = todo1

print(todo1 === todo3)  // true
print(todo1 === todo2)  // false

// --

enum NetworkState: Equatable {
    case idle
    case loading
    case success(String)
    case failure(Int)
}

print(NetworkState.idle == NetworkState.idle)  // true
print(NetworkState.loading == NetworkState.idle)  // false
print(NetworkState.success("Hello") == NetworkState.success("Hello"))  // true
print(NetworkState.success("Hello") == NetworkState.success("World"))  // false
print(NetworkState.failure(404) == NetworkState.failure(404))  // true
