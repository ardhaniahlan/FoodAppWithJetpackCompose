package org.apps.foodcompose.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.apps.foodcompose.ui.common.UiState
import org.apps.foodcompose.data.FoodRepository
import org.apps.foodcompose.model.ChooseFood

class DetailViewModel (private val repository: FoodRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<ChooseFood>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ChooseFood>>
        get() = _uiState

    fun getFoodById(foodId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getFoodById(foodId))
        }
    }
}