//
//  action_in_blogUITests.swift
//  action-in-blogUITests
//
//  Created by 강준현 on 9/23/26.
//

import FlyingFox
import XCTest

final class action_in_blogUITests: XCTestCase {

    override func setUpWithError() throws {
        continueAfterFailure = false
    }

    let stubResponse: String = """
        {
            "count":1351,
            "next":"https://pokeapi.co/api/v2/pokemon?offset=20&limit=20",
            "previous":null,
            "results":[
                {"name":"bulbasaur","url":"https://pokeapi.co/api/v2/pokemon/1/"},
                {"name":"ivysaur","url":"https://pokeapi.co/api/v2/pokemon/2/"}
            ]
        }
        """

    @MainActor
    func testExample() async throws {
        try await withMockServer(
            route: (
                "GET /api/v2/pokemon",
                { _ in
                    return HTTPResponse(
                        statusCode: .ok,
                        body: Data(self.stubResponse.utf8)
                    )
                }
            )
        ) { url in
            let app = XCUIApplication()
            app.launchEnvironment["POKEMON_URL"] = url
            app.launch()

            XCTAssertTrue(
                app.staticTexts["bulbasaur"].waitForExistence(timeout: 3)
            )
            XCTAssertTrue(app.staticTexts["ivysaur"].exists)
        }
    }
}
