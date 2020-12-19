package ru.frozenrpiest.academyapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Actor(
    val id: Int,
    val name: String,
    val picture: String
) : Parcelable