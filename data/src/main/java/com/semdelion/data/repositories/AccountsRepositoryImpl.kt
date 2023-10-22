package com.semdelion.data.repositories

import com.semdelion.data.core.storages.BaseRoomStorages
import com.semdelion.data.storages.account.AppSettings
import com.semdelion.data.storages.account.IAccountsStorage
import com.semdelion.domain.core.coroutines.IoDispatcher
import com.semdelion.domain.repositories.accounts.AuthException
import com.semdelion.domain.repositories.accounts.EmptyFieldException
import com.semdelion.domain.repositories.accounts.Field
import com.semdelion.domain.repositories.accounts.IAccountsRepository
import com.semdelion.domain.repositories.accounts.models.Account
import com.semdelion.domain.repositories.accounts.models.SignUpData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn

class AccountsRepositoryImpl(
    private val accountsStorage: IAccountsStorage,
    private val appSettings: AppSettings,
    private val ioDispatcher: IoDispatcher
): BaseRoomStorages(), IAccountsRepository
{
    private val currentAccountIdFlow: MutableStateFlow<AccountId> by lazy { MutableStateFlow(AccountId(appSettings.getCurrentAccountId())) }

    override suspend fun isSignedIn(): Boolean {
        delay(2000)
        return appSettings.getCurrentAccountId() != AppSettings.NO_ACCOUNT_ID
    }

    override suspend fun signIn(email: String, password: String) = wrapSQLiteException(ioDispatcher.value) {
        if (email.isBlank()) throw EmptyFieldException(Field.Email)
        if (password.isBlank()) throw EmptyFieldException(Field.Password)
        delay(1000)
        val accountId = accountsStorage.findAccountIdByEmailAndPassword(email, password)
        appSettings.setCurrentAccountId(accountId)
        currentAccountIdFlow.value = AccountId(accountId)

        return@wrapSQLiteException
    }

    override suspend fun signUp(signUpData: SignUpData) = wrapSQLiteException(ioDispatcher.value) {
        signUpData.validate()
        delay(1000)
        accountsStorage.createAccount(signUpData)
    }

    override suspend fun logout() {
        appSettings.setCurrentAccountId(AppSettings.NO_ACCOUNT_ID)
        currentAccountIdFlow.value = AccountId(AppSettings.NO_ACCOUNT_ID)
    }

    override suspend fun getAccount(): Flow<Account?> {
        return currentAccountIdFlow
            .flatMapLatest { accountId ->
                if (accountId.value == AppSettings.NO_ACCOUNT_ID) {
                    flowOf(null)
                } else {
                    accountsStorage.getAccountById(accountId.value)
                }
            }
            .flowOn(ioDispatcher.value)
    }

    override suspend fun updateAccountUsername(newUsername: String) = wrapSQLiteException(ioDispatcher.value) {
        if (newUsername.isBlank()) throw EmptyFieldException(Field.Username)
        delay(1000)
        val accountId = appSettings.getCurrentAccountId()
        if (accountId == AppSettings.NO_ACCOUNT_ID) throw AuthException()

        accountsStorage.updateUsernameForAccountId(accountId, newUsername)

        currentAccountIdFlow.value = AccountId(accountId)
        return@wrapSQLiteException
    }


    private class AccountId(val value: Long)
}