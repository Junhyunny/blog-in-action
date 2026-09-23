import Testing

@testable import action_in_blog

final class MockSignalClient: SignalClient {
    private(set) var events: AsyncStream<SignalEvent>
    private(set) var continuation: AsyncStream<SignalEvent>.Continuation!
    init() {
        var continuous: AsyncStream<SignalEvent>.Continuation?
        self.events = AsyncStream { streamContinuation in
            continuous = streamContinuation
        }
        self.continuation = continuous
    }

    func sendEvent(signalEvent: SignalEvent) {
        self.continuation.yield(signalEvent)
    }
}

struct ContentViewModelTests {

    @MainActor
    @Test
    func `given_joined_event_is_received_when_receive_then_status_is_joined`()
        async throws
    {
        let mockSignalClient = MockSignalClient()
        let sut = ContentViewModel(signalClient: mockSignalClient)

        sut.observation()

        mockSignalClient.sendEvent(signalEvent: .joined)
        try await waitFor { sut.status == "joined" }
        #expect(sut.status == "joined")
    }
}

func waitFor(
    timeout: Duration = .seconds(1),
    condition: @escaping () async -> Bool
) async throws {
    let clock = ContinuousClock()
    let deadline = clock.now.advanced(by: timeout)
    while clock.now < deadline {
        if await condition() {
            return
        }
        try await Task.sleep(for: .milliseconds(10))
    }
    throw WaitError.timeout
}

enum WaitError: Error {
    case timeout
}
