package com.catnip.profile.presentation.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.core.wrapper.ViewResource
import com.catnip.profile.domain.LogoutUserUseCase
import com.catnip.profile.domain.UpdateProfileUseCase
import com.catnip.shared.data.model.viewparam.UserViewParam
import com.catnip.shared.domain.GetCurrentUserUseCase
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val logoutUserUseCase: LogoutUserUseCase,
) : ViewModel() {

    val currentUserResult = MutableLiveData<ViewResource<UserViewParam>>()
    val updateUserResult = MutableLiveData<ViewResource<UserViewParam>>()
    val logoutResult = MutableLiveData<ViewResource<Unit>>()

    fun updateProfile(username: String) {
        viewModelScope.launch {
            val userViewParam = currentUserResult.value?.payload ?: return@launch

            updateProfileUseCase(
                UpdateProfileUseCase.Param(userViewParam.copy(username = username))
            ).collect(updateUserResult::postValue)
        }
    }

    fun getCurrentUser() {
        viewModelScope.launch {
            getCurrentUserUseCase().collect(currentUserResult::postValue)
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUserUseCase().collect(logoutResult::postValue)
        }
    }

}