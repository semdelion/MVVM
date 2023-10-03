package com.semdelion.data.storages.account

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.semdelion.data.storages.account.tuples.AccountSignInTuple
import com.semdelion.data.storages.account.tuples.AccountUpdateUsernameTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {
    @Query("SELECT id, password From accounts WHERE email = :email")
    suspend fun findByEmail(email: String): AccountSignInTuple?

    @Update(entity = AccountDBEntity::class)
    suspend fun updateUsername(usernameTuple: AccountUpdateUsernameTuple)

    @Insert
    suspend fun createAccount(accountDBEntity: AccountDBEntity)

    @Query("SELECT * From accounts WHERE id = :accountId")
    fun getById(accountId: Long): Flow<AccountDBEntity?>
}