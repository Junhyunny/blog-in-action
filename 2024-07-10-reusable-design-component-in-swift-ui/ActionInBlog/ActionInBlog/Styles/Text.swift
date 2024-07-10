import SwiftUI

extension Text {
    func textStyle<Style: ViewModifier>(_ style: Style) -> some View {
        ModifiedContent(content: self, modifier: style)
    }
}

struct Header: ViewModifier {
    
    func body(content: Content) -> some View {
        content
            .font(.title2)
            .foregroundColor(.davysGrey)
    }
}

struct Title: ViewModifier {
    
    func body(content: Content) -> some View {
        content
            .font(.title3)
            .foregroundColor(.davysGrey)
    }
}

struct Paragraph: ViewModifier {
    
    func body(content: Content) -> some View {
        content
            .font(.body)
            .foregroundColor(.davysGrey)
    }
}
