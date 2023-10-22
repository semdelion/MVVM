package com.semdelion.data.storages.account

import android.database.sqlite.SQLiteConstraintException
import com.semdelion.data.storages.account.tuples.AccountUpdateUsernameTuple
import com.semdelion.domain.repositories.accounts.AccountAlreadyExistsException
import com.semdelion.domain.repositories.accounts.AuthException
import com.semdelion.domain.repositories.accounts.models.Account
import com.semdelion.domain.repositories.accounts.models.SignUpData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface IAccountsStorage {

    suspend fun findAccountIdByEmailAndPassword(email: String, password: String): Long

    suspend fun createAccount(signUpData: SignUpData)

    fun getAccountById(accountId: Long): Flow<Account?>

    suspend fun updateUsernameForAccountId(accountId: Long, newUsername: String)
}