package com.semdelion.domain.repositories.accounts


open class AppException : RuntimeException()

class EmptyFieldException(
    val field: Field
) : AppException()

class PasswordMismatchException : AppException()

class AccountAlreadyExistsException : AppException()

class AuthException : AppException()

class StorageException: AppException()

enum class Field {
    Email,
    Username,
    Password
}