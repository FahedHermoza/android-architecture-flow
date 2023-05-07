package com.fahed.composeapp.data.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DBProduct::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

}