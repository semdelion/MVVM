package com.semdelion.data.storages.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.semdelion.domain.repositories.accounts.models.Account
import com.semdelion.domain.repositories.accounts.models.SignUpData

@Entity(
    tableName = "accounts",
    indices = [Index("email", unique = true)]
)
data class AccountDBEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(collate = ColumnInfo.NOCASE) val email: String,
    val username: String,
    val password: String,
    @ColumnInfo(name = "created_at") val createdAt: Long
) {
    fun toAccount(): Account = Account(
        email = email,
        username = username,
        createdAt = createdAt
    )

    companion object {
        fun fromSignUpData(signUpData: SignUpData): AccountDBEntity = AccountDBEntity(
            id = 0,
            email = signUpData.email,
            username = signUpData.username,
            password = signUpData.password,
            createdAt = System.currentTimeMillis()
        )
    }
}