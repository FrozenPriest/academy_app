package ru.frozenrpiest.academyapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actors_table")
data class CastEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "cast_id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "picture_link")
    val picture: String
)

