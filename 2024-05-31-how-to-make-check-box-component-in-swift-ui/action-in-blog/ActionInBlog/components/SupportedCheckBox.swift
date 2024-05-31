import SwiftUI

struct iOSCheckboxToggleStyle: ToggleStyle {
    func makeBody(configuration: Configuration) -> some View {
        Button(action: {
            configuration.isOn.toggle()
        }, label: {
            HStack {
                Image(systemName: configuration.isOn ? "checkmark.square" : "square")
                configuration.label
                    .colorMultiply(.black)
            }
        })
    }
}

struct SupportedCheckBox: View {
    @State var isOn: Bool = false
    
    let label: String
    let value: String
    let onChange: (Bool, String) -> Void
    
    var body: some View {
        Toggle(label, isOn: $isOn)
            .toggleStyle(iOSCheckboxToggleStyle())
            .onChange(of: isOn, { onChange(isOn, value) })
            .accessibilityIdentifier("checkbox")
    }
}
