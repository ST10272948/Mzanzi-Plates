package com.mzansiplatess.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzansiplatess.app.model.Restaurant
import com.mzansiplatess.app.repo.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: Repository = Repository()) : ViewModel() {
    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadRestaurants() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val list = repo.getRestaurants()
                _restaurants.value = list
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Unknown error"
            } finally {
                _loading.value = false
            }
        }
    }
}


