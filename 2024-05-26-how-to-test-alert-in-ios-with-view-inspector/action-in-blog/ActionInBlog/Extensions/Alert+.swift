import SwiftUI

extension View {
    func inspectableAlert(isPresented: Binding<Bool>, content: @escaping () -> Alert) -> some View {
        return self.modifier(
            InspectableAlert(
                isPresented: isPresented,
                popupBuilder: content
            )
        )
    }
}
