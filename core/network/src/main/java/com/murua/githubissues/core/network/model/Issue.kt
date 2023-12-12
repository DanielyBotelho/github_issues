package com.murua.githubissues.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Status { @SerialName("open") OPEN, @SerialName("closed") CLOSED }

@Serializable
data class Issue(
    val id: Int,
    val user: User,
    val title: String = "",
    val state: Status,
    @SerialName("html_url") val url: String,
    @SerialName("body") val description: String? = "",
    @SerialName("created_at") val createdAt: String
)