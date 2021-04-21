package com.corootine.fuzzy.domain.userId

interface UserIdProvider {

    suspend fun provide(): UserId
}