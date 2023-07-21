package co.touchlab.kampkit.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.ComposeUIViewController
import co.touchlab.kampkit.injectInstance
import co.touchlab.kampkit.models.BreedViewModel
import co.touchlab.kampkit.ui.theme.KaMPKitTheme
import co.touchlab.kermit.Logger
import platform.UIKit.UIApplication
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceStyle

fun MainViewController(logger: Logger, viewModel: BreedViewModel) = ComposeUIViewController {
    val isDarkThemeEnabled =
        UIScreen.mainScreen.traitCollection.userInterfaceStyle == UIUserInterfaceStyle.UIUserInterfaceStyleDark

    KaMPKitTheme(isDarkThemeEnabled, false) {
        MainScreen(viewModel, logger)
    }
}
