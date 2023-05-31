package org.apps.foodcompose.model

data class Food(
    val id: Long,
    val nama: String,
    val desc: String,
    val photoUrl: String,
    val masak: String,
)