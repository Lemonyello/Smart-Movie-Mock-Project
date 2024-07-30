package com.hangpp.smartmovie.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenreDto (
    @SerializedName("id") @Expose val id: String,
    @SerializedName("name") @Expose val name: String
)

data class GenreResult (
    @SerializedName("genres") @Expose val genres: List<GenreDto>
)