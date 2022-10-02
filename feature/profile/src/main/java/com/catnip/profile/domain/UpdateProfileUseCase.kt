package com.catnip.profile.domain

import com.catnip.core.base.BaseUseCase
import com.catnip.core.wrapper.ViewResource
import com.catnip.profile.data.repository.UpdateProfileRepository
import com.catnip.profile.mapper.ProfileMapper
import com.catnip.shared.data.model.viewparam.UserViewParam
import com.catnip.shared.utils.ext.suspendSubscribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class UpdateProfileUseCase(
    private val checkProfileFieldUseCase: CheckProfileFieldUseCase,
    private val saveProfileDataUseCase: SaveProfileDataUseCase,
    private val repository: UpdateProfileRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<UpdateProfileUseCase.Param, UserViewParam>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<UserViewParam>> = flow {
        param?.let {
            val data = it.userViewParam
            emit(ViewResource.Loading())

            val validation =
                checkProfileFieldUseCase(CheckProfileFieldUseCase.Param(data.username)).first()

            if (validation is ViewResource.Error) {
                emit(ViewResource.Error(validation.exception))
                return@flow
            }

            repository.updateProfile(ProfileMapper.toDataObject(data))
                .collect { updateResult ->
                    updateResult.suspendSubscribe(
                        doOnError = { error ->
                            emit(ViewResource.Error(error.exception))
                        },
                        doOnSuccess = {
                            val isSuccess = updateResult.payload?.isSuccess ?: false

                            if (isSuccess) {
                                saveProfileDataUseCase(SaveProfileDataUseCase.Param(data)).collect { saveResult ->
                                    saveResult.suspendSubscribe(
                                        doOnSuccess = { viewRes ->
                                            viewRes.payload?.let { user ->
                                                emit(ViewResource.Success(user))
                                            }
                                        },
                                        doOnError = { error ->
                                            emit(ViewResource.Error(error.exception))
                                        }
                                    )
                                }
                            }
                        },

                        )
                }
        } ?: throw IllegalStateException("Param Required")
    }

    data class Param(val userViewParam: UserViewParam)
}