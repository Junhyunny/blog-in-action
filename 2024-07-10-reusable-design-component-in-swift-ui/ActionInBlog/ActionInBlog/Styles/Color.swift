import SwiftUI

extension Color {
    init(hex: Int, opacity: Double = 1) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xff) / 255,
            green: Double((hex >> 08) & 0xff) / 255,
            blue: Double((hex >> 00) & 0xff) / 255,
            opacity: opacity
        )
    }
}

extension ShapeStyle where Self == Color {
    static var cornflowerBlue: Color {
        Color(hex: 0x659FE5)
    }
    static var scarlet: Color {
        Color(hex: 0xFF3300)
    }
    static var davysGrey: Color {
        Color(hex: 0x525252)
    }
}
