package model

import android.os.Parcelable
import com.murua.githubissues.core.network.model.Issue
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.parcelize.Parcelize

@Parcelize
enum class State(val stateName: String): Parcelable { OPEN("open"), CLOSED("closed") }

@Parcelize
class IssueItem(
    val id: Int,
    val avatarUrl: String,
    val date: String,
    val title: String,
    val description: String,
    val url: String,
    val state: State
) : Parcelable {
    companion object
}

fun Issue.asDataModel() = IssueItem(
    id = id,
    avatarUrl = user.avatarUrl,
    date = createdAt.toInstant().toLocalDateTime(TimeZone.UTC).toShortDate(),
    title = title,
    description = description ?: "",
    url = url,
    state = State.valueOf(state.name)
)

fun LocalDateTime.toShortDate() =
    "${dayOfMonth}/${monthNumber}/${year}"

