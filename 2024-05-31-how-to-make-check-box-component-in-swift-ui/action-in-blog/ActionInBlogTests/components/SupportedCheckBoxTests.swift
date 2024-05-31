import XCTest
import SwiftUI
import Nimble
import ViewInspector

@testable import ActionInBlog

final class SupportedCheckBoxTests: XCTestCase {
    func test_renderCheckBox() throws {
        func onChange(isChecked: Bool, value: String) -> Void {}
        let sut = SupportedCheckBox(label: "label", value: "value", onChange: onChange)
        
        
        let button = try sut.inspect().find(button: "label")
        
        
        expect(button).toNot(beNil())
        expect(try button.find(text: "label")).toNot(beNil())
    }
    
    func test_whenClick_thenGetValueAndState() throws {
        var expectedChecked = false
        var expectedValue = ""
        func onChange(isChecked: Bool, value: String) -> Void {
            expectedValue = value
        }
        let sut = SupportedCheckBox(label: "label", value: "value", onChange: onChange)
        
        
        let button = try sut.inspect().find(button: "label")
        try button.tap()
        
        
        expect(expectedChecked).to(beTrue())
        expect(expectedValue).toEventually(equal("value"))
    }
}
