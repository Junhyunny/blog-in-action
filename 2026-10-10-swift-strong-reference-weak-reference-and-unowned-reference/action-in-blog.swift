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
    final class Parent {
        var child: Child?
        deinit { print("Parent 해제") }
    }

    final class Child {
        var parent: Parent?
        deinit { print("Child 해제") }
    }

    var parent: Parent? = Parent()
    var child: Child? = Child()
    print("부모에게 자식을 할당")
    parent?.child = child
    print("자식에게 부모를 할당")
    child?.parent = parent
    print("parent 변수 참조 해제")
    parent = nil
    print("child 변수 참조 해제")
    child = nil  // 두 deinit 모두 호출되지 않는다
}

#Playground {
    final class Parent {
        var child: Child?
        deinit { print("Parent 해제") }
    }

    final class Child {
        weak var parent: Parent?
        deinit { print("Child 해제") }
    }

    var parent: Parent? = Parent()
    var child: Child? = Child()
    print("부모에게 자식을 할당")
    parent?.child = child
    print("자식에게 부모를 할당")
    child?.parent = parent
    print("parent 변수 참조 해제")
    parent = nil
    print("child 변수 참조 해제")
    child = nil
}

#Playground {
    final class Parent {
        var child: Child?
        var name: String = "Parent"
        deinit { print("Parent 해제") }
    }

    final class Child {
        unowned var parent: Parent
        init(parent: Parent) {
            self.parent = parent
        }
        deinit { print("Child 해제") }
    }

    var parent: Parent? = Parent()
    var child: Child? = Child(parent: parent!)
    print("parent 변수 참조 해제")
    parent = nil
    // print(child?.parent.name ?? "")  // crash 발생
}

#Playground {
    class Button {
        var onTap: (() -> Void)?

        func tap() {
            onTap?()
        }

        deinit {
            print("Button deinit")
        }
    }

    class ViewController {
        let button = Button()

        func setup() {
            button.onTap = {
                self.handleTap()
            }
        }

        func handleTap() {
            print("Button tapped")
        }

        deinit {
            print("ViewController deinit")
        }
    }

    var vc: ViewController? = ViewController()
    vc?.setup()
    vc?.button.tap()
    print("vc 변수 참조 정리")
    vc = nil
}

#Playground {
    class Button {
        var onTap: (() -> Void)?

        func tap() {
            onTap?()
        }

        deinit {
            print("Button deinit")
        }
    }

    class ViewController {
        let button = Button()

        func setup() {
            button.onTap = { [weak self] in
                self?.handleTap()
            }
        }

        func handleTap() {
            print("Button tapped")
        }

        deinit {
            print("ViewController deinit")
        }
    }

    var vc: ViewController? = ViewController()
    vc?.setup()
    vc?.button.tap()
    print("vc 변수 참조 정리")
    vc = nil
}
