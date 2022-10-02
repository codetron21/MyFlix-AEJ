package com.catnip.profile.data.network.datasources

import com.catnip.profile.data.network.model.UpdateProfileRequest
import com.catnip.profile.data.network.model.UpdateProfileResponse
import com.catnip.profile.data.network.service.ProfileFeatureService
import com.catnip.shared.data.model.response.BaseResponse

interface UpdateProfileDataSource {
    suspend fun updateProfile(profileRequest: UpdateProfileRequest): BaseResponse<UpdateProfileResponse>
}

class UpdateProfileDataSourceImpl(
    private val service: ProfileFeatureService
) : UpdateProfileDataSource {

    override suspend fun updateProfile(
        profileRequest: UpdateProfileRequest
    ): BaseResponse<UpdateProfileResponse> {
        return service.updateProfile(profileRequest)
    }

}