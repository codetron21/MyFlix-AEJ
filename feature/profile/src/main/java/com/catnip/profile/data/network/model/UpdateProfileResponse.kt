package com.catnip.profile.data.network.model

import com.catnip.shared.data.model.response.UserResponse
import com.google.gson.annotations.SerializedName

data class UpdateProfileResponse(
    @SerializedName("user")
    val user: UserResponse
)