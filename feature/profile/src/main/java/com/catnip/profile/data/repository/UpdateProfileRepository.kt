package com.catnip.profile.data.repository

import com.catnip.core.wrapper.DataResource
import com.catnip.profile.data.network.datasources.UpdateProfileDataSource
import com.catnip.profile.data.network.model.UpdateProfileRequest
import com.catnip.profile.data.network.model.UpdateProfileResponse
import com.catnip.shared.data.model.response.BaseResponse
import com.catnip.shared.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


typealias UpdateProfileDataResources = DataResource<BaseResponse<UpdateProfileResponse>>

interface UpdateProfileRepository {

    suspend fun updateProfile(
        profileRequest: UpdateProfileRequest
    ): Flow<UpdateProfileDataResources>
}

class UpdateProfileRepositoryImpl(
    private val dataSource: UpdateProfileDataSource
) : UpdateProfileRepository, Repository() {

    override suspend fun updateProfile(profileRequest: UpdateProfileRequest): Flow<UpdateProfileDataResources> {
        return flow {
            emit(safeNetworkCall { dataSource.updateProfile(profileRequest) })
        }
    }
}