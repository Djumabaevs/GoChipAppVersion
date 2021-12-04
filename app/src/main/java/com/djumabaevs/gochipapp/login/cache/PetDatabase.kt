package com.djumabaevs.gochipapp.login.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Pet::class], version = 1)
abstract class PetDatabase : RoomDatabase() {

    abstract fun petDao(): PetDao
}