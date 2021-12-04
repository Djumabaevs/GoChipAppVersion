package com.djumabaevs.gochipapp.login.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class Pet(
    @PrimaryKey val name: String,
    val type: String,
)