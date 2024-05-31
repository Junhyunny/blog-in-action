import SwiftUI

struct CheckBox: View {
    internal let inspection = Inspection<Self>()
    @State var isOn: Bool = false
    
    let label: String
    let value: String
    let onChange: (Bool, String) -> Void
    
    var body: some View {
        Button(
            action: {
                onChange(!isOn, value)
                isOn.toggle()
            },
            label: {
                HStack {
                    Image(systemName: isOn ? "checkmark.square" : "square")
                    Text(label).colorMultiply(.black)
                }
            }
        )
        .onReceive(inspection.notice) { self.inspection.visit(self, $0) }
    }
}
