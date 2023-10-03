package com.semdelion.data.storages.account

import android.database.sqlite.SQLiteConstraintException
import com.semdelion.data.storages.account.tuples.AccountUpdateUsernameTuple
import com.semdelion.domain.repositories.accounts.AccountAlreadyExistsException
import com.semdelion.domain.repositories.accounts.AuthException
import com.semdelion.domain.repositories.accounts.models.Account
import com.semdelion.domain.repositories.accounts.models.SignUpData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class AccountRoomStorage(
    private val accountsDao: AccountsDao):
    IAccountsStorage {

    override suspend fun findAccountIdByEmailAndPassword(email: String, password: String): Long {
        val tuple = accountsDao.findByEmail(email) ?: throw AuthException()
        if(tuple.password != password) throw AuthException()
        return tuple.id
    }

    override suspend fun createAccount(signUpData: SignUpData) {
        try {
            val entity = AccountDBEntity.fromSignUpData(signUpData)
            accountsDao.createAccount(entity)
        }
        catch (ex: SQLiteConstraintException) {
            val exception = AccountAlreadyExistsException()
            exception.initCause(ex)
            throw exception
        }
    }

    override fun getAccountById(accountId: Long): Flow<Account?> {
        return accountsDao.getById(accountId).map {accountDbEntity -> accountDbEntity?.toAccount()}
    }

    override suspend fun updateUsernameForAccountId(accountId: Long, newUsername: String) {
        accountsDao.updateUsername(AccountUpdateUsernameTuple(accountId, newUsername))
    }
}