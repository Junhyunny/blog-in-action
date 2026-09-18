//struct Coordinate {
//    let x: Int
//    let y: Int
//}
//
//let a = Coordinate(x: 1, y: 2)
//let b = Coordinate(x: 1, y: 2)
//
// print(a < b)  // compile 에러 - Binary operator '<' cannot be applied to two 'Coordinate' operands

// ---

struct Version: Comparable {
    let major: Int
    let minor: Int
    let patch: Int

    static func < (lhs: Version, rhs: Version) -> Bool {
        (lhs.major, lhs.minor, lhs.patch)
            < (rhs.major, rhs.minor, rhs.patch)
    }
}

let v1 = Version(major: 1, minor: 9, patch: 0)
let v2 = Version(major: 2, minor: 0, patch: 0)

print(v1 < v2)  // true
print(v1 > v2)  // false
print(v1 == v2)  // false

// ---

struct User: Comparable {
    let id: Int
    let name: String

    static func == (lhs: User, rhs: User) -> Bool {
        lhs.id == rhs.id
    }

    static func < (lhs: User, rhs: User) -> Bool {
        lhs.name < rhs.name
    }
}

let a = User(id: 1, name: "Alice")
let b = User(id: 1, name: "Bob")

print(a == b)  // true
print(a < b)  // true

// ---

class Person: Comparable {
    let id: Int
    let name: String

    init(id: Int, name: String) {
        self.id = id
        self.name = name
    }

    static func == (lhs: Person, rhs: Person) -> Bool {
        lhs.id == rhs.id
    }

    static func < (lhs: Person, rhs: Person) -> Bool {
        lhs.id < rhs.id
    }
}

let alice = Person(id: 1, name: "Alice")
let bob = Person(id: 2, name: "Bob")

print(alice < bob)  // true
print(alice == bob)  // false
print(alice > bob)  // false

// ---

enum Priority: Comparable {
    case low
    case medium
    case high
}

print(Priority.low < Priority.medium)  // true
print(Priority.medium < Priority.high)  // true
print(Priority.high > Priority.low)  // true

// ---

enum Score: Comparable {
    case none
    case value(Int)
}

print(Score.value(10) < Score.value(20))  // true
print(Score.value(20) == Score.value(20))  // true

// ---

let scores = [30, 10, 20]

print(scores.sorted())  // [10, 20, 30]
print(scores.min())  // Optional(10)
print(scores.max())  // Optional(10)

// ---

let value = Double.nan

print(value == value)  // false
print(value < value)  // false
print(value > value)  // false

if value.isNaN {
    print("NaN에 대한 별도 처리")
}
