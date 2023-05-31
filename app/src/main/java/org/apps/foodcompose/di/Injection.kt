package org.apps.foodcompose.di

import org.apps.foodcompose.data.FoodRepository

object Injection {
    fun provideRepository(): FoodRepository {
        return FoodRepository.getInstance()
    }
}