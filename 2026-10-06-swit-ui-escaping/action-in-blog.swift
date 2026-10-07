import Playgrounds

func performNow(_ action: () -> Void) {
    action()
}

final class DeferredActions {
    private var action: (() -> Void)?

    func save(_ action: @escaping () -> Void) {
        self.action = action
    }

    func fire() { action?() }
    
    func clear() { action = nil }
}

#Playground {
    performNow { print("즉시 실행") }

    let deferred = DeferredActions()
    deferred.save { print("나중에 실행") }
    print("deferred.save() 함수 호출 완료")
    deferred.fire()
}
