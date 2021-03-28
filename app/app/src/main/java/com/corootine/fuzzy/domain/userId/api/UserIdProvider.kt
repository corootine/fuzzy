package com.corootine.fuzzy.domain.userId.api

interface UserIdProvider {

    suspend fun provide(): UserId
}