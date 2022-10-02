package com.catnip.shared.data.model.mapper

import com.catnip.shared.data.model.response.UserResponse
import com.catnip.shared.data.model.viewparam.UserViewParam
import com.catnip.shared.utils.DataObjectMapper
import com.catnip.shared.utils.ViewParamMapper

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
object UserMapper : ViewParamMapper<UserResponse, UserViewParam> {
    override fun toViewParam(dataObject: UserResponse?): UserViewParam = UserViewParam(
        email = dataObject?.email.orEmpty(),
        birthdate = dataObject?.birthdate.orEmpty(),
        gender = dataObject?.gender ?: -1,
        id = dataObject?.id ?: -1,
        username = dataObject?.username.orEmpty()
    )
}

object UserObjectMapper : DataObjectMapper<UserResponse, UserViewParam> {
    override fun toDataObject(viewParam: UserViewParam?): UserResponse = UserResponse(
        email = viewParam?.email.orEmpty(),
        birthdate = viewParam?.birthdate.orEmpty(),
        gender = viewParam?.gender ?: -1,
        id = viewParam?.id ?: -1,
        username = viewParam?.username.orEmpty()
    )
}