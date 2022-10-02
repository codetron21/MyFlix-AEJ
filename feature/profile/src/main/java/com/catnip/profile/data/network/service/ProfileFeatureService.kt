package com.catnip.profile.data.network.service

import com.catnip.profile.data.network.model.UpdateProfileRequest
import com.catnip.profile.data.network.model.UpdateProfileResponse
import com.catnip.shared.data.model.response.BaseResponse
import retrofit2.http.Body
import retrofit2.http.PUT

interface ProfileFeatureService {

    @PUT("/api/v1/user")
    suspend fun updateProfile(@Body profileRequest: UpdateProfileRequest): BaseResponse<UpdateProfileResponse>

}