import XCTest
import ViewInspector
import Nimble

@testable import ActionInBlog

final class ContentViewTests: XCTestCase {
    func test_whenClickButton_thenChangeState() throws {
        let sut = ContentView()
        let expectation = sut.inspection.inspect { view in // 1
            
            try view.find(button: "Change").tap()
            
            
            expect(try view.find(text: "1")).toNot(beNil())
            expect(try view.find(text: "-1")).toNot(beNil())
        }
        ViewHosting.host(view: sut)
        wait(for: [expectation], timeout: 2.5) // 2
    }
}
