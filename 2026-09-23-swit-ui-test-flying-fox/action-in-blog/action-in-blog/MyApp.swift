import SwiftUI

struct AppConfig {
    var url: String = "https://pokeapi.co"

    static func fromLaunchEnvironment() -> AppConfig {
        var appConfig = AppConfig()
        #if DEBUG
            let environment = ProcessInfo.processInfo.environment
            if let url = environment["POKEMON_URL"] {
                appConfig.url = url
            }
        #endif
        return appConfig
    }
}

private struct AppConfigurationKey: EnvironmentKey {
    static let defaultValue = AppConfig()
}

extension EnvironmentValues {
    var appConfig: AppConfig {
        get { self[AppConfigurationKey.self] }
        set { self[AppConfigurationKey.self] = newValue }
    }
}

@main struct MyApp: App {
    var body: some Scene {
        WindowGroup {
            ContentView()
                .environment(\.appConfig, .fromLaunchEnvironment())
        }
    }
}
