package org.apps.foodcompose.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.apps.foodcompose.model.ChooseFood
import org.apps.foodcompose.model.Food
import org.apps.foodcompose.model.FoodData

class FoodRepository {

    private val pilihFood = mutableListOf<ChooseFood>()

    init {
        if (pilihFood.isEmpty()) {
            FoodData.food.forEach {
                pilihFood.add(ChooseFood(it))
            }
        }
    }

    fun getAllFood(): Flow<List<ChooseFood>> {
        return flowOf(pilihFood)
    }

    fun getFood(): List<Food> {
        return FoodData.food
    }

    fun getFoodById(foodId: Long) : ChooseFood{
        return pilihFood.first {
            it.pilihFood.id == foodId
        }
    }

    fun searchFood(query: String): List<Food>{
        return FoodData.food.filter {
            it.nama.contains(query, ignoreCase = true)
        }
    }

    companion object {
        @Volatile
        private var instance: FoodRepository? = null

        fun getInstance(): FoodRepository =
            instance ?: synchronized(this) {
                FoodRepository().apply {
                    instance = this
                }
            }
    }
}
