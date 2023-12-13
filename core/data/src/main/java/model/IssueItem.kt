package model

import android.os.Parcelable
import com.murua.githubissues.core.network.model.Issue
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
    date = createdAt,
    title = title,
    description = description ?: "",
    url = url,
    state = State.valueOf(state.name)
)
