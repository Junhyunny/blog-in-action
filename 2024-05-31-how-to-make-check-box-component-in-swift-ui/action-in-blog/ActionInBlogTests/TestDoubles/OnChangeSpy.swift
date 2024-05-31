class OnChangeSpy {
    var expectedChecked = false
    var expectedValue = ""
    
    func onChange(isChecked: Bool, value: String) -> Void {
        self.expectedChecked = isChecked
        self.expectedValue = value
    }
}
