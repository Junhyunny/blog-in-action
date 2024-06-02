struct CarStruct {
    var brand: String
    var color: String
    var name: String
    var options: [String]
}

var structCar = CarStruct(brand: "hyunDai", color: "red", name:"grandeur", options: ["loop", "cooling seat"])
var copiedStructCar = structCar
copiedStructCar.color = "green"
copiedStructCar.name = "sonata"
copiedStructCar.options = []

print("original car: ", structCar)
print("copied car: ", copiedStructCar)


class CarClass: CustomStringConvertible {
    var brand: String = ""
    var color: String = ""
    var name: String = ""
    var options: [String] = []
    
    init(brand: String, color: String, name: String, options: [String]) {
        self.brand = brand
        self.color = color
        self.name = name
        self.options = options
    }
    
    var description: String {
        return "CarClass(" +
        "brand: \(brand), " +
        "color: \(color), " +
        "name: \(name), " +
        "options: \(options))"
    }
}

var classCar = CarClass(brand: "hyunDai", color: "red", name:"grandeur", options: ["loop", "cooling seat"])
var copiedClassCar = classCar
copiedClassCar.color = "green"
copiedClassCar.name = "sonata"
copiedClassCar.options = []

print("original car: ", classCar)
print("copied car: ", copiedClassCar)
