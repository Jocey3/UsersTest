package com.users.test.data.mapper

import com.users.test.data.model.UserDataModel
import com.users.test.data.source.local.room.entity.UserEntity
import com.users.test.domain.model.UserDomainModel

class UserMapper() {
    fun mapFromEntityToData(entity: UserEntity): UserDataModel {
        return UserDataModel(id = entity.id, name = entity.name, description = entity.description)
    }

    fun mapFromDataToDomain(model: UserDataModel): UserDomainModel {
        return UserDomainModel(id = model.id, name = model.name, description = model.description)
    }

    fun mapFromDataToEntity(model: UserDataModel): UserEntity {
        return UserEntity(id = model.id, name = model.name, description = model.description)
    }

    fun mapFromDomainToData(entity: UserDomainModel): UserDataModel {
        return UserDataModel(id = entity.id, name = entity.name, description = entity.description)
    }
}