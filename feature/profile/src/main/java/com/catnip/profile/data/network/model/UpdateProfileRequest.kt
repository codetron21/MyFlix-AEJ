package com.catnip.profile.data.network.model

import com.google.gson.annotations.SerializedName

data class UpdateProfileRequest(
    @SerializedName("birthdate")
    val birthdate: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("gender")
    val gender: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("username")
    val username: String?
)