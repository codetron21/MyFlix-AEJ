package com.catnip.profile.domain

import com.catnip.core.base.BaseUseCase
import com.catnip.core.wrapper.ViewResource
import com.catnip.shared.data.repository.UserPreferenceRepository
import com.catnip.shared.utils.ext.suspendSubscribe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class LogoutUserUseCase(
    private val repository: UserPreferenceRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, Unit>(dispatcher) {

    override suspend fun execute(param: Unit?): Flow<ViewResource<Unit>> = flow {
        emit(ViewResource.Loading())
        repository.clearData().first().suspendSubscribe(
            doOnSuccess = { emit(ViewResource.Success(Unit)) },
            doOnError = { emit(ViewResource.Error(it.exception)) }
        )
    }
}