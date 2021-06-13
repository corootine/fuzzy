package com.corootine.fuzzy.domain.userId.provide

inline class UserId(val id: String) {

    companion object {

        fun empty(): UserId = UserId("")
    }
}

