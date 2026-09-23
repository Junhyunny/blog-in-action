//
//  ContentViewModel.swift
//  action-in-blog
//
//  Created by 강준현 on 9/23/26.
//

enum SignalEvent {
    case joined
    case peerLeft
}

protocol SignalClient {
    var events: AsyncStream<SignalEvent> { get }
}

final class ContentViewModel {
    private(set) var status: String = ""
    private let signalClient: SignalClient

    init(signalClient: SignalClient) {
        self.signalClient = signalClient
    }

    func observation() {
        Task {
            for await event in signalClient.events {
                switch event {
                case .joined:
                    self.status = "joined"
                case .peerLeft:
                    self.status = "peerLeft"
                }
            }
        }
    }
}
