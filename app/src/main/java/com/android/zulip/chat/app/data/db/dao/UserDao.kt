package com.android.zulip.chat.app.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.zulip.chat.app.data.db.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(usersList: List<UserEntity>)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM user where user.user_id = :userId")
    suspend fun getUserById(userId: Long): UserEntity

    @Query("SELECT * FROM user where lower(user.full_name) LIKE '%' || :name || '%'")
    suspend fun getAllUsersByNameLike(name: String): List<UserEntity>
}