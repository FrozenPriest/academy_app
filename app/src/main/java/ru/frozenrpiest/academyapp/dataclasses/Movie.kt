package ru.frozenrpiest.academyapp.dataclasses

import android.os.Parcel
import android.os.Parcelable

data class Movie(val name:String, val duration: Int, val rating: Float, val reviewCount: Int,
                 val genres: List<String>, val ageRestriction: String, val posterPreview: Int) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readFloat(),
        parcel.readInt(),
        parcel.createStringArrayList()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun describeContents(): Int {
    return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}