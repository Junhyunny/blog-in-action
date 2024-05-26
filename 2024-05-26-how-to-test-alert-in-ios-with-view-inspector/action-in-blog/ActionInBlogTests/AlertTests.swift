import XCTest
import ViewInspector
import Nimble
@testable import ActionInBlog

final class ContentViewTests: XCTestCase {

    func test_whenClickIncreaseButton_thenOpenAlert() throws {
        let sut = ContentView()
        let increaseButton = try sut.inspect().find(button: "증가")
        
        
        try increaseButton.tap()
        
        
        let alertWindow = try sut.inspect().find(ViewType.Alert.self, containing: "숫자를 증가시키겠습니까?")
        let yesButton = try sut.inspect().find(ViewType.AlertButton.self, containing: "Yes")
        let cancelButton = try sut.inspect().find(ViewType.AlertButton.self, containing: "Cancel")
        expect(alertWindow).toNot(beNil())
        expect(yesButton).toNot(beNil())
        expect(cancelButton).toNot(beNil())
    }
}
