import Playgrounds

#Playground {
    final class Box {
        var number = 1
        deinit { print("Box 해제") }
    }

    var first: Box? = Box()  // Box 객체 한 개를 만든다
    var second = first  // 새 Box를 만들지 않고 같은 객체를 가리킨다
    print(first === second)  // true, 두 참조의 대상이 같다
    first?.number = 10
    print(second?.number ?? -1)  // 10, 한 객체를 공유한다
    first = nil  // 첫 번째 손가락만 거둔다
    print(second?.number ?? -1)  // 10, 객체는 여전히 살아 있다
    second = nil  // 두 번째 손가락을 거둔다
}

#Playground {
    final class Person {
        let name: String
        init(_ name: String) { self.name = name }
        deinit { print("\(name) 해제") }
    }

    var first: Person? = Person("Junhyunny")
    var second = first  // 같은 인스턴스를 강하게 참조한다
    print("first 변수 해제")
    first = nil  // second의 강한 참조가 남아 있으므로 아직 해제되지 않는다
    print("second 변수 해제")
    second = nil  // 마지막 강한 참조가 사라져 deinit이 호출된다
}

#Playground {
    class Traveler {
        var name: String
        var destination: String?
        init(name: String, destination: String? = nil) {
            self.name = name
            self.destination = destination
        }
    }

    let traveler1 = Traveler(name: "Lily")
    let traveler2 = traveler1
    traveler2.destination = "Big Sur"
    print("Done traveling")
    //    func test() {
    //        let traveler1 = Traveler(name: "Lily")
    //        retain
    //
    //        let traveler2 = traveler1
    //        release
    //
    //        traveler2.destination = "Big Sur"
    //        release
    //
    //        print("Done traveling")
    //    }
}
