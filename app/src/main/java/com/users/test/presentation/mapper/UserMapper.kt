package com.users.test.presentation.mapper

import com.users.test.domain.model.UserDomainModel
import com.users.test.presentation.screens.add_user.UserUiModel

class UserMapper {
    fun mapFromUiToDomain(model: UserUiModel): UserDomainModel {
        return UserDomainModel(id = model.id, name = model.name, description = model.description)
    }
}