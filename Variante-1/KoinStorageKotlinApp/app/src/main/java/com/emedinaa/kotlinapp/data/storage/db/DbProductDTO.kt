package com.emedinaa.kotlinapp.data.storage.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_product")
data class DBProduct(
    @PrimaryKey(autoGenerate = true) val id:Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "cost") val cost: Double?,
    @ColumnInfo(name = "logo") val logo: Int?
)