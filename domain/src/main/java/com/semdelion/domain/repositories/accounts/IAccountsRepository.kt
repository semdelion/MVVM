package com.semdelion.domain.repositories.accounts

import com.semdelion.domain.repositories.accounts.models.Account
import com.semdelion.domain.repositories.accounts.models.SignUpData
import kotlinx.coroutines.flow.Flow

/**
 * Repository with account-related actions, e.g. sign-in, sign-up, edit account etc.
 */
interface IAccountsRepository {

    /**
     * Whether user is signed-in or not.
     */
    suspend fun isSignedIn(): Boolean

    /**
     * Try to sign-in with the email and password.
     * @throws [EmptyFieldException], [AuthException]
     */
    suspend fun signIn(email: String, password: String)

    /**
     * Create a new account.
     * @throws [EmptyFieldException], [PasswordMismatchException], [AccountAlreadyExistsException]
     */
    suspend fun signUp(signUpData: SignUpData)

    /**
     * Sign-out from the app.
     */
    suspend fun logout()

    /**
     * Get the account info of the current signed-in user.
     */
    suspend fun getAccount(): Flow<Account?>

    /**
     * Change the username of the current signed-in user.
     * @throws [EmptyFieldException], [AuthException]
     */
    suspend fun updateAccountUsername(newUsername: String)

}