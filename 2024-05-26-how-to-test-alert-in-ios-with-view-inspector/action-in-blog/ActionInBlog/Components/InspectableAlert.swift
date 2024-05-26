import SwiftUI

struct InspectableAlert: ViewModifier {
    let isPresented: Binding<Bool>
    let popupBuilder: () -> Alert
    let onDismiss: (() -> Void)? = nil
    
    func body(content: Self.Content) -> some View {
        content.alert(isPresented: isPresented, content: popupBuilder)
    }
}
