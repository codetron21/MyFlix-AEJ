package com.catnip.profile.mapper

import com.catnip.profile.data.network.model.UpdateProfileRequest
import com.catnip.shared.data.model.viewparam.UserViewParam
import com.catnip.shared.utils.DataObjectMapper

object ProfileMapper :DataObjectMapper<UpdateProfileRequest,UserViewParam>{
    override fun toDataObject(viewParam: UserViewParam?): UpdateProfileRequest {
        return UpdateProfileRequest(
            email = viewParam?.email.orEmpty(),
            birthdate = viewParam?.birthdate.orEmpty(),
            gender = viewParam?.gender ?: -1,
            id = viewParam?.id ?: -1,
            username = viewParam?.username.orEmpty()
        )
    }
}