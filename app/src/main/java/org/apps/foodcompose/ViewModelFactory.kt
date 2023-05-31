package org.apps.foodcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.apps.foodcompose.data.FoodRepository
import org.apps.foodcompose.ui.screen.detail.DetailViewModel
import org.apps.foodcompose.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: FoodRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}