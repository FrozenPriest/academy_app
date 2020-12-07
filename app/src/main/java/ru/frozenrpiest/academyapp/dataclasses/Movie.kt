package ru.frozenrpiest.academyapp.dataclasses

data class Movie(val name:String, val duration: Int, val rating: Float, val reviewCount: Int,
                 val genres: List<String>, val ageRestriction: String, val posterPreview: Int)
