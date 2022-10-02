package com.catnip.profile.domain

import com.catnip.core.base.BaseUseCase
import com.catnip.core.wrapper.DataResource
import com.catnip.core.wrapper.ViewResource
import com.catnip.shared.data.model.mapper.UserObjectMapper
import com.catnip.shared.data.model.viewparam.UserViewParam
import com.catnip.shared.data.repository.UserPreferenceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class SaveProfileDataUseCase(
    private val repository: UserPreferenceRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<SaveProfileDataUseCase.Param, UserViewParam>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<UserViewParam>> = flow {
        param?.let {
            val userResponse = UserObjectMapper.toDataObject(it.user)
            val saveUser = repository.setCurrentUser(userResponse).first()

            if (saveUser is DataResource.Success) {
                emit(ViewResource.Success(it.user))
            } else {
                emit(ViewResource.Error(IllegalStateException("Failed to save local data")))
            }
        }
    }

    data class Param(val user: UserViewParam)
}