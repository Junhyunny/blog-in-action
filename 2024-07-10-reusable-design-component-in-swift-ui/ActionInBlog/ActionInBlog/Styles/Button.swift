import SwiftUI

private func commonStyle(
    configuration: ButtonStyleConfiguration
) -> some View {
    configuration
        .label
        .frame(minWidth: 44, minHeight: 44)
        .padding(.horizontal)
        .foregroundStyle(Color.white)
        .font(.body)
        .bold()
}

struct Primary: ButtonStyle {
    
    func makeBody(configuration: Configuration) -> some View {
        commonStyle(configuration: configuration)
            .background(Color.cornflowerBlue)
            .clipShape(ButtonBorderShape.capsule)
    }
}

struct Cancel: ButtonStyle {
    
    func makeBody(configuration: Configuration) -> some View {
        commonStyle(configuration: configuration)
            .background(Color.scarlet)
            .clipShape(ButtonBorderShape.capsule)
    }
}
