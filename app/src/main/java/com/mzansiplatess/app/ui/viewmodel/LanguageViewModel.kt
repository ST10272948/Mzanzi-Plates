package com.mzansiplatess.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LanguageViewModel : ViewModel() {

    // Holds the currently selected language
    private val _selectedLanguage = MutableStateFlow("English")
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    // Update the selected language (just updates state)
    fun updateLanguage(language: String) {
        _selectedLanguage.value = language
    }
}
