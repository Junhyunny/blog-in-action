import SwiftUI
import XCTest
import ViewInspector
import Nimble

@testable import ActionInBlog

final class ContentViewTests: XCTestCase {
    func test_whenClickIncreaseButton_thenOpenAlert() throws {
        let sut = ContentView()
        let expectation = sut.inspection.inspect { view in
            try view.find(button: "Increase").tap()
            
            
            let alertWindow = try view.find(
                ViewType.Alert.self,
                containing: "Do you want to increase count?"
            )
            let yesButton = try view.find(ViewType.AlertButton.self, containing: "Yes")
            let cancelButton = try view.find(ViewType.AlertButton.self, containing: "Cancel")
            expect(alertWindow).toNot(beNil())
            expect(yesButton).toNot(beNil())
            expect(cancelButton).toNot(beNil())
        }
        ViewHosting.host(view: sut)
        wait(for: [expectation], timeout: 2.5)
    }
    
    func test_givenOpenedAlert_whenYesButton_thenCountIsIncreasedAndCloseAlert() throws {
        let sut = ContentView()
        let expectation = sut.inspection.inspect { view in
            try view.find(button: "Increase").tap()
            
            
            try view.find(
                ViewType.AlertButton.self,
                containing: "Yes"
            )
            .tap()
            
            
            let result = try view.find(text: "1")
            expect(result).toNot(beNil())
            expect{
                try view.find(
                    ViewType.AlertButton.self,
                    containing: "Yes"
                )
            }.to(throwError())
        }
        ViewHosting.host(view: sut)
        wait(for: [expectation], timeout: 2.5)
    }
    
    func test_givenOpenedAlert_whenCancelButton_thenCountIsNotIncreasedAndCloseAlert() throws {
        let sut = ContentView()
        let expectation = sut.inspection.inspect { view in
            try view.find(button: "Increase").tap()
            
            
            try view.find(
                ViewType.AlertButton.self,
                containing: "Cancel"
            )
            .tap()
            
            
            let result = try view.find(text: "0")
            expect(result).toNot(beNil())
            expect{
                try view.find(
                    ViewType.AlertButton.self,
                    containing: "Cancel"
                )
            }.to(throwError())
        }
        ViewHosting.host(view: sut)
        wait(for: [expectation], timeout: 2.5)
    }
}
