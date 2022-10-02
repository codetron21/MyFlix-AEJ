package com.catnip.profile.domain

import com.catnip.core.base.BaseUseCase
import com.catnip.core.exception.FieldErrorException
import com.catnip.core.wrapper.ViewResource
import com.catnip.profile.R
import com.catnip.profile.constants.ProfileConstants.FIELD_USERNAME
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

typealias CheckFieldProfileResult = List<Pair<Int, Int>>

class CheckProfileFieldUseCase(dispatcher: CoroutineDispatcher) :
    BaseUseCase<CheckProfileFieldUseCase.Param, CheckFieldProfileResult>(dispatcher) {

    override suspend fun execute(param: Param?): Flow<ViewResource<CheckFieldProfileResult>> =
        flow {
            param?.let { data ->
                val result = mutableListOf<Pair<Int, Int>>()

                checkIsUsernameValid(data.username)?.let { result.add(it) }

                if (result.isEmpty()) {
                    emit(ViewResource.Success(result))
                } else {
                    emit(ViewResource.Error(FieldErrorException(result)))
                }
            } ?: throw IllegalStateException("Param Required")
        }

    private fun checkIsUsernameValid(username: String?): Pair<Int, Int>? {
        if (username == null || username.isEmpty())
            return Pair(FIELD_USERNAME, R.string.error_username_empty)
        return null
    }

    data class Param(val username: String)
}