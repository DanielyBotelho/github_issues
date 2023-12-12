package com.murua.githubissues.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    @SerialName("avatar_url") val avatarUrl: String
)