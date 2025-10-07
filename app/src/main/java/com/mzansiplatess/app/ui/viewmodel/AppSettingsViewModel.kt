package com.mzansiplatess.app.ui.viewmodel

import android.app.Application
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

private val Application.settingsDataStore by preferencesDataStore(name = "app_settings")

enum class AppThemeMode { SYSTEM, LIGHT, DARK }

data class AppSettings(
    val pushNotifications: Boolean = true,
    val promotions: Boolean = false,
    val appUpdates: Boolean = true,
    val themeMode: AppThemeMode = AppThemeMode.SYSTEM,
    val languageCode: String = "en"
)

class AppSettingsViewModel(app: Application) : AndroidViewModel(app) {
    private val PUSH = booleanPreferencesKey("pushNotifications")
    private val PROMO = booleanPreferencesKey("promotions")
    private val UPDATES = booleanPreferencesKey("appUpdates")
    private val THEME = intPreferencesKey("themeMode")
    private val LANG = intPreferencesKey("languageCodeHash") // store hash to avoid long strings

    private val appContext = getApplication<Application>()

    val settings: StateFlow<AppSettings> = appContext.settingsDataStore.data
        .catch { emit(emptyPreferences()) }
        .map { prefs ->
            AppSettings(
                pushNotifications = prefs[PUSH] ?: true,
                promotions = prefs[PROMO] ?: false,
                appUpdates = prefs[UPDATES] ?: true,
                themeMode = AppThemeMode.values().getOrElse(prefs[THEME] ?: 0) { AppThemeMode.SYSTEM },
                languageCode = when (prefs[LANG]) {
                    "en".hashCode() -> "en"
                    "zu".hashCode() -> "zu"
                    "tn".hashCode() -> "tn"
                    else -> "en"
                }
            )
        }
        .stateIn(viewModelScope, SharingStarted.Eagerly, AppSettings())

    fun setPushNotifications(enabled: Boolean) = save { it[PUSH] = enabled }
    fun setPromotions(enabled: Boolean) = save { it[PROMO] = enabled }
    fun setAppUpdates(enabled: Boolean) = save { it[UPDATES] = enabled }
    fun setTheme(mode: AppThemeMode) = save { it[THEME] = mode.ordinal }
    fun setLanguage(code: String) = save { it[LANG] = code.lowercase().hashCode() }

    private fun save(block: suspend (androidx.datastore.preferences.core.MutablePreferences) -> Unit) {
        viewModelScope.launch {
            appContext.settingsDataStore.edit { prefs ->
                block(prefs)
            }
        }
    }
}


