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
                isOn.toggle() // 1
                onChange(isOn, value) // 2
            },
            label: {
                HStack {
                    Image(systemName: isOn ? "checkmark.square" : "square") // 3
                    Text(label).colorMultiply(.black) //. 
                }
            }
        )
        .onReceive(inspection.notice) { self.inspection.visit(self, $0) }
    }
}
