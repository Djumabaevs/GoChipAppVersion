package com.djumabaevs.gochipapp.login.cache

import androidx.room.withTransaction
import kotlinx.coroutines.delay
import javax.inject.Inject

class RestaurantRepository @Inject constructor(
    private val api: PetApi,
    private val db: PetDatabase
) {
    private val petDao = db.petDao()

    fun getPets() = networkBoundResource(
        query = {
            petDao.getAllPets()
        },
        fetch = {
            delay(2000)
            api.getPets()
        },
        saveFetchResult = { pets ->
            db.withTransaction {
                petDao.deleteAllPets()
                petDao.insertPets(pets)
            }
        }
    )
}