import Foundation
import Playgrounds

final class UnsafeCounter: @unchecked Sendable {
    var value = 0
}

func runRaceTest() async {
    for round in 1...5 {
        let counter = UnsafeCounter()
        await withTaskGroup(of: Void.self) { group in
            for _ in 0..<100_000 {
                group.addTask {
                    counter.value += 1
                }
            }
        }
        print("\(round)회차: \(counter.value) / 100000")
    }
}

#Playground("Data Race") {
    await runRaceTest()
}

// ---

actor SafeCounter {
    private var value = 0

    func increment() {
        value += 1
    }

    func currentValue() -> Int {
        value
    }
}

func runActorTest() async {
    for round in 1...5 {
        let counter = SafeCounter()
        await withTaskGroup(of: Void.self) { group in
            for _ in 0..<100_000 {
                group.addTask {
                    await counter.increment()
                }
            }
        }
        print("\(round)회차: \(await counter.currentValue()) / 100000")
    }
}

#Playground("Actor") {
    await runActorTest()
}

// ---

actor UserStore {
    var names: [String] = []
    
    func append(_ name: String) {
        names.append(name)
    }
}

let store = UserStore()

#Playground("Compile Error") {
    await store.append("Alice")
    await store.append("Bob")
}
