package com.fahed.composeapp.data.storage.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import kotlinx.coroutines.flow.Flow


@Dao
interface ProductDao {

    @Query("SELECT * from table_product")
    fun products(): Flow<List<DBProduct>>

    @Query("SELECT * from table_product where id=:id")
    suspend fun getProduct(id: Int): DBProduct

    @Insert(onConflict = REPLACE)
    suspend fun addProduct(product: DBProduct)

    @Update(onConflict = REPLACE)
    suspend fun updateProduct(product: DBProduct)

    @Delete
    suspend fun deleteProduct(product: DBProduct)

    @Query("DELETE from table_product")
    suspend fun deleteAll()

}
