let count = 10
let ratio = 0.5
let title = "Snow"
let visible = true

print(type(of: count))  // Int
print(type(of: ratio))  // Double
print(type(of: title))  // String
print(type(of: visible))  // Bool

// ---

let gpuValue: Float = 0.5

print(type(of: gpuValue))  // Float

// ---

print(Int8.min)  //-128
print(Int8.max)  // 127
print(UInt8.min)  // 0
print(UInt8.max)  //255

// ---

// var runtimeValue = UInt8.max  // 255
// runtimeValue += 1  // 런타임 오류: arithmetic overflow

// ---

let maxInt = Int8.max  // 127 (Int8이 가질 수 있는 최댓값)
let result = maxInt.addingReportingOverflow(1)

print(result.partialValue)  // -128 (오버플로우된 결과값)
print(result.overflow)  // true (오버플로우가 발생했음을 알려줌)

if result.overflow {
    print("오버플로우가 발생했으니 안전한 다른 처리를 합니다.")
}

// ---

let wrappedUp = UInt8.max &+ 1
let wrappedDown = UInt8.min &- 1
let signedUp = Int8.max &+ 1
let signedDown = Int8.min &- 1
let wrappedProduct: UInt8 = 20 &* 20

print(wrappedUp)  // 255 &+ 1 == 0
print(wrappedDown)  // 0 &- 1 == 255
print(signedUp)  // 127 &+ 1 == -128
print(signedDown)  // -128 &- 1 == 127
print(wrappedProduct)  // 400에서 하위 8bit만 남아 144

// ---

let greeting = "Hi 👋"

for character in greeting {
    print(character)
}

// ---

let inferred = "A"
let explicit: Character = "A"

print(type(of: inferred))  // String
print(type(of: explicit))  // Character

// ---

let koreaIcon = "🇰🇷"

print(koreaIcon.count)  // 1
print(koreaIcon.unicodeScalars.count)  // 2
print(koreaIcon.utf8.count)  // 8
print(koreaIcon.utf16.count)  // 4

// ---

let language = "Swift 🇰🇷"

let firstIndex: String.Index = language.startIndex
print(language[firstIndex])  // "S"

let secondIndex: String.Index = language.index(after: firstIndex)
print(language[secondIndex])  // "w"

let offset: String.Index = language.index(language.startIndex, offsetBy: 6)
print(language[offset])  // "🇰🇷"

for index: String.Index in language.indices {
    print(language[index])
}

// ---

let fullName = "Marie Curie"
let space = fullName.firstIndex(of: " ") ?? fullName.endIndex
let firstNameSlice = fullName[..<space]
let firstName = String(firstNameSlice)

print(type(of: firstNameSlice))  // Substring
print(type(of: firstName))  // String

// ---

var original = "HEADER:" + String(repeating: "A", count: 5) + ":TAIL"
var slice = original.dropFirst(7).prefix(5)

slice.replaceSubrange(slice.startIndex...slice.startIndex, with: "Z")

print(slice)  // ZAAAA
print(original)  // HEADER:AAAAAAAAHEADER:AAAAA:TAIL

// ---

let originalString = "HEADER:Junhyunny, Develeoper!:FOOTER"
let start = originalString.index(original.startIndex, offsetBy: 7)
let end = originalString.index(start, offsetBy: 22)
let substring = originalString[start..<end]

print(substring)  // Substring "Junhyunny, Develeoper!"
print(substring.startIndex)  // 7[utf8]
print(substring.startIndex == start)  // true
print(substring.endIndex == end)  // true
print(originalString[slice.startIndex])  // "J"
