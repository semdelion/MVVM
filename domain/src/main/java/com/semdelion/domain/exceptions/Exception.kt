package com.semdelion.domain.exceptions


open class AppException : RuntimeException()

class EmptyFieldException(
    val field: Field
) : AppException()

class PasswordMismatchException : AppException()

class AccountAlreadyExistsException : AppException()

class AuthException : AppException()

enum class Field {
    Email,
    Username,
    Password
}