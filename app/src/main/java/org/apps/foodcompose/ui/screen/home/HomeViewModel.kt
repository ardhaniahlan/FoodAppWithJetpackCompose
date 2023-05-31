package org.apps.foodcompose.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.apps.foodcompose.ui.common.UiState
import org.apps.foodcompose.data.FoodRepository
import org.apps.foodcompose.model.ChooseFood
import org.apps.foodcompose.model.Food

class HomeViewModel (private val repository: FoodRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<ChooseFood>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<ChooseFood>>>
        get() = _uiState

    fun getAllFood(){
        viewModelScope.launch {
            repository.getAllFood()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { chooseFood ->
                    _uiState.value = UiState.Success(chooseFood)
                }
        }
    }

    private val _groupedFood = MutableStateFlow(
        repository.getFood()
            .sortedBy { it.nama}
            .groupBy { it.nama[0] }
    )
    val groupedFood: StateFlow<Map<Char, List<Food>>> get() = _groupedFood

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query
    fun search(newQuery: String) {
        _query.value = newQuery
        _groupedFood.value = repository.searchFood(_query.value)
            .sortedBy { it.nama }
            .groupBy { it.nama[0] }
    }

}