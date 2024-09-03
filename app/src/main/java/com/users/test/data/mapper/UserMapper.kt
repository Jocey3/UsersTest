package com.users.test.data.mapper

import com.users.test.data.model.UserDataModel
import com.users.test.data.source.local.room.entity.UserEntity
import com.users.test.domain.model.UserDomainModel

class UserMapper() {
    fun mapFromEntityToData(entity: UserEntity): UserDataModel {
        return UserDataModel(name = entity.name, description = entity.description)
    }

    fun mapFromDataToDomain(model: UserDataModel): UserDomainModel {
        return UserDomainModel(name = model.name, description = model.description)
    }

    fun mapFromDataToEntity(model: UserDataModel): UserEntity {
        return UserEntity(id = null, name = model.name, description = model.description)
    }

    fun mapFromDomainToData(entity: UserDomainModel): UserDataModel {
        return UserDataModel(name = entity.name, description = entity.description)
    }
}