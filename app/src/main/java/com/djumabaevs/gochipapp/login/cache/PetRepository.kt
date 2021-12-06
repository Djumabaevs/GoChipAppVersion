package com.djumabaevs.gochipapp.login.cache

import androidx.room.withTransaction
import kotlinx.coroutines.delay
import javax.inject.Inject

class PetRepository @Inject constructor(
   // private val api: PetApi,
    private val db: PetDatabase
) {
    private val petDao = db.petDao()

    fun getPets() = db.petDao().getAllPets()

//    fun getPets() = networkBoundResource(
//        query = {
//            petDao.getAllPets()
//        },
//        fetch = {
//            delay(2000)
//          //  api.getPets()
//        },
//        saveFetchResult = { pets ->
//            db.withTransaction {
//                petDao.deleteAllPets()
//                petDao.insertPets(pets)
//            }
//        }
//    )

    suspend fun savePets(pets: List<Pet>) {
        db.petDao().deleteAllPets()
        db.petDao().insertPets(pets)
    }
}