package model

import kotlinx.serialization.Serializable

enum class IssueState { OPEN, CLOSED }

@Serializable
data class Issue(
    val id: String,
    val title: String = "",
    val state: IssueState,
)