import SwiftUI

struct iOSCheckboxToggleStyle: ToggleStyle {
    func makeBody(configuration: Configuration) -> some View {
        Button(action: {
            configuration.isOn.toggle() // 1
        }, label: {
            HStack {
                Image(systemName: configuration.isOn ? "checkmark.square" : "square") // 2
                configuration.label
                    .colorMultiply(.black) // 3
            }
        })
    }
}

struct SupportedCheckBox: View {
    internal let inspection = Inspection<Self>()
    
    @State var isOn: Bool = false
    
    let label: String
    let value: String
    let onChange: (Bool, String) -> Void
    
    var body: some View {
        Toggle(label, isOn: $isOn)
            .toggleStyle(iOSCheckboxToggleStyle()) // 1
            .onChange(of: isOn, { onChange(isOn, value) }) // 2
            .onReceive(inspection.notice) { self.inspection.visit(self, $0) }
    }
}
