package com.murua.githubissues.core.data.model

import com.murua.githubissues.core.network.model.Issue
import com.murua.githubissues.core.network.model.Status
import com.murua.githubissues.core.network.model.User
import model.State
import model.asDataModel
import org.junit.Test
import kotlin.test.assertEquals

class IssueItemTest {
    @Test
    fun whenIssueIsMapped_shouldMappedToIssueItem() {
        val issue = Issue.mock()

        val issueItem = issue.asDataModel()

        assertEquals(0, issueItem.id)
        assertEquals("avatar/url", issueItem.avatarUrl)
        assertEquals("Título do Issue", issueItem.title)
        assertEquals(State.OPEN, issueItem.state)
        assertEquals("//:url", issueItem.url)
        assertEquals("Descrição do Issue", issueItem.description)
        assertEquals("2023-12-06T14:34:59Z", issueItem.date)
    }
}

fun Issue.Companion.mock() =
    Issue(
        id = 0,
        user = User(1, "avatar/url"),
        title = "Título do Issue",
        state = Status.OPEN,
        url = "//:url",
        description = "Descrição do Issue",
        createdAt = "2023-12-06T14:34:59Z",
    )