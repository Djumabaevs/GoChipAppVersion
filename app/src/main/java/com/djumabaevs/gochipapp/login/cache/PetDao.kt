package com.djumabaevs.gochipapp.login.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {

    @Query("SELECT * FROM pets")
    fun getAllRestaurants(): Flow<List<Pet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPets(restaurants: List<Pet>)

    @Query("DELETE FROM pets")
    suspend fun deleteAllRestaurants()
}