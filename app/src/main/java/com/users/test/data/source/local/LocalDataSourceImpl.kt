package com.users.test.data.source.local

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.users.test.data.mapper.UserMapper
import com.users.test.data.model.UserDataModel
import com.users.test.data.source.local.room.UsersDao
import com.users.test.data.source.local.room.entity.UserEntity

class LocalDataSourceImpl(
    private val usersDao: UsersDao,
    private val mapper: UserMapper
) : LocalDataSource {

    override fun getAll(): PagingSource<Int, UserDataModel> {
        val originalPagingSource = usersDao.getAll()
        return UserEntityToDataModelPagingSource(originalPagingSource, mapper)
    }

    override suspend fun insert(user: UserDataModel) {
        usersDao.insert(mapper.mapFromDataToEntity(user))
    }

    override suspend fun getById(id: Int): UserDataModel {
        return mapper.mapFromEntityToData(usersDao.getById(id))
    }

    override suspend fun insertAll(users: List<UserDataModel>) {
        usersDao.insertAll(users.map { user -> mapper.mapFromDataToEntity(user) })
    }

    override suspend fun update(user: UserDataModel) {
        usersDao.update(mapper.mapFromDataToEntity(user))
    }

    override suspend fun delete(user: UserDataModel) {
        usersDao.delete(mapper.mapFromDataToEntity(user))
    }

    class UserEntityToDataModelPagingSource(
        private val originalPagingSource: PagingSource<Int, UserEntity>,
        private val mapper: UserMapper
    ) : PagingSource<Int, UserDataModel>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDataModel> {
            // Load data from the original PagingSource
            val result = originalPagingSource.load(params)

            // Map the entities to data models
            return when (result) {
                is LoadResult.Page -> {
                    LoadResult.Page(
                        data = result.data.map {
                            // Mapping here
                            Log.d("myLog", it.toString())
                            mapper.mapFromEntityToData(it)
                        },
                        prevKey = result.prevKey,
                        nextKey = result.nextKey
                    )
                }

                is LoadResult.Error -> {
                    LoadResult.Error(result.throwable)
                }


                else -> {
                    throw IllegalStateException("Unexpected LoadResult type: $result")
                }
            }
        }

        override fun getRefreshKey(state: PagingState<Int, UserDataModel>): Int? {
            return originalPagingSource.getRefreshKey(state as PagingState<Int, UserEntity>)
        }
    }
}