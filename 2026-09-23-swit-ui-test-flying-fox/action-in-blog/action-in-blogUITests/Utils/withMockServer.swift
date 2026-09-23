//
//  withMockServer.swift
//  action-in-blog
//
//  Created by 강준현 on 9/23/26.
//

import FlyingFox
import FlyingSocks
import Foundation

func withMockServer(
    route: (HTTPRoute, @Sendable (HTTPRequest) async throws -> HTTPResponse),
    wrappered: @escaping (String) async throws -> Void
) async throws {
    let server = HTTPServer(port: 0)
    await server.appendRoute(route.0, handler: route.1)
    defer {
        await server.stop()
    }
    Task {
        do {
            try await server.run()
        } catch {
            print("HTTP Server is failed to start")
        }
    }
    try? await server.waitUntilListening()
    guard let address = await server.listeningAddress else {
        throw MockServerError.notFoundAddress
    }
    var port: UInt16 = 0
    switch address {
    case .ip4(_, let portNumber):
        port = portNumber
    case .ip6(_, let portNumber):
        port = portNumber
    case .unix:
        throw MockServerError.notFoundPort
    }
    try await wrappered("http://localhost:\(port)")
}

enum MockServerError: Error {
    case notFoundAddress
    case notFoundPort
}
