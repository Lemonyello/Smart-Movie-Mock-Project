package com.hangpp.smartmovie.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ActorDto(
    @SerializedName("id") @Expose val id: String,
    @SerializedName("profile_path") @Expose val profilePath: String,
    @SerializedName("name") @Expose val name: String
)

data class ActorResult(
    @SerializedName("id") @Expose val id: String,
    @SerializedName("cast") @Expose val casts: List<ActorDto>
)