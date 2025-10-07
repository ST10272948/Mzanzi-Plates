package com.mzansiplatess.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.mzansiplatess.app.ui.AppNavHost
import com.mzansiplatess.app.ui.helper.LocalHelper
import com.mzansiplatess.app.ui.theme.MzansiPlatessTheme
import com.mzansiplatess.app.ui.viewmodel.AppSettingsViewModel
import com.mzansiplatess.app.ui.viewmodel.AppThemeMode
import com.mzansiplatess.app.ui.viewmodel.LanguageViewModel

class MainActivity : ComponentActivity() {

    private val languageViewModel: LanguageViewModel by viewModels()
    private val appSettingsViewModel: AppSettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Observe selected language from ViewModel
            val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()

            // Apply locale whenever it changes
            LocalHelper.setLocale(this, selectedLanguage)

            val appSettings by appSettingsViewModel.settings.collectAsState()

            // Apply app theme based on settings
            MzansiPlatessTheme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController, 
                    languageViewModel = languageViewModel,
                    appSettingsViewModel = appSettingsViewModel
                )
            }
        }
    }
}
