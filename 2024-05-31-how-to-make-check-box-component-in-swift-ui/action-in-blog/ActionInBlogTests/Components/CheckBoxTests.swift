import XCTest
import SwiftUI
import Nimble
import ViewInspector

@testable import ActionInBlog

final class CheckBoxTests: XCTestCase {
    func test_renderCheckBox() throws { // 1
        func onChange(isChecked: Bool, value: String) -> Void {}
        let sut = CheckBox(label: "label", value: "value", onChange: onChange)
        
        
        let button = try sut.inspect().find(button: "label")
        
        
        expect(button).toNot(beNil())
        expect(try button.find(text: "label")).toNot(beNil())
    }
    
    func test_whenClick_thenGetValueAndState() throws { // 2
        let onChangeSpy = OnChangeSpy()
        let sut = CheckBox(label: "label", value: "value", onChange: onChangeSpy.onChange)
        let expectation = sut.inspection.inspect { view in
            let button = try view.find(button: "label")
            try button.tap()
            
            
            expect(onChangeSpy.expectedChecked).to(beTrue())
            expect(onChangeSpy.expectedValue).to(equal("value"))
        }
        ViewHosting.host(view: sut)
        wait(for: [expectation], timeout: 2.5)
    }
    
    func test_whenDoubleClick_thenGetValueAndState() throws { // 3
        let onChangeSpy = OnChangeSpy()
        let sut = CheckBox(label: "label", value: "value", onChange: onChangeSpy.onChange)
        let expectation = sut.inspection.inspect { view in
            let button = try view.find(button: "label")
            try button.tap()
            try button.tap()
            
            
            expect(onChangeSpy.expectedChecked).to(beFalse())
            expect(onChangeSpy.expectedValue).to(equal("value"))
        }
        ViewHosting.host(view: sut)
        wait(for: [expectation], timeout: 2.5)
    }
}
